# String Reversal — Three Approaches in Java

**Challenge:** Create a function that reverses a string.

---

## Solutions Overview

| Method | Approach | Time | Space | Notes |
|---|---|---|---|---|
| `reverse()` | Manual `StringBuilder` loop | O(n) | O(n) | Pre-allocated capacity |
| `reverse2()` | Built-in `StringBuilder.reverse()` | O(n) | O(n) | Idiomatic Java |
| `reverse3()` | Two-pointer on `char[]` | O(n) | O(n) | Best constant factors |

---

## Solution 1 — Manual `StringBuilder` Loop

```java
public static String reverse(String word) {
    if (word == null || word.isEmpty()) {
        return word;
    }

    StringBuilder sb = new StringBuilder(word.length()); // pre-allocate!

    for (int i = word.length() - 1; i >= 0; i--) {
        sb.append(word.charAt(i));
    }

    return sb.toString();
}
```

**How it works:** Iterates from the last character down to index `0`, appending each character to a `StringBuilder`.

**Key detail — pre-allocation:** `new StringBuilder(word.length())` reserves exactly the right capacity upfront. Without this, `StringBuilder` starts with a default capacity of 16 and resizes (doubles + copies) whenever it runs out of room, which wastes both time and memory.

**When to use:** Good for demonstrating a manual loop approach in interviews or when you want explicit control over the iteration.

---

## Solution 2 — Built-in `StringBuilder.reverse()`

```java
public static String reverse2(String word) {
    StringBuilder sb = new StringBuilder(word);
    return sb.reverse().toString();
}
```

**How it works:** Delegates to the JDK's `StringBuilder.reverse()`, which is an internally optimised native method that handles Unicode surrogate pairs correctly.

**When to use:** This is the **idiomatic Java solution** — clean, readable, and well-tested. Prefer this in production code.

> ⚠️ Note: this version is missing a `null` guard. In production, add `if (word == null) return null;` before constructing the `StringBuilder`.

---

## Solution 3 — Two-Pointer on `char[]`

```java
public static String reverse3(String word) {
    char[] chars = word.toCharArray();

    int left = 0;
    int right = chars.length - 1;

    while (left < right) {
        char temp = chars[left];
        chars[left] = chars[right];
        chars[right] = temp;
        left++;
        right--;
    }

    return String.valueOf(chars);
}
```

**How it works:** Two pointers start at opposite ends of the array and march toward the centre, swapping characters along the way.

### Visual walkthrough — `"abcdef"`

```
Initial state
┌───┬───┬───┬───┬───┬───┐
│ a │ b │ c │ d │ e │ f │
└───┴───┴───┴───┴───┴───┘
  ↑                   ↑
  L=0               R=5

Step 1 — swap chars[0] and chars[5]
┌───┬───┬───┬───┬───┬───┐
│ f │ b │ c │ d │ e │ a │   ✓ ← already in final position
└───┴───┴───┴───┴───┴───┘
      ↑           ↑
      L=1         R=4

Step 2 — swap chars[1] and chars[4]
┌───┬───┬───┬───┬───┬───┐
│ f │ e │ c │ d │ b │ a │   ✓ ✓
└───┴───┴───┴───┴───┴───┘
          ↑   ↑
          L=2 R=3

Step 3 — swap chars[2] and chars[3]
┌───┬───┬───┬───┬───┬───┐
│ f │ e │ d │ c │ b │ a │   ✓ ✓ ✓
└───┴───┴───┴───┴───┴───┘
              ↑
          L=3 > R=2 → STOP

Result: "fedcba"
```

**Stopping condition — `while (left < right)`:**
- **Even-length string:** pointers pass each other (L > R) after the last swap — loop ends.
- **Odd-length string:** pointers converge on the same middle character (L == R) — that character doesn't need swapping, and the loop ends cleanly.

**Why it has better constant factors than `StringBuilder`:**
- Only one array allocation (`char[]`) — no internal resize buffers.
- Swaps in-place using a single `temp` variable — no second full-size buffer.
- Exactly `n/2` swaps — never touches the same index twice.

**When to use:** Best choice when demonstrating algorithmic knowledge in a coding challenge. Shows awareness of in-place techniques and pointer mechanics.

---

## Why All Three Are O(n) Space

Java strings are **immutable**. You can never truly reverse a string "in place" — at minimum you must allocate a `char[]` of the same length, which is O(n). Solution 3 is the most space-efficient in practice because it uses only that one array with no additional internal buffers, but asymptotically all three are equal.

---

## Running the Code

```java
public static void main(String[] args) {
    String word = "abcdefg";

    System.out.println(reverse(word));   // gfedcba
    System.out.println(reverse2(word));  // gfedcba
    System.out.println(reverse3(word));  // gfedcba
}
```

---

## Full Source

`src/main/java/dev/me/arrays/problems/ReverseArray.java`