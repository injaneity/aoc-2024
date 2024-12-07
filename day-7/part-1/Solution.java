import java.util.*;
import java.io.*;

public class Solution {

    public static void main(String[] args) throws IOException {

        File file = new File("../input.txt");
        
        Long ans = 0L;

        try (Scanner sc = new Scanner(file)) {

            while(sc.hasNextLine()) {

                String[] equation = sc.nextLine().trim().split(":");
                String[] numbers = equation[1].trim().split(" ");
                Long result = Long.parseLong(equation[0]);

                for (int i = 0; i < (int)Math.pow(2, numbers.length - 1); i++) {

                    Long sum = Long.parseLong(numbers[0]);

                    String binaryRepresentation = String.format("%" + (numbers.length - 1) + "s", 
                                                    Integer.toBinaryString(i)).replace(' ', '0');

                    for (int j = 0; j < binaryRepresentation.length(); j++) {

                        if (binaryRepresentation.charAt(j) == '0') {
                            sum += Long.parseLong(numbers[j + 1]);
                        } else {
                            sum *= Long.parseLong(numbers[j + 1]);
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
