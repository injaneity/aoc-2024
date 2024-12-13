import java.util.*;
import java.io.*;

public class Solution {

    public static long checksum(List<List<Integer>> blocks) {
        long sum = 0;
        long position = 0;
    
        for (List<Integer> block : blocks) {
            int fileId = block.get(0);
            int length = block.get(1);
    
            if (fileId > -1) {
                for (int j = 0; j < length; j++) {
                    sum += fileId * position;
                    position++;
                }
            } else {
                position += length;
            }
        }
    
        return sum;
    }
    

    public static void main(String[] args) throws IOException {

        File file = new File("../test.txt");
    
        try (Scanner sc = new Scanner(file)) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                List<List<Integer>> clean = new ArrayList<>();
    
                int i = 0;
                int idx = 0;
    
                while (i < line.length()) {
                    List<Integer> list = new ArrayList<>();

                    int fileLength = Character.getNumericValue(line.charAt(i));
                
                    if (i % 2 == 0) {
                        list.add(idx++);                        
                    } else {
                        list.add(-1);
                    }
                    list.add(fileLength);
                
                    clean.add(list);
                    i++;
                }
                
                int lastIdx = clean.size() - 1;

                while (lastIdx > 0) {

                    List<Integer> pair = clean.get(lastIdx);

                    int fileId = pair.get(0);
                    int fileLength = pair.get(1);
                    
                    if (fileId > 0) {

                        for (int j = 0; j < lastIdx; j++) {

                            if (clean.get(j).get(0) == -1) {

                                int gap = clean.get(j).get(1);

                                if (fileLength <= gap) {
                                    clean.set(j, pair);

                                    List<Integer> leftover = new ArrayList<>(pair);
                                    leftover.set(0, -1);
                                    clean.set(lastIdx, leftover);

                                    if (gap - fileLength > 0) {
                                        List<Integer> space = new ArrayList<>();
                                        space.add(-1);
                                        space.add(gap - fileLength);
                                        clean.add(j + 1, space);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    String visualise = "";
                    for (int j = 0; j < clean.size(); j++) {
                        for (int k = 0; k < clean.get(j).get(1); k++) {
                            visualise += clean.get(j).get(0);
                        }
                    }
                    System.out.println(visualise.replace("-1", "."));
                    lastIdx--;

                }    
                System.out.println(checksum(clean));            
            }

        }
    }
    
}
