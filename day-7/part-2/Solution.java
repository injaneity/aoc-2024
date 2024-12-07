import java.util.*;
import java.io.*;

public class Solution {

    public static String base3Representation(int number, int length) {

        StringBuilder base3 = new StringBuilder();
        
        while (number > 0) {
            base3.append(number % 3);
            number /= 3;
        }
        
        while (base3.length() < length) {
            base3.append('0');
        }
        
        return base3.reverse().toString();
    }
    public static void main(String[] args) throws IOException {

        File file = new File("../input.txt");
        
        Long ans = 0L;

        try (Scanner sc = new Scanner(file)) {

            while(sc.hasNextLine()) {

                String[] equation = sc.nextLine().trim().split(":");
                String[] numbers = equation[1].trim().split(" ");
                Long result = Long.parseLong(equation[0]);
                
                for (int i = 0; i < (int)Math.pow(3, numbers.length - 1); i++) {

                    String base3Representation = base3Representation(i, numbers.length - 1);

                    /*
                     * Same as part 1
                     */
                    Long sum = Long.parseLong(numbers[0]);

                    for (int j = 0; j < base3Representation.length(); j++) {

                        switch(base3Representation.charAt(j)) {

                            case '0':
                                sum += Long.parseLong(numbers[j + 1]);
                                break;

                            case '1':
                                sum *= Long.parseLong(numbers[j + 1]);
                                break;

                            case '2':
                                sum = Long.parseLong(String.valueOf(sum) + numbers[j + 1]);
                                break;
                        }
                    }

                    if (sum.equals(result)) {
                        ans += result;
                        break;
                    }

                }
            }
        }

        System.out.println(ans);

    }
}
