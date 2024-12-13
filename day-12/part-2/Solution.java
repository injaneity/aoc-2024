import java.util.*;
import java.io.*;

public class Solution {

    private static Map<Character, Integer> labelCounts = new HashMap<>();

    public static List<List<Character>> labelConnectedComponents(List<List<Character>> matrix) {

        int rows = matrix.size();
        int cols = matrix.get(0).size();

        boolean[][] visited = new boolean[rows][cols];
        List<List<Character>> labels = new ArrayList<>();
        
        for (int i = 0; i < rows; i++) {
            List<Character> labelRow = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                labelRow.add(matrix.get(i).get(j));

                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    visited[i][j] = true;
                }
            }
            labels.add(labelRow);
        }

        char currentLabel = 'A';

        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {

                if (!visited[i][j]) {

                    dfs(matrix, visited, labels, i, j, matrix.get(i).get(j), currentLabel);
                    currentLabel++;

                }
            }
        }
        

        return labels;
    }

    private static void dfs(List<List<Character>> matrix, boolean[][] visited,
                        List<List<Character>> labels, int row, int col,
                        Character targetChar, char label) {

        final int[][] DIRECTIONS = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
        };

        if (row < 0 || row >= matrix.size() || 
            col < 0 || col >= matrix.get(0).size()) {
            return;
        }
        if (visited[row][col] || 
            !matrix.get(row).get(col).equals(targetChar)) {
            return;
        }

        labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);

        visited[row][col] = true;
        labels.get(row).set(col, label);

        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            dfs(matrix, visited, labels, newRow, newCol, targetChar, label);
        }
    }

    /*
     * Calculates the perimeter for a given region label
     */
    public static int calculateSides(List<List<Character>> labels, Character targetLabel) {
        int sides = 0;
        int rows = labels.size();
        int cols = labels.get(0).size();
    
        for (int row = 0; row < rows; row++) {

            boolean inSideTop = false;
            boolean inSideBottom = false;

            for (int col = 0; col < cols; col++) {

                if (labels.get(row).get(col).equals(targetLabel) && !labels.get(row - 1).get(col).equals(targetLabel)) {
                    if (!inSideTop) {
                        sides++;
                        inSideTop = true;
                    }
                } else {
                    inSideTop = false;
                }
    
                if (labels.get(row).get(col).equals(targetLabel) && !labels.get(row + 1).get(col).equals(targetLabel)) {
                    if (!inSideBottom) {
                        sides++;
                        inSideBottom = true;
                    }
                } else {
                    inSideBottom = false;
                }
            }
        }
    
        for (int col = 0; col < cols; col++) {
            boolean inSideLeft = false;
            boolean inSideRight = false;
            for (int row = 0; row < rows; row++) {

                if (labels.get(row).get(col).equals(targetLabel) && !labels.get(row).get(col - 1).equals(targetLabel)) {
                    if (!inSideLeft) {
                        sides++;
                        inSideLeft = true;
                    }
                } else {
                    inSideLeft = false;
                }
    
                if (labels.get(row).get(col).equals(targetLabel) && !labels.get(row).get(col + 1).equals(targetLabel)) {
                    if (!inSideRight) {
                        sides++;
                        inSideRight = true;
                    }
                } else {
                    inSideRight = false;
                }
            }
        }
    
        return sides;
    }
    
    
    public static void main(String[] args) throws IOException {

        File file = new File("../input.txt");

        List<List<Character>> matrix = new ArrayList<>();
        int len = 0;

        try (Scanner sc = new Scanner(file)) {

            
            while(sc.hasNextLine()) {

                String line = sc.nextLine();
                List<Character> row = new ArrayList<>();
                len = line.length();

                for (int i = 0; i < len; i++) {
                    row.add(line.charAt(i));
                }
                row.add(0, '0');
                row.add('0');

                matrix.add(row);
            }

        }

        matrix.add(0, new ArrayList<>(Collections.nCopies(len + 2, '0')));
        matrix.add(new ArrayList<>(Collections.nCopies(len + 2, '0')));
        List<List<Character>> labelled = labelConnectedComponents(matrix); 
              
        Long sum = 0L;
        for (Character c: labelCounts.keySet()) {
            sum += labelCounts.get(c) * calculateSides(labelled, c);
        }
        System.out.println(sum);

    }
}
