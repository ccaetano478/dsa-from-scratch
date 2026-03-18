package dev.me.arrays.problems;

//Create a function that reverses a string

public class ReverseArray {

    public static String reverse(String word){

        if (word == null || word.isEmpty()){
            return word;
        }

        StringBuilder sb = new StringBuilder(word.length()); //allocate

        for(int i=word.length()-1;i>=0;i--){
            sb.append(word.charAt(i));
        }

        return sb.toString();
    }

    public static String reverse2(String word){
        if (word == null || word.isEmpty()){
            return word;
        }
        StringBuilder sb = new StringBuilder(word);
        return sb.reverse().toString();
    }

    public static String reverse3(String word){
        char[] chars = word.toCharArray();

        int left = 0;
        int right = chars.length-1;
        while (left < right){
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }

        return String.valueOf(chars);
    }

    static void main() {
        String word = "abcdefg";
        String newWord = reverse3(word);

        System.out.println(newWord);
    }
}
