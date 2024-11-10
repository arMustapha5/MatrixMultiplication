import java.util.Scanner;

public class MatrixMultiplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input dimensions of Matrix A with validation
        int rowsA = getValidInput(scanner, "Enter the number of rows for Matrix A: ");
        int colsA = getValidInput(scanner, "Enter the number of columns for Matrix A: ");

        // Initialize Matrix A
        int[][] matrixA = new int[rowsA][colsA];
        System.out.println("Enter elements of Matrix A:");
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
                matrixA[i][j] = getValidInput(scanner, "A[" + i + "][" + j + "]: ");
            }
        }

        // Input dimensions of Matrix B with validation
        int rowsB = getValidInput(scanner, "Enter the number of rows for Matrix B: ");
        int colsB = getValidInput(scanner, "Enter the number of columns for Matrix B: ");

        // Check if matrix multiplication is possible
        if (colsA != rowsB) {
            System.out.println("Matrix multiplication is not possible. The number of columns in Matrix A must be equal to the number of rows in Matrix B.");
            scanner.close();
            return;
        }

        // Initialize Matrix B
        int[][] matrixB = new int[rowsB][colsB];
        System.out.println("Enter elements of Matrix B:");
        for (int i = 0; i < rowsB; i++) {
            for (int j = 0; j < colsB; j++) {
                matrixB[i][j] = getValidInput(scanner, "B[" + i + "][" + j + "]: ");
            }
        }

        // Initialize Matrix C (Result Matrix)
        int[][] matrixC = new int[rowsA][colsB];

        // Perform Matrix Multiplication
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        // Display the Resulting Matrix C
        System.out.println("\nMatrix C (Result):");
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                System.out.print(matrixC[i][j] + "\t");
            }
            System.out.println();
        }

        scanner.close();
    }

    // Method to get valid integer input from the user, preventing spaces
    private static int getValidInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim(); // Read entire line and trim spaces
            // Check if input is a valid integer and contains no spaces
            if (input.matches("\\d+")) {
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            } else {
                System.out.println("Invalid input. Please enter a single integer without spaces.");
            }
        }
    }
}
