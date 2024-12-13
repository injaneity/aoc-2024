import java.util.*;
import java.io.*;

public class Solution {

    public static List<Long> calculate(Long input) {

        List<Long> result = new ArrayList<>();
        String string = String.valueOf(input);
        int length = string.length();

        if (input == 0) {
            result.add(1L);

        } else if (length % 2 == 0) {
            result.add(Long.parseLong(string.substring(0, length / 2)));
            result.add(Long.parseLong(string.substring(length / 2, length)));

        } else {
            result.add(input * 2024);
        }

        return result;
    }

    public static void main(String[] args) throws IOException {

        File file = new File("./input.txt");
        Map<Long, Long> map = new HashMap<>();

        try (Scanner sc = new Scanner(file)) {
            while(sc.hasNextLine()) {
                String[] values = sc.nextLine().split(" ");

                for (int i = 0; i < values.length; i++) {
                    Long value = Long.parseLong(values[i]);
                    map.put(value, map.getOrDefault(value, 0L) + 1);
                }
            }
        }

        for (int i = 0; i < 75; i++) {

            Map<Long, Long> tempMap = new HashMap<>();
            for (Long key: map.keySet()) {
                
                List<Long> values = calculate(key);
                for (Long value: values) {
                    tempMap.put(value, tempMap.getOrDefault(value, 0L) + map.get(key));
                }
    
            }
            map = tempMap;
        }

        Long sum = 0L;
        for (Long key: map.keySet()) {
            sum += map.get(key);
        }
        System.out.println(sum);
    }
}
