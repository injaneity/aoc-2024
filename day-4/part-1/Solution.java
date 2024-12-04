import java.util.*;
import java.util.regex.*;
import java.io.*;

public class Solution {

    public static int checkForwardBackward(String input) {

        int count = 0;

        Pattern pattern = Pattern.compile("XMAS");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            count++;
        }

        pattern = Pattern.compile("SAMX");
        matcher = pattern.matcher(input);
        while (matcher.find()) {
            count++;
        }

        return count;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("../input.txt");

        int count = 0;

        /*
         * Create 2D Arraylist of Strings
         */
        List<String> text = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                text.add(sc.nextLine());
            }
        }

        /*
         * Check forward and backward
         */
        for (String s: text) {
            count += checkForwardBackward(s);
        }

        /*
         * Tranpose the 2D matrix
         */
        for (int i = 0; i< text.size(); i++) {

            String rowString = "";
            for (int j = 0; j < text.get(0).length(); j++) {
                rowString += text.get(j).charAt(i);
            }

            count += checkForwardBackward(rowString);
        }

        /*
         * Obtain all main diagonals of 2D matrix
         */
        for (int i = 0; i < text.get(0).length(); i++) {

            String diagonalString = "";
            int row = 0;
            int col = i;

            while (row < text.size() && col >= 0) {
                diagonalString += text.get(row).charAt(col);
                row++;
                col--;
            }

            count += checkForwardBackward(diagonalString.toString());
        }

        for (int i = 1; i < text.size(); i++) {

            String diagonalString = "";
            int row = i;
            int col = text.get(0).length() - 1;

            while (row < text.size() && col >= 0) {
                diagonalString += text.get(row).charAt(col);
                row++;
                col--;
            }

            count += checkForwardBackward(diagonalString.toString());
        }

        /*
         * Obtain all anti diagonals of 2D matrix
         */
        for (int i = text.get(0).length() - 1; i >= 0; i--) {

            String antiDiagonalString = "";
            int row = 0;
            int col = i;

            while (row < text.size() && col < text.get(0).length()) {
                antiDiagonalString += text.get(row).charAt(col);
                row++;
                col++;
            }

            count += checkForwardBackward(antiDiagonalString.toString());
        }
        
        for (int i = 1; i < text.size(); i++) {

            String antiDiagonalString = "";
            int row = i;
            int col = 0;

            while (row < text.size() && col < text.get(0).length()) {
                antiDiagonalString += text.get(row).charAt(col);
                row++;
                col++;
            }

            count += checkForwardBackward(antiDiagonalString.toString());
        }

        System.out.println(count);
    }
}
