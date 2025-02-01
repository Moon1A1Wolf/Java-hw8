import java.io.*;
import java.util.*;

public class Corporation {
    private static final List<Employee> employees = new ArrayList<>();
    private static String filePath;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the file path to load employees: ");
            filePath = scanner.nextLine();
            loadEmployees();

            while (true) {
                System.out.println("\n--- Corporation Management ---");
                System.out.println("1. Add Employee");
                System.out.println("2. Edit Employee");
                System.out.println("3. Delete Employee");
                System.out.println("4. Search Employee by Surname");
                System.out.println("5. Display Employees");
                System.out.println("6. Save Employees");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                String input = scanner.nextLine();
                if (!input.matches("\\d+")) {
                    System.out.println("Invalid input. Try again.");
                    continue;
                }
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> addEmployee(scanner);
                    case 2 -> editEmployee(scanner);
                    case 3 -> deleteEmployee(scanner);
                    case 4 -> searchEmployee(scanner);
                    case 5 -> displayEmployees(scanner);
                    case 6 -> saveEmployees();
                    case 0 -> {
                        saveEmployees();
                        System.out.println("Exiting program...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    private static void loadEmployees() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found. A new file will be created.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    employees.add(new Employee(parts[0], parts[1], Integer.parseInt(parts[2])));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static void addEmployee(Scanner scanner) {
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        employees.add(new Employee(surname, name, age));
        System.out.println("Employee added successfully.");
    }

    private static void editEmployee(Scanner scanner) {
        System.out.print("Enter surname of employee to edit: ");
        String surname = scanner.nextLine();

        for (Employee e : employees) {
            if (e.getSurname().equalsIgnoreCase(surname)) {
                System.out.print("Enter new name: ");
                e.setName(scanner.nextLine());
                System.out.print("Enter new age: ");
                e.setAge(scanner.nextInt());
                scanner.nextLine();
                System.out.println("Employee updated.");
                return;
            }
        }
        System.out.println("Employee not found.");
    }

    private static void deleteEmployee(Scanner scanner) {
        System.out.print("Enter surname of employee to delete: ");
        String surname = scanner.nextLine();

        employees.removeIf(e -> e.getSurname().equalsIgnoreCase(surname));
        System.out.println("Employee deleted if found.");
    }

    private static void searchEmployee(Scanner scanner) {
        System.out.print("Enter surname to search: ");
        String surname = scanner.nextLine();

        List<Employee> results = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getSurname().equalsIgnoreCase(surname)) {
                results.add(e);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            saveSearchResults(results);
            results.forEach(System.out::println);
        }
    }

    private static void displayEmployees(Scanner scanner) {
        System.out.println("1. Show all employees");
        System.out.println("2. Filter by age");
        System.out.println("3. Filter by surname starting letter");

        String input = scanner.nextLine();
        if (!input.matches("\\d+")) {
            System.out.println("Invalid input. Please enter a number between 1 and 3.");
            return;
        }
        int choice = Integer.parseInt(input);

        switch (choice) {
            case 1 -> {
                if (employees.isEmpty()) {
                    System.out.println("No employees found.");
                } else {
                    employees.forEach(System.out::println);
                }
            }
            case 2 -> {
                System.out.print("Enter age: ");
                String ageInput = scanner.nextLine();
                if (!ageInput.matches("\\d+")) {
                    System.out.println("Invalid input.");
                    return;
                }
                int age = Integer.parseInt(ageInput);
                List<Employee> filtered = employees.stream()
                        .filter(e -> e.getAge() == age)
                        .toList();
                if (filtered.isEmpty()) {
                    System.out.println("No employees found.");
                } else {
                    filtered.forEach(System.out::println);
                }
            }
            case 3 -> {
                System.out.print("Enter starting letter: ");
                String letterInput = scanner.nextLine();
                if (letterInput.length() != 1 || !Character.isLetter(letterInput.charAt(0))) {
                    System.out.println("Invalid input. Enter a single letter.");
                    return;
                }
                char letter = letterInput.charAt(0);
                List<Employee> filtered = employees.stream()
                        .filter(e -> e.getSurname().charAt(0) == letter)
                        .toList();
                if (filtered.isEmpty()) {
                    System.out.println("No employees found.");
                } else {
                    filtered.forEach(System.out::println);
                }
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void saveSearchResults(List<Employee> results) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("search_results.txt"))) {
            for (Employee e : results) {
                writer.write(e.toString());
                writer.newLine();
            }
            System.out.println("Search results saved to search_results.txt.");
        } catch (IOException e) {
            System.err.println("Error saving search results: " + e.getMessage());
        }
    }

    private static void saveEmployees() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Employee e : employees) {
                writer.write(e.toFileFormat());
                writer.newLine();
            }
            System.out.println("Employees saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving employees: " + e.getMessage());
        }
    }
}

class Employee {
    private String surname;
    private String name;
    private int age;

    public Employee(String surname, String name, int age) {
        this.surname = surname;
        this.name = name;
        this.age = age;
    }

    public String getSurname() { return surname; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public int getAge() { return age; }

    public String toFileFormat() {
        return surname + "," + name + "," + age;
    }

    @Override
    public String toString() {
        return surname + " " + name + ", Age: " + age;
    }
}
