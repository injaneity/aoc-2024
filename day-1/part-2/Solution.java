import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        File file = new File("../input.txt");

        List<Integer> left = new ArrayList<>();
        Map<Integer, Long> right = new HashMap<>();

        try (Scanner sc = new Scanner(file)) {
            while(sc.hasNextLine()){
                String[] values = sc.nextLine().split(" {3}");
                left.add(Integer.parseInt(values[0]));
                int index = Integer.parseInt(values[1]);
                right.put(index, right.getOrDefault(index, 0L) + 1);
            }
        }

        Collections.sort(left);

        Long sum = 0L;
        for (int value: left) {
            if (right.containsKey(value)) {
                sum += value * right.get(value);
            }
        }
        System.out.println(sum);
    }
}

