import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArrayToFile {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter file path: ");
            String filePath = scanner.nextLine();

            System.out.print("Enter array elements (space-separated): ");
            String[] input = scanner.nextLine().split("\\s+");
            int[] numbers = new int[input.length];

            for (int i = 0; i < input.length; i++) {
                numbers[i] = Integer.parseInt(input[i]);
            }

            String originalArray = formatArray(numbers);
            String evenNumbers = formatArray(filterNumbers(numbers, true));
            String oddNumbers = formatArray(filterNumbers(numbers, false));
            String reversedArray = formatArray(reverseArray(numbers));

            writeToFile(filePath, originalArray, evenNumbers, oddNumbers, reversedArray);
            System.out.println("Data successfully written to file.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void writeToFile(String filePath, String... lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("File writing error: " + e.getMessage());
        }
    }

    private static String formatArray(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int num : array) {
            sb.append(num).append(" ");
        }
        return sb.toString().trim();
    }

    private static int[] filterNumbers(int[] array, boolean even) {
        List<Integer> filtered = new ArrayList<>();
        for (int num : array) {
            if ((num % 2 == 0) == even) {
                filtered.add(num);
            }
        }
        return filtered.stream().mapToInt(i -> i).toArray();
    }

    private static int[] reverseArray(int[] array) {
        int[] reversed = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            reversed[i] = array[array.length - 1 - i];
        }
        return reversed;
    }
}
