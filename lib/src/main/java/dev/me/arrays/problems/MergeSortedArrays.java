package dev.me.arrays.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
# Merge Sorted Arrays

Given two sorted integer arrays, implement a function `mergeSortedArrays` that merges them into a single sorted array.

## Signature

mergeSortedArrays(int[] a, int[] b) → int[]

## Example

Input:  [0, 3, 4, 31]  and  [4, 6, 30]
Output: [0, 3, 4, 4, 6, 30, 31]

## Constraints

- Both input arrays are already sorted in ascending order
- The returned array must also be sorted in ascending order
- Duplicate values across arrays are allowed and must be preserved
- Do not use a built-in sort function on the merged result
 */
public class MergeSortedArrays {

    public static int[] mergeSortedArrays(int[] nums1, int[] nums2){
        if (nums1 == null || nums2 == null){ return nums1;}

        if (nums1.length == 0){ return nums2;}
        if (nums2.length == 0){ return nums1;}

        int pointerNums1 = 0;
        int pointerNums2 = 0;

        int index = 0;

        int[] result = new int[nums1.length + nums2.length];

        while (index < result.length){

            // Se nums1 esgotou, copia o restante de nums2
            if (pointerNums1 == nums1.length) {
                result[index] = nums2[pointerNums2];
                pointerNums2++;
                index++;
            // Se nums2 esgotou, copia o restante de nums1
            } else if (pointerNums2 == nums2.length) {
                result[index] = nums1[pointerNums1];
                pointerNums1++;
                index++;
            // Caso normal: compara e pega o menor
            } else if (nums1[pointerNums1] <= nums2[pointerNums2]) {
                result[index] = nums1[pointerNums1];
                pointerNums1++;
                index ++;
            } else {
                result[index] = nums2[pointerNums2];
                pointerNums2++;
                index ++;
            }
        }

        return result;
    }


    public static int[] mergeSortedArrays2(int[] nums1, int[] nums2){
        if (nums1.length == 0){ return nums2;}
        if (nums2.length == 0){ return nums1;}

        int nums1Item = nums1[0];
        int nums2Item = nums2[0];

        int i = 1, j = 1;

        List <Integer> result = new ArrayList<>();

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

    public static int[] mergeSortedArrays3(int[] nums1, int[] nums2){
        if (nums1.length == 0) return nums2;
        if (nums2.length == 0) return nums1;

        int[] result = new int[nums1.length + nums2.length];
        int p1 = 0, p2 = 0, i = 0;

        while (p1 < nums1.length && p2 < nums2.length) {
            if (nums1[p1] <= nums2[p2]) {
                result[i++] = nums1[p1++];
            } else {
                result[i++] = nums2[p2++];
            }
        }

        while (p1 < nums1.length) result[i++] = nums1[p1++];
        while (p2 < nums2.length) result[i++] = nums2[p2++];

        return result;
    }


    static void main() {

        int[] nums1 = {0, 3, 4, 31};
        int[] nums2 = {4, 6, 8, 15, 30};
        int[] result = mergeSortedArrays3(nums1, nums2);

        System.out.println(Arrays.toString(result));
    }

}
