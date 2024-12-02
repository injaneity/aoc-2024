import java.io.*;
import java.util.*;

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

        int count = 0;

        try (Scanner sc = new Scanner(file)) {
            while(sc.hasNextLine()){
                String[] values = sc.nextLine().split(" ");

                List<Integer> valuesList = new ArrayList<>();
                for (int i = 0; i < values.length; i++) {
                    valuesList.add(Integer.parseInt((values[i])));
                }

                int multiplier = valuesList.get(1) > valuesList.get(0) ? 1 : -1;

                if (differenceCheck(valuesList, multiplier)) {
                    count++;
                }
            }
        }

        System.out.println(count);
    }
}

