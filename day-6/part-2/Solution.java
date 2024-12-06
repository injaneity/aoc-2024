import java.util.*;
import java.io.*;

public class Solution {

    public static boolean loop(int row, int col, List<String> data, char direction) {

        Set<String> visited = new HashSet<>();

        while (row >= 0 && row < data.size() 
                    && col >= 0 && col < data.get(0).length()) {

            String state = row + "," + col + "," + direction;
            if (visited.contains(state)) {
                return true;
            }

            visited.add(state);

            switch(direction) {
                case 'v':
                    if (row + 1 < data.size() && 
                                data.get(row + 1).charAt(col) == '#') {
                        direction = '<';
                    } else {
                        row++;
                    }
                    break;

                case '>':
                    if (col + 1 < data.get(0).length() && 
                                data.get(row).charAt(col + 1) == '#') {
                        direction = 'v';
                    } else {
                        col++;
                    }
                    break;

                case '^':
                    if (row - 1 >= 0 && 
                                    data.get(row - 1).charAt(col) == '#') {
                        direction = '>';
                    } else {
                        row--;
                    }
                    break;

                case '<':
                    if (col - 1 >= 0 && 
                                data.get(row).charAt(col - 1) == '#') {
                        direction = '^';
                    } else {
                        col--;
                    }
                    break;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {

        String start = "^>v<";
        char direction = '^';

        File file = new File("../input.txt");
        List<String> data = new ArrayList<>();

        boolean started = false;
        int row = 0;
        int col = 0;

        try (Scanner sc = new Scanner(file)) {
            int currentRow = 0;
            while (sc.hasNextLine()) {

                String line = sc.nextLine();

                if (!started) {

                    for (int i = 0; i < line.length(); i++) {
                        char c = line.charAt(i);

                        if (start.indexOf(c) != -1) {
                            row = currentRow;
                            col = i;
                            direction = c;
                            started = true;
                            break;
                        }

                    }
                }
                data.add(line);
                currentRow++;
            }
        }

        int count = 0;

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(0).length(); j++) {

                if (data.get(i).charAt(j) == '.' && !(i == row && j == col)) {

                    String original = data.get(i);
                    char[] charArray = original.toCharArray();
                    charArray[j] = '#';
                    data.set(i, new String(charArray));

                    if (loop(row, col, data, direction)) {
                        count++;
                    }

                    data.set(i, original);
                }
            }
        } 

        System.out.println(count);
    }
}
