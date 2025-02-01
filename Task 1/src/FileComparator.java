import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileComparator {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the path to the first file: ");
            String filePath1 = scanner.nextLine();
            System.out.print("Enter the path to the second file: ");
            String filePath2 = scanner.nextLine();

            List<String> lines1 = readFileLines(filePath1);
            List<String> lines2 = readFileLines(filePath2);
            compareFiles(lines1, lines2);
        }
    }

    private static List<String> readFileLines(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
        return lines;
    }

    private static void compareFiles(List<String> list1, List<String> list2) {
        int maxLines = Math.max(list1.size(), list2.size());
        for (int i = 0; i < maxLines; i++) {
            String line1 = i < list1.size() ? list1.get(i) : "File 1 doesn't have this line";
            String line2 = i < list2.size() ? list2.get(i) : "File 2 doesn't have this line";
            if (!line1.equals(line2)) {
                System.out.println("Difference found in line " + (i + 1) + ":");
                System.out.println("File 1: " + line1);
                System.out.println("File 2: " + line2);
            }
        }
    }
}