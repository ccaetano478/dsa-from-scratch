# Unique Characters in String — Three Approaches in Java

**Challenge:** Implement an algorithm to determine if a string has all unique characters. What if you cannot use additional data structures?

> *— Cracking the Coding Interview*

---

## Solutions Overview

| Method | Approach | Time | Space | Notes |
|---|---|---|---|---|
| `isUnique()` | HashSet lookup | O(n) | O(n) | Readable, explicit intent |
| `isUniqueV2()` | Boolean array (128 slots) | O(n) | O(1) | Fixed allocation, no hashing |
| `isUniqueV3()` | Bitmask on a single `int` | O(n) | O(1) | No extra data structure |

---

## Solution 1 — HashSet Lookup

```java
public static boolean isUnique(String word) {
    if (word.length() > 128) return false;

    Set<Character> seen = new HashSet<>();
    for (char c : word.toCharArray()) {
        if (!seen.add(c)) return false;
    }
    return true;
}
```

**How it works:** Iterates through each character and tries to add it to a `HashSet`. Since `add()` returns `false` when the element already exists, a duplicate is detected immediately.

**Key detail — pigeonhole short-circuit:** `if (word.length() > 128) return false;` — a string longer than the number of ASCII characters *must* contain duplicates by the pigeonhole principle. This is a free O(1) optimization worth adding to all three versions.

**When to use:** Good for an initial interview answer. Semantically honest — a `Set` models *"have I seen this?"* more clearly than a `Map<Character, Boolean>`.

---

## Solution 2 — Boolean Array (128 slots)

```java
public static boolean isUniqueV2(String word) {
    if (word.length() > 128) return false;

    boolean[] seen = new boolean[128];
    for (char c : word.toCharArray()) {
        if (seen[c]) return false;
        seen[c] = true;
    }
    return true;
}
```

**How it works:** Uses the character's ASCII numeric value directly as an index into a fixed-size `boolean[]`. No hashing, no boxing — just a direct array lookup.

**Why it's faster than the HashSet:** Array indexing is a single memory access. The HashSet must compute a hash, resolve potential collisions, and box `char` into `Character` on every iteration. The `boolean[128]` is also a tiny, fixed allocation with excellent cache locality.

> ⚠️ This still technically uses an extra data structure (the array), so it does **not** satisfy the book's follow-up constraint — but it gets much closer.

**When to use:** When you want to demonstrate awareness of the ASCII table and avoid hashing overhead.

---

## Solution 3 — Bitmask on a Single `int`

```java
public static boolean isUniqueV3(String word) {
    if (word.length() > 26) return false;

    int bitmask = 0;
    for (char c : word.toCharArray()) {
        int bit = c - 'a';
        if ((bitmask & (1 << bit)) != 0) return false;
        bitmask |= (1 << bit);
    }
    return true;
}
```

**How it works:** Each letter maps to a bit position inside a single `int`. Checking a character means testing whether its corresponding bit is already `1`; marking it means flipping that bit to `1`.

### Visual Walkthrough — `"bad"`

```
Start
bitmask = 00000000000000000000000000000000

Read 'b' → bit = 'b' - 'a' = 1
Check: (bitmask & 0b10) == 0  → not seen
Set:    bitmask |= 0b10
bitmask = 00000000000000000000000000000010

Read 'a' → bit = 'a' - 'a' = 0
Check: (bitmask & 0b01) == 0  → not seen
Set:    bitmask |= 0b01
bitmask = 00000000000000000000000000000011

Read 'd' → bit = 'd' - 'a' = 3
Check: (bitmask & 0b1000) == 0  → not seen
Set:    bitmask |= 0b1000
bitmask = 00000000000000000000000000001011

No duplicates found → return true ✓
```

Now with a duplicate — `"aba"`:

```
After 'a' and 'b':
bitmask = 00000000000000000000000000000011

Read 'a' again → bit = 0
Check: (bitmask & 0b01) != 0  → SEEN → return false ✗
```

### The Two Key Operations

| Operation | Code | Meaning |
|---|---|---|
| Check bit | `(bitmask & (1 << bit)) != 0` | Is this character already marked? |
| Set bit | `bitmask \|= (1 << bit)` | Mark this character as seen |

> ✅ **Satisfies "no additional data structures":** The entire state lives in a single `int` primitive — a CPU register, not a heap allocation. No array, no map, no set.

> ℹ️ **Limitation:** An `int` has 32 bits, covering exactly the 26 lowercase Latin letters. For the full ASCII range you would need a `long` (64 bits) plus a second `int`, or two `long`s.

---

## Why Solutions 2 and 3 Are O(1) Space

Both avoid any allocation that grows with input. The `boolean[128]` is always exactly 128 bytes regardless of string length, and the bitmask is always a single 4-byte `int`. Contrast this with the `HashSet`, which allocates one `Entry` object per unique character on the heap — O(n) in the worst case.

---

## Running the Code

```java
public static void main(String[] args) {
    String word = "data";

    System.out.println(isUnique(word));   // false — 'a' repeats
    System.out.println(isUniqueV2(word)); // false
    System.out.println(isUniqueV3(word)); // false

    String unique = "swift";
    System.out.println(isUnique(unique));   // true
    System.out.println(isUniqueV2(unique)); // true
    System.out.println(isUniqueV3(unique)); // true
}
```

---

## Full Source

`src/main/java/dev/me/arrays/problems/UniqueCharInString.java`