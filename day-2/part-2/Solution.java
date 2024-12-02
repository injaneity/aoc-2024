import java.io.*;
import java.util.*;

public class Solution {

    public static boolean differenceCheck(List<Integer> values, int multiplier) {
        int flag = 0;
        int prev = values.get(0);
        for (int i = 1; i < values.size(); i++) {
            int difference = (values.get(i) - prev) * multiplier;
            if (difference < 1 || difference > 3) {
                if (flag != 0) {
                    return false;
                }
                flag++;
            } else {
                prev = values.get(i);
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
                
                List<Integer> reverseList = new ArrayList<>(valuesList);
                Collections.reverse(reverseList);

                int multiplier = valuesList.get(1) > valuesList.get(0) ? 1 : -1;
                int revMultiplier = reverseList.get(1) > reverseList.get(0) ? 1 : -1;
                if (differenceCheck(valuesList, multiplier) || differenceCheck(reverseList, revMultiplier)) {
                    count++;
                }
            }
        }

        System.out.println(count);
    }
}

