package Task1_FileHandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FileUtility {

    // Scanner for user input, declared as static to be accessible by all static methods.
    static Scanner scanner = new Scanner(System.in);
    // Default file path. The program will create/use a file named "sample.txt".
    static String filePath = "\"D:\\.vscode\\JAVA\\CODTECH PROJECT\\Task1_FileHandling\\sample.txt\"";

    public static void main(String[] args) {
        int choice = 0;

        do {
            // Display the menu to the user.
            System.out.println("\n===== File Utility Menu =====");
            System.out.println("1. Read File");
            System.out.println("2. Write to File (Overwrites)");
            System.out.println("3. Modify File (Find and Replace)");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input from the scanner
                continue; // Skip the rest of the loop and show the menu again
            } finally {
                scanner.nextLine(); // Consume the newline character to prevent issues with next input.
            }


            // Process the user's choice.
            switch (choice) {
                case 1:
                    readFile();
                    break;
                case 2:
                    writeFile();
                    break;
                case 3:
                    modifyFile();
                    break;
                case 4:
                    System.out.println("Exiting File Utility.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        // Close the scanner when the program exits.
        scanner.close();
    }

    /**
     * Reads the content of the file specified by filePath and prints it to the console.
     */
    public static void readFile() {
        // Using try-with-resources to ensure the BufferedReader is automatically closed.
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("\n--- File Content ---");
            // Read the file line by line until the end is reached.
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("--- End of File ---");
        } catch (IOException e) {
            // Handle cases where the file cannot be read (e.g., it doesn't exist).
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    /**
     * Prompts the user for input and writes it to the file, overwriting any existing content.
     */
    public static void writeFile() {
        System.out.println("Enter text to write to the file (type 'exit' on a new line to finish):");
        // Using try-with-resources to ensure the BufferedWriter is automatically closed.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String line;
            // Continuously read from console until the user types "exit".
            while (!(line = scanner.nextLine()).equalsIgnoreCase("exit")) {
                writer.write(line);
                writer.newLine(); // Add a new line after each entry.
            }
            System.out.println("File written successfully.");
        } catch (IOException e) {
            // Handle potential errors during file writing.
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }

    /**
     * Finds a specific word in the file and replaces all occurrences with another word.
     */
    public static void modifyFile() {
        System.out.print("Enter the word to find: ");
        String find = scanner.nextLine();
        System.out.print("Enter the word to replace it with: ");
        String replace = scanner.nextLine();

        StringBuilder fileContent = new StringBuilder();

        // Step 1: Read the entire file into a StringBuilder in memory.
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Replace all occurrences in the current line and append to the StringBuilder.
                fileContent.append(line.replaceAll(find, replace)).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error reading the file for modification: " + e.getMessage());
            return; // Exit the method if reading fails.
        }

        // Step 2: Write the modified content from the StringBuilder back to the same file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(fileContent.toString());
            System.out.println("File modified successfully.");
        } catch (IOException e) {
            System.out.println("Error writing modified content to the file: " + e.getMessage());
        }
    }
}
