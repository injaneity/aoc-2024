import java.util.*;
import java.io.*;

public class Solution {

    public static void main(String[] args) throws IOException {

        File file = new File("../input.txt");
        List<String> lines = new ArrayList<>();
        
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        }

        if (lines.isEmpty()) {
            System.out.println("0");
            return;
        }

        int rows = lines.size();
        int cols = lines.get(0).length();
        int[][] map = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < cols; j++) {
                map[i][j] = line.charAt(j) - '0';
            }
        }

        List<int[]> trailheads = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 0) {
                    trailheads.add(new int[]{i, j});
                }
            }
        }

        if (trailheads.isEmpty()) {
            System.out.println("0");
            return;
        }

        /*
         * Use the directions to allocate adjacent nodes
         */
        int[][] directions = { {-1,0}, {1,0}, {0,-1}, {0,1} };

        List<List<List<int[]>>> adjMap = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            List<List<int[]>> rowList = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                rowList.add(new ArrayList<>());
            }
            adjMap.add(rowList);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int[] dir : directions) {
                    int ni = i + dir[0];
                    int nj = j + dir[1];
                    if (ni >= 0 && ni < rows && nj >= 0 && nj < cols) {
                        if (map[ni][nj] == map[i][j] + 1) {
                            adjMap.get(i).get(j).add(new int[]{ni, nj});
                        }
                    }
                }
            }
        }

        Set<String> nines = new HashSet<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 9) {
                    nines.add(i + "," + j);
                }
            }
        }

        if (nines.isEmpty()) {
            System.out.println("0");
            return;
        }

        int totalScore = 0;

        for (int[] trailhead : trailheads) {
            int startI = trailhead[0];
            int startJ = trailhead[1];
            
            Set<String> visited = new HashSet<>();
            Set<String> reachableNines = new HashSet<>();
            
            dfs(startI, startJ, adjMap, visited, nines, reachableNines);
            
            totalScore += reachableNines.size();
        }

        System.out.println(totalScore);
    }

    static void dfs(int i, int j, List<List<List<int[]>>> adjMap, Set<String> visited, Set<String> nines, Set<String> reachableNines) {
        String key = i + "," + j;
        if (visited.contains(key)) {
            return;
        }
        visited.add(key);
        
        if (nines.contains(key)) {
            reachableNines.add(key);
        }
        
        for (int[] neighbor : adjMap.get(i).get(j)) {
            dfs(neighbor[0], neighbor[1], adjMap, visited, nines, reachableNines);
        }
    }
}
