# Merge Sorted Arrays

Given two sorted integer arrays, implement a function `mergeSortedArrays` that merges them into a single sorted array.

## Signature

```java
mergeSortedArrays(int[] a, int[] b) → int[]
```

## Example

```
Input:  [0, 3, 4, 31]  and  [4, 6, 8, 15, 30]
Output: [0, 3, 4, 4, 6, 8, 15, 30, 31]
```

## Constraints

- Both input arrays are already sorted in ascending order
- The returned array must also be sorted in ascending order
- Duplicate values across arrays are allowed and must be preserved
- Do not use a built-in sort function on the merged result

---

## Solutions Overview

| Method | Approach | Time | Space | Notes |
|---|---|---|---|---|
| `mergeSortedArrays()` | Single `while` loop with pointer exhaustion guards | O(n + m) | O(n + m) | Explicit index control |
| `mergeSortedArrays2()` | Pre-loaded items + `ArrayList` | O(n + m) | O(n + m) | Avoids array pre-allocation |
| `mergeSortedArrays3()` | Two-phase two-pointer | O(n + m) | O(n + m) | Cleanest, recommended |

---

## Solution 1 — Single Loop with Exhaustion Guards

```java
public static int[] mergeSortedArrays(int[] nums1, int[] nums2) {
    if (nums1 == null || nums2 == null) { return nums1; }
    if (nums1.length == 0) { return nums2; }
    if (nums2.length == 0) { return nums1; }

    int pointerNums1 = 0, pointerNums2 = 0, index = 0;
    int[] result = new int[nums1.length + nums2.length];

    while (index < result.length) {
        if (pointerNums1 == nums1.length) {
            result[index++] = nums2[pointerNums2++];
        } else if (pointerNums2 == nums2.length) {
            result[index++] = nums1[pointerNums1++];
        } else if (nums1[pointerNums1] <= nums2[pointerNums2]) {
            result[index++] = nums1[pointerNums1++];
        } else {
            result[index++] = nums2[pointerNums2++];
        }
    }

    return result;
}
```

**How it works:** A single loop runs until `result` is fully filled. At each iteration, four cases are evaluated in order:

1. `nums1` is exhausted → copy from `nums2`
2. `nums2` is exhausted → copy from `nums1`
3. `nums1[p1] <= nums2[p2]` → take from `nums1`
4. Otherwise → take from `nums2`

**Key insight — order of conditions matters:** The exhaustion guards (cases 1 and 2) must come *before* the comparison (cases 3 and 4). If you compare `nums1[p1]` against `nums2[p2]` when one pointer is already out of bounds, you get an `ArrayIndexOutOfBoundsException`.

---

## Solution 2 — Pre-loaded Items with ArrayList

```java
public static int[] mergeSortedArrays2(int[] nums1, int[] nums2) {
    if (nums1.length == 0) { return nums2; }
    if (nums2.length == 0) { return nums1; }

    int nums1Item = nums1[0];
    int nums2Item = nums2[0];
    int i = 1, j = 1;

    List<Integer> result = new ArrayList<>();

    while (result.size() < nums1.length + nums2.length) {
        boolean nums1Esgotado = i > nums1.length;
        boolean nums2Esgotado = j > nums2.length;

        if (!nums1Esgotado && (nums2Esgotado || nums1Item <= nums2Item)) {
            result.add(nums1Item);
            if (i < nums1.length) { nums1Item = nums1[i]; }
            i++;
        } else {
            result.add(nums2Item);
            if (j < nums2.length) { nums2Item = nums2[j]; }
            j++;
        }
    }

    return result.stream().mapToInt(Integer::intValue).toArray();
}
```

**How it works:** The first element of each array is loaded into `nums1Item` and `nums2Item` before the loop. Indices start at `1`. At each step, the two "current" items are compared, the smaller is added to the result, and the next item from that array is pre-loaded.

**Trade-off:** Avoids knowing the output size upfront (uses `ArrayList` instead of a fixed array). However, the pre-loading pattern adds complexity — exhaustion flags must be tracked manually to avoid loading out-of-bounds indices.

### ⚠️ Bug history — `ArrayIndexOutOfBoundsException`

The original version of this solution had a bug:

```java
// ❌ Original — no exhaustion check before accessing the array
result.add(nums1Item);
nums1Item = nums1[i]; // crashes when i == nums1.length
i++;
```

When `i` reached `nums1.length`, the code tried to access `nums1[i]` before checking if the array was exhausted. The fix is to guard the pre-load:

```java
// ✅ Fixed
result.add(nums1Item);
if (i < nums1.length) { nums1Item = nums1[i]; }
i++;
```

---

## Solution 3 — Two-Phase Two-Pointer ✅ Recommended

```java
public static int[] mergeSortedArrays3(int[] nums1, int[] nums2) {
    if (nums1.length == 0) return nums2;
    if (nums2.length == 0) return nums1;

    int[] result = new int[nums1.length + nums2.length];
    int p1 = 0, p2 = 0, i = 0;

    // Phase 1 — compare while both arrays have elements
    while (p1 < nums1.length && p2 < nums2.length) {
        if (nums1[p1] <= nums2[p2]) {
            result[i++] = nums1[p1++];
        } else {
            result[i++] = nums2[p2++];
        }
    }

    // Phase 2 — copy remainder (only one will execute)
    while (p1 < nums1.length) result[i++] = nums1[p1++];
    while (p2 < nums2.length) result[i++] = nums2[p2++];

    return result;
}
```

**How it works:** The problem is split into two clean phases.

**Phase 1** runs only while *both* arrays have unvisited elements — no exhaustion checks needed, the loop condition itself guarantees safety.

**Phase 2** copies whatever is left. Since one array is already exhausted after phase 1, exactly one of the two cleanup loops will execute (the other runs zero iterations).

### Visual walkthrough — `[0, 3, 4, 31]` and `[4, 6, 30]`

```
Phase 1 — compare and pick the smaller element each step

 p1→  0   3   4  31        p2→  4   6  30
      ──────────────             ──────────────
i=0   0 < 4  → take 0      result: [0]
i=1   3 < 4  → take 3      result: [0, 3]
i=2   4 ≤ 4  → take 4      result: [0, 3, 4]
i=3   31 > 4 → take 4      result: [0, 3, 4, 4]
i=4   31 > 6 → take 6      result: [0, 3, 4, 4, 6]
i=5   31 > 30→ take 30     result: [0, 3, 4, 4, 6, 30]
      p2 exhausted → exit phase 1

Phase 2 — copy remainder of nums1

i=6   copy 31              result: [0, 3, 4, 4, 6, 30, 31] ✓
```

**Why this is the cleanest solution:**
- Phase 1 loop body has **one responsibility only** — compare and pick.
- Phase 2 is a straight copy — **zero comparisons**, no branching.
- No exhaustion flags, no pre-loading, no edge cases inside the main loop.

---

## Why All Solutions Are O(n + m)

Every element from both arrays is visited exactly once. There is no nested looping — each pointer only moves forward. The result array is always exactly `n + m` in size, so space is also O(n + m) with no overhead.

---

## Running the Code

```java
static void main() {
    int[] nums1 = {0, 3, 4, 31};
    int[] nums2 = {4, 6, 8, 15, 30};

    System.out.println(Arrays.toString(mergeSortedArrays(nums1, nums2)));   // [0, 3, 4, 4, 6, 8, 15, 30, 31]
    System.out.println(Arrays.toString(mergeSortedArrays2(nums1, nums2)));  // [0, 3, 4, 4, 6, 8, 15, 30, 31]
    System.out.println(Arrays.toString(mergeSortedArrays3(nums1, nums2)));  // [0, 3, 4, 4, 6, 8, 15, 30, 31]
}
```

---

## Full Source

`src/main/java/dev/me/arrays/problems/MergeSortedArrays.java`