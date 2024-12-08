import java.util.*;
import java.io.*;

public class Solution {

    public static int loop(List<String> data, int row, int col, int rowDifference, int colDifference, Set<String> visited) {

        int sum = 0;
        int nextRow = row - rowDifference;
        int nextCol = col - colDifference;

        while(nextRow >= 0 && nextRow < data.size() &&
                nextCol >= 0 && nextCol < data.get(nextRow).length()) {

            String state = nextRow + "," + nextCol;
            if (!visited.contains(state)) {
                visited.add(state);
                sum++;
            }

            nextRow -= rowDifference;
            nextCol -= colDifference;
        }
        return sum;
    }

    public static int check(List<String> data, int row, int col, char target, Set<String> visited) {

        int sum = 0;
        
        for (int currentRow = 0; currentRow < data.size(); currentRow++) {

            String line = data.get(currentRow);

            for (int currentCol = 0; currentCol < line.length(); currentCol++) {

                if (line.charAt(currentCol) == target && !(currentRow == row && currentCol == col)) {

                    int rowDifference = currentRow - row;
                    int colDifference = currentCol - col;

                    sum += loop(data, currentRow, currentCol, rowDifference, colDifference, visited);
                    sum += loop(data, currentRow, currentCol, -1 * rowDifference, -1 * colDifference, visited);
                }
            }
        }
        return sum;
    }
    

    public static void main(String[] args) throws IOException {

        File file = new File("../input.txt"); // change filepath as necessary
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
                    sum += check(data, i, j, line.charAt(j), visited);
                }

            }
        }

        System.out.println(sum);
    }
}
