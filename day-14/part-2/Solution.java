import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Solution {

    /*
     * I did not know this existed, GPTed the solution
     */
    public static void generateImage(int[][] grid, int rows, int cols, int iteration, int cellSize, String outputDir) throws IOException {
        int width = cols * cellSize;
        int height = rows * cellSize;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (grid[y][x] > 0) {
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }

        g.dispose();

        Files.createDirectories(Paths.get(outputDir));

        String filename = String.format("%s/iteration_%05d.png", outputDir, iteration);
        ImageIO.write(image, "png", new File(filename));
    }

    public static int countQuadrant(int startRow, int endRow, 
                                    int startCol, int endCol, int[][] grid) {
        int count = 0;
        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j < endCol; j++) {
                count += grid[i][j];
            }
        }
        return count;
    }

    public static class Robot {

        int currentX;
        int currentY;

        int deltaX;
        int deltaY;

        public Robot(List<String> coordinates, List<String> velocities) {
            
            currentX = Integer.parseInt(coordinates.get(0));
            currentY = Integer.parseInt(coordinates.get(1));
            
            deltaX = Integer.parseInt(velocities.get(0));
            deltaY = Integer.parseInt(velocities.get(1));
        }

        @Override
        public String toString() {
            return String.format("[%2d %2d] [%2d %2d]", currentX, currentY, deltaX, deltaY);
        }
    }

    public static void move(Map<String, Set<Robot>> matrix, int rows, int cols, Robot robot) {

        String currentCoord = robot.currentX + "," + robot.currentY;
        Set<Robot> robotsAtCurrent = matrix.getOrDefault(currentCoord, new HashSet<>());
        robotsAtCurrent.remove(robot);
        if (robotsAtCurrent.isEmpty()) {
            matrix.remove(currentCoord);
        } else {
            matrix.put(currentCoord, robotsAtCurrent);
        }
        
        int newX = robot.currentX + robot.deltaX;
        int newY = robot.currentY + robot.deltaY;

        newX = ((newX % cols) + cols) % cols;
        newY = ((newY % rows) + rows) % rows;

        robot.currentX = newX;
        robot.currentY = newY;

        String newCoord = robot.currentX + "," + robot.currentY;
        Set<Robot> robotsAtNew = matrix.getOrDefault(newCoord, new HashSet<>());
        robotsAtNew.add(robot);
        matrix.put(newCoord, robotsAtNew);
    }

    public static void main(String[] args) throws IOException {

        File file = new File("../input.txt");
        List<Robot> robots = new ArrayList<>();

        int rows = 103;
        int cols = 101;
        try (Scanner sc = new Scanner(file)) {
            while(sc.hasNextLine()) {

                String[] line = sc.nextLine().trim().split(" ");

                Robot robot = new Robot(
                        Arrays.asList(line[0].replace("p=", "").split(",")), 
                        Arrays.asList(line[1].replace("v=", "").split(","))
                );
                
                robots.add(robot);
            }
        }

        Map<String, Set<Robot>> matrix = new HashMap<>();
    
        for (Robot robot: robots) {

            String coord = robot.currentX + "," + robot.currentY;
            Set<Robot> currentRobots = matrix.getOrDefault(coord, new HashSet<>());
            currentRobots.add(robot);
            matrix.put(coord, currentRobots);
        }

        for (int i = 0; i < 10000; i++) {
            for (Robot robot: robots) {
                move(matrix, rows, cols, robot);
            }

            /*
             * Convert to image
             */
            int[][] grid = new int[rows][cols];
            for (Map.Entry<String, Set<Robot>> entry : matrix.entrySet()) {

                String[] parts = entry.getKey().split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);

                grid[y][x] = entry.getValue().size();
            }
            generateImage(grid, rows, cols, i, 10, "../images");
        }

        

        

    }
}
