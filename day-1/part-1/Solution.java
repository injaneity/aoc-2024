import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        File file = new File("../input.txt");

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        try (Scanner sc = new Scanner(file)) {
            while(sc.hasNextLine()){
                String[] values = sc.nextLine().split("   ");
                left.add(Integer.parseInt(values[0]));
                right.add(Integer.parseInt(values[1]));
            }
        }

        Collections.sort(left);
        Collections.sort(right);

        long sum = 0;
        for (int i = 0; i < Math.min(left.size(), right.size()); i++) {
            sum += Math.abs(left.get(i) - right.get(i));
        }
        System.out.println(sum);
    }
}

