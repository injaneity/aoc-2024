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

        long finalSum = 0;
        int totalPrizes = 0;
        File file = new File("../input.txt"); // Change filepath as necessary

        try (Scanner sc = new Scanner(file)) {

            while(sc.hasNextLine()) {

                String lineA = readNextNonEmptyLine(sc);
                if (lineA == null) break;
                List<Long> buttonA = parseButton(lineA);

                String lineB = readNextNonEmptyLine(sc);
                if (lineB == null) break; // No more data
                List<Long> buttonB = parseButton(lineB);

                String linePrize = readNextNonEmptyLine(sc);
                if (linePrize == null) break; // No more data
                List<Long> prize = parsePrize(linePrize);

                Long minCost = findMinCost(buttonA, buttonB, prize);

                if (minCost != null) {
                    finalSum += minCost;
                    totalPrizes++;
                }
            }
        }

        System.out.println("Total Prizes Won: " + totalPrizes);
        System.out.println("Minimum Tokens Spent: " + finalSum);
    }

    private static String readNextNonEmptyLine(Scanner sc) {
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) return line;
        }
        return null;
    }

    private static List<Long> parseButton(String line) {
        List<Long> button = new ArrayList<>();
        String[] parts = line.split(":")[1].split(",");
        String xString = parts[0].trim().substring(2); // Remove "X+"
        String yString = parts[1].trim().substring(2); // Remove "Y+"
        button.add(Long.parseLong(xString));
        button.add(Long.parseLong(yString));
        return button;
    }

    private static List<Long> parsePrize(String line) {
        List<Long> prize = new ArrayList<>();
    
        String[] parts = line.split(":")[1].split(",");
        long xOriginal = Long.parseLong(parts[0].trim().substring(2)); // Remove "X="
        long yOriginal = Long.parseLong(parts[1].trim().substring(2)); // Remove "Y="
    
        long xT = 10000000000000L + xOriginal;
        long yT = 10000000000000L + yOriginal;
    
        prize.add(xT);
        prize.add(yT);
        return prize;
    }
    

    private static Long findMinCost(List<Long> buttonA, List<Long> buttonB, List<Long> prize) {

        long xA = buttonA.get(0);
        long yA = buttonA.get(1);

        long xB = buttonB.get(0);
        long yB = buttonB.get(1);

        long xT = prize.get(0);
        long yT = prize.get(1);

        long D = xA * yB - xB * yA;

        /*
         * Note that Cramer's rule only works for unique solutions
         * where determinant is not 0
         */

        if (D == 0) {
            return null;
        }

        /*
         * Substitute target into column A
         */
        double aDouble = (double)(xT * yB - xB * yT) / D;

        /*
         * Substitute target into column B
         */
        double bDouble = (double)(xA * yT - yA * xT) / D;

        /*
         * Integer solution since you cant press the button multiple times
         */
        if (isInteger(aDouble) && isInteger(bDouble)) {
            long a = (long) aDouble;
            long b = (long) bDouble;

            if (a >= 0 && b >= 0) {
                return 3 * a + b;
            }
        }

        return null;
    }

    private static boolean isInteger(double d) {
        return Math.floor(d) == d;
    }
}
