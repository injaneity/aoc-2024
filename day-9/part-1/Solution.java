import java.util.*;
import java.io.*;

public class Solution {

    public static long checksum(List<Integer> blocks) {
        long sum = 0;
        for (int i = 0; i < blocks.size(); i++) {
            int fileId = blocks.get(i);
            if (fileId != -1) {
                sum += (long) i * fileId;
            }
        }
        return sum;
    }
    

    public static void main(String[] args) throws IOException {

        File file = new File("../test.txt");
    
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                List<Integer> clean = new ArrayList<>();
    
                int i = 0;
                int idx = 0;
    
                while (i < line.length()) {

                    if (i % 2 == 0) { // File length
                        int fileLength = Integer.parseInt("" + line.charAt(i));
                        for (int j = 0; j < fileLength; j++) {
                            clean.add(idx);
                        }
                        idx++;
                    } else { // Free space
                        int freeLength = Integer.parseInt("" + line.charAt(i));
                        for (int j = 0; j < freeLength; j++) {
                            clean.add(-1); // Use -1 to represent free space
                        }
                    }
                    i++;
                }
    
                List<Integer> shifted = new ArrayList<>(clean);
                int lastIndex = shifted.size() - 1;
    
                i = 0;
                while (i < lastIndex) {
                    
                    if (shifted.get(i) == -1) {
                        while (lastIndex >= 0 && shifted.get(lastIndex) == -1) {
                            lastIndex--;
                        }
                        if (lastIndex < i) break;
                        
                        shifted.set(i, shifted.get(lastIndex));
                        shifted.set(lastIndex, -1);
                        lastIndex--;
                    }
                    i++;
                }
    
                System.out.println(checksum(shifted));
            }
        }
    }
    
}
