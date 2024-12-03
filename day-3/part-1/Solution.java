import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Solution {

    public static boolean differenceCheck(List<Integer> values, int multiplier) {
        for (int i = 0; i < values.size() - 1; i++) {
            int difference = (values.get(i+1) - values.get(i)) * multiplier;
            if (difference < 1 || difference > 3) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) throws IOException {
        File file = new File("../input.txt");

        int sum = 0;
        try (Scanner sc = new Scanner(file)) {
            while(sc.hasNextLine()){
                /*
                 * Regex to find mul({number},{number})
                 */
                Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
                Matcher matcher = pattern.matcher(sc.nextLine());
                
                List<String> list = new ArrayList<>();
                while (matcher.find()) {
                    list.add(matcher.group());
                }

                for (String substring: list) {
                    String clean = substring.substring(4, substring.length() - 1);
                    String[] values = clean.split(",");
                    if (values.length == 2) {
                        sum += Integer.parseInt(values[0]) * Integer.parseInt(values[1]);
                    } 
                }
            }
        }
        System.out.println(sum);
    }
}

