import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArrayReader {
    public static void main(String[] args) {
        String filePath = "nums.txt";
        List<int[]> arrays = readArraysFromFile(filePath);

        printArrays(arrays);
    }

    private static List<int[]> readArraysFromFile(String filePath) {
        List<int[]> arrays = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] numbers = line.split("\\s+");
                int[] array = new int[numbers.length];

                for (int i = 0; i < numbers.length; i++) {
                    array[i] = Integer.parseInt(numbers[i]);
                }
                arrays.add(array);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in file: " + e.getMessage());
        }

        return arrays;
    }

    private static void printArrays(List<int[]> arrays) {
        for (int i = 0; i < arrays.size(); i++) {
            System.out.print("Array " + (i + 1) + ": ");
            for (int num : arrays.get(i)) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}
