import java.util.*;
import java.io.*;

public class Solution {

    public static int check(List<String> data, int row, int col, char target, Set<String> visited) {

        int sum = 0;
        
        for (int currentRow = 0; currentRow < data.size(); currentRow++) {
            String line = data.get(currentRow);

            for (int currentCol = 0; currentCol < line.length(); currentCol++) {

                if (line.charAt(currentCol) == target && !(currentRow == row && currentCol == col)) {

                    int rowDifference = currentRow - row;
                    int colDifference = currentCol - col;

                    /*
                     * Check Behind
                     */
                    int behindRow = row - rowDifference;
                    int behindCol = col - colDifference;
                    if (behindRow >= 0 && behindRow < data.size() &&
                        behindCol >= 0 && behindCol < data.get(behindRow).length()) {
                        
                        String state = behindRow + "," + behindCol;
                        if (!visited.contains(state)) {
                            visited.add(state);
                            sum++;
                        }
                    }
                    
                    /*
                     * Check Ahead
                     */
                    int aheadRow = currentRow + rowDifference;
                    int aheadCol = currentCol + colDifference;
                    if (aheadRow >= 0 && aheadRow < data.size() &&
                        aheadCol >= 0 && aheadCol < data.get(aheadRow).length()) {
                        
                        String state = aheadRow + "," + aheadCol;
                        if (!visited.contains(state)) {
                            visited.add(state);
                            sum++;
                        }
                    }
                }
            }
        }
        return sum;
    }
    

    public static void main(String[] args) throws IOException {

        File file = new File("../test.txt"); // change filepath as necessary
        List<String> data = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        int sum = 0;

        try (Scanner sc = new Scanner(file)) {
            while(sc.hasNextLine()) {
                data.add(sc.nextLine());
            }
        }

        for (int i = 0; i < data.size(); i++) {
            String line = data.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (Character.isLetterOrDigit(line.charAt(j))) {
                    /*
                     * Only search ahead for corresponding nodes
                     */
                    sum += check(data, i, j, line.charAt(j), visited);
                }
            }
        }

        System.out.println(sum);
    }
}
