import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) throws IOException {
        File file = new File("../input.txt");

        long sum = 0;
        boolean mulEnabled = true;

        /*
         * Use of d+ find to find mul(X,Y)
         */
        String regex = "mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)";
        Pattern pattern = Pattern.compile(regex);

        try (Scanner sc = new Scanner(file)) {

            while (sc.hasNextLine()) {

                String line = sc.nextLine();

                /*
                 * Loops through with regex to handle last seen
                 */
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {

                    String match = matcher.group();

                    if (match.startsWith("mul(")) {
                        if (mulEnabled) {
                            int x = Integer.parseInt(matcher.group(1));
                            int y = Integer.parseInt(matcher.group(2));
                            sum += (long) x * y;
                        }

                    } else if (match.equals("do()")) {
                        mulEnabled = true;

                    } else if (match.equals("don't()")) {
                        mulEnabled = false;
                    }
                }
            }
        }

        System.out.println(sum);
    }
}
