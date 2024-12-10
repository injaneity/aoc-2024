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

        Map<String, Integer> memo = new HashMap<>();

        int totalRating = 0;

        for (int[] trailhead : trailheads) {
            int startI = trailhead[0];
            int startJ = trailhead[1];
            
            String startKey = startI + "," + startJ;
            int pathsFromTrailhead = countPaths(startI, startJ, adjMap, memo, nines);
            totalRating += pathsFromTrailhead;
        }

        System.out.println(totalRating);
    }

    static int countPaths(int i, int j, List<List<List<int[]>>> adjMap, Map<String, Integer> memo, Set<String> nines) {
        String key = i + "," + j;
        
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        if (nines.contains(key)) {
            memo.put(key, 1);
            return 1;
        }
        
        int totalPaths = 0;
        
        for (int[] neighbor : adjMap.get(i).get(j)) {
            totalPaths += countPaths(neighbor[0], neighbor[1], adjMap, memo, nines);
        }
        
        memo.put(key, totalPaths);
        
        return totalPaths;
    }
}
