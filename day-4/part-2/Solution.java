import java.util.*;
import java.util.regex.*;
import java.io.*;

public class Solution {

    public static boolean countMAS(List<String> pattern, List<String> input) {
        for (int i = 0; i < 3; i++) {
            if (!input.get(i).matches(pattern.get(i))) {
                return false;
            }
        }
        return true;
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

        for (int i = 0; i < text.size() - 2; i++) {

            String row1 = text.get(i);
            String row2 = text.get(i + 1);
            String row3 = text.get(i + 2);
        
            for (int j = 0; j < row1.length() - 2; j++) {

                List<String> pattern = new ArrayList<>();

                pattern.add("M.S");
                pattern.add(".A.");
                pattern.add("M.S");

                List<String> input = new ArrayList<>();

                input.add(row1.substring(j, j + 3));
                input.add(row2.substring(j, j + 3));
                input.add(row3.substring(j, j + 3));

                if (countMAS(pattern, input)) {
                    count++;
                }
            
                pattern.set(0, "S.S");
                pattern.set(2, "M.M");
                if (countMAS(pattern, input)) {
                    count++;
                }

                pattern.set(0, "S.M");
                pattern.set(2, "S.M");
                if (countMAS(pattern, input)) {
                    count++;
                }

                pattern.set(0, "M.M");
                pattern.set(2, "S.S");
                if (countMAS(pattern, input)) {
                    count++;
                }
            }
        }

        System.out.println(count);
        
    }
}
