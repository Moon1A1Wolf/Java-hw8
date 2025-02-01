import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the file path:");
            String filePath = scanner.nextLine();

            String longestLine = "";
            int maxLength = 0;
            
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.length() > maxLength) {
                        maxLength = line.length();
                        longestLine = line;
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
                return;
            }
            
            System.out.println("Length of the longest line: " + maxLength);
            System.out.println("Longest line: " + longestLine);
        }
    }
}
