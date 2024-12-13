import java.util.*;
import java.io.*;

public class Solution {

    /*
     * Each machine has:
     * Button A: [xA, yA]
     * Button B: [xB, yB]
     * Prize: [xT, yT]
     */
    public static void main(String[] args) throws IOException {

        int finalSum = 0;
        int totalPrizes = 0;
        File file = new File("../input.txt"); // Change filepath as necessary

        try (Scanner sc = new Scanner(file)) {

            while(sc.hasNextLine()) {

                String lineA = sc.nextLine().trim();
                while (lineA.isEmpty() && sc.hasNextLine()) {
                    lineA = sc.nextLine().trim();
                }

                List<Integer> buttonA = parseButton(lineA);

                String lineB = sc.nextLine().trim();
                while (lineB.isEmpty() && sc.hasNextLine()) {
                    lineB = sc.nextLine().trim();
                }

                List<Integer> buttonB = parseButton(lineB);

                String linePrize = sc.nextLine().trim();
                while (linePrize.isEmpty() && sc.hasNextLine()) {
                    linePrize = sc.nextLine().trim();
                }

                List<Integer> prize = parsePrize(linePrize);

                int minCost = findMinCost(buttonA, buttonB, prize);

                if (minCost != Integer.MAX_VALUE) {
                    finalSum += minCost;
                    totalPrizes++;
                }
            }
        }

        System.out.println("Total Prizes Won: " + totalPrizes);
        System.out.println("Minimum Tokens Spent: " + finalSum);
    }

    private static List<Integer> parseButton(String line) {
        List<Integer> button = new ArrayList<>();

        String[] parts = line.split(":")[1].split(",");
        String xString = parts[0].trim().substring(2); // Remove "X+"
        String yString = parts[1].trim().substring(2); // Remove "Y+"

        button.add(Integer.parseInt(xString));
        button.add(Integer.parseInt(yString));
        return button;
    }

    private static List<Integer> parsePrize(String line) {
        List<Integer> prize = new ArrayList<>();

        String[] parts = line.split(":")[1].split(",");
        String xString = parts[0].trim().substring(2); // Remove "X="
        String yString = parts[1].trim().substring(2); // Remove "Y="

        prize.add(Integer.parseInt(xString));
        prize.add(Integer.parseInt(yString));
        return prize;
    }

    private static int findMinCost(List<Integer> buttonA, List<Integer> buttonB, List<Integer> prize) {

        int xA = buttonA.get(0);
        int yA = buttonA.get(1);

        int xB = buttonB.get(0);
        int yB = buttonB.get(1);
        
        int xT = prize.get(0);
        int yT = prize.get(1);

        int minCost = Integer.MAX_VALUE;

        for (int a = 0; a <= 100; a++) {

            int remainingX = xT - (a * xA);
            if (remainingX < 0) continue;

            if (xB == 0) {
                if (remainingX != 0) continue;
            } else {
                if (remainingX % xB != 0) continue;
            }

            int b = 0;
            if (xB != 0) {
                b = remainingX / xB;
            }
            if (b < 0 || b > 100) {
                continue; 
            }

            int totalY = (a * yA) + (b * yB);
            if (totalY != yT) {
                continue;
            }

            int cost = (3 * a) + b;
            if (cost < minCost) {
                minCost = cost;
            }
        }

        return minCost;
    }
}
