package dev.me.arrays.problems;

import java.util.HashMap;
import java.util.Map;

public class UniqueCharInString {

    //using hashmap approach
    public static boolean isUnique (String word) {
        Map<Character, Boolean> seenLetters = new HashMap();


        for (int i = 0; i < word.length(); i++){
            Character currentLetter = word.charAt(i);
            boolean seen = seenLetters.get(currentLetter) != null ? seenLetters.get(currentLetter) : false;

            if (seen) return false;

            seenLetters.put(currentLetter,  true);
        }

        return true;
    }

    //using array of bits
    public static boolean isUniqueV2 (String word) {
        boolean[] seen = new boolean[128];
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i);
            if (seen[c]) return false;
            seen[c] = true;
        }
        return true;
    }



    static void main() {
        String word = "data";

        Boolean hasAllUniqueLetters = isUnique(word);
        Boolean hasAllUniqueLettersv2 = isUniqueV2(word);

        System.out.println(hasAllUniqueLetters);
        System.out.println(hasAllUniqueLettersv2);
    }

}
