import java.util.*;
import java.io.*;

public class Solution {

    public static boolean containsPair(String[] sequence,
                                Map<Integer, List<Integer>> pairings) {

        for (int i = sequence.length - 1; i >= 0; i--) {

            List<Integer> mapping = pairings.get(Integer.parseInt(sequence[i]));
            if (mapping == null) continue;
            int idx = i - 1;

            while (idx >= 0) {
                if (mapping.contains(Integer.parseInt(sequence[idx]))) {
                    System.out.println();
                    return true;
                }
                idx--;
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {

        File file = new File("../input.txt");
        int sum = 0;

        Map<Integer, List<Integer>> pairings = new HashMap<>();

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                if (line.length() == 0) break;

                String[] pair = line.split("\\|");
                Integer index = Integer.parseInt(pair[0]);
                Integer value = Integer.parseInt(pair[1]);

                List<Integer> pairing = pairings.getOrDefault(index, new ArrayList<>());
                if (!pairing.contains(value)) {
                    pairing.add(value);
                }
                pairings.put(index, pairing);
            }

            while (sc.hasNextLine()) {
                String[] sequence = sc.nextLine().split(",");
                
                if (!containsPair(sequence, pairings)) {
                    sum += Integer.parseInt(sequence[sequence.length/2]);
                }
            }
        }

        System.out.println(sum);

    }
}
