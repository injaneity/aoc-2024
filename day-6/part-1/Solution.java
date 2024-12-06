import java.util.*;
import java.io.*;

public class Solution {
    public static void main(String[] args) throws IOException {

        String start = "^>v<";
        char direction = '^';

        File file = new File("../input.txt");

        List<String> data = new ArrayList<>();

        boolean started = false;

        int row = 0;
        int col = 0;
        int currentRow = 0;

        try (Scanner sc = new Scanner(file)) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!started) {
                    for (int i = 0; i < start.length(); i++) {

                        if (line.contains("" + start.charAt(i))) {

                            row = currentRow;
                            col = line.indexOf(start.charAt(i));

                            direction = start.charAt(i);
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
        
        while (row >= 0 && row < data.size() 
                    && col >= 0 && col < data.get(0).length()) {

            char[] charArray = data.get(row).toCharArray();
            charArray[col] = 'X';
            data.set(row, String.valueOf(charArray));

            /*
             * Move
             */
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

                    } else col++;
                    break;

                case '^':

                    if (row - 1 >= 0 && 
                            data.get(row - 1).charAt(col) == '#') {
                        direction = '>';

                    } else row--;
                    break;
                
                case '<':
                    if (col - 1 >= 0 && 
                            data.get(row).charAt(col - 1) == '#') {
                        direction = '^';

                    } else col--;
                    break;
            }
        }
        
        for (String s: data) {
            System.out.print(s);
            for (Character c: s.toCharArray()) {
                if (c == 'X') count++;
            }
        }

        System.out.println(count);
    }
}
