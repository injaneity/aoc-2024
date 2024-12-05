import java.util.*;
import java.io.*;

public class Solution {

    /*
     * Probably simpler to do this than store that cursed hashmap
     */
    static List<String[]> conditions = new ArrayList<>();
    
    static int ans = 0;

    public static boolean check(List<String> order) {
        for (int i = 1; i < order.size(); i++) {
            for (int j = 0; j < i; j++) {
                String prev = order.get(j);
                String current = order.get(i);
                for (String[] condition : conditions) {
                    if (condition[0].equals(current) && condition[1].equals(prev)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void fix(List<String> order) {

        List<String> filteredConditions = new ArrayList<>();
        Set<String> orderSet = new HashSet<>(order);
        
        for (String[] condition : conditions) {
            if (orderSet.contains(condition[0]) && orderSet.contains(condition[1])) {
                filteredConditions.add(condition[0]);
            }
        }

        Map<Integer, String> arr = new HashMap<>();
        for (String element : order) {
            int count = 0;
            for (String fc : filteredConditions) {
                if (fc.equals(element)) {
                    count++;
                }
            }
            arr.put(count, element);
        }

        int middleIndex = order.size() / 2;
        String middleElement = arr.get(middleIndex);
        
        if (middleElement != null) {
            ans += Integer.parseInt(middleElement);
        }
    }

    public static void main(String[] args) throws IOException {

        File file = new File("../input.txt");

        try (Scanner sc = new Scanner(file)) {

            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                if (line.length() == 0) break;

                String[] pair = line.trim().split("\\|");
                conditions.add(new String[]{pair[0].trim(), pair[1].trim()});

            }

            while (sc.hasNextLine()) {

                String line = sc.nextLine();

                String[] sequence = line.trim().split(",");
                List<String> order = new ArrayList<>();
                for (String s : sequence) {
                    order.add(s.trim());
                }

                if (!check(order)) {
                    fix(order);
                }
            }
            System.out.println(ans);

        }
    }
}
