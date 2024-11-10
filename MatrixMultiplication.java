import java.util.Scanner;

public class MatrixMultiplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] dimensionsA = null;
        int[] dimensionsB = null;

        // Get and validate dimensions for Matrix A
        while (dimensionsA == null) {
            System.out.println("Enter dimensions for Matrix A (rows,columns or rows columns):");
            String input = scanner.nextLine();
            ValidationResult result = validateDimensions(input);
            if (!result.isValid) {
                System.out.println(result.message);
                continue;
            }
            dimensionsA = result.dimensions;
        }

        int[][] matrixA = new int[dimensionsA[0]][dimensionsA[1]];
        while (!inputMatrix(matrixA, scanner)) {
            System.out.println("Enter values for Matrix A (" + dimensionsA[0] + "x" + dimensionsA[1] + "):");
        }

        // Get and validate dimensions for Matrix B
        while (dimensionsB == null) {
            System.out.println("Enter dimensions for Matrix B (rows,columns or rows columns):");
            String input = scanner.nextLine();
            ValidationResult result = validateDimensions(input);
            if (!result.isValid) {
                System.out.println(result.message);
                continue;
            }
            dimensionsB = result.dimensions;
        }

        // Validate if multiplication is possible
        if (dimensionsA[1] != dimensionsB[0]) {
            System.out.println("Error: Cannot multiply these matrices.");
            System.out.println("Number of columns in Matrix A (" + dimensionsA[1] + 
                             ") must equal number of rows in Matrix B (" + dimensionsB[0] + ")");
            scanner.close();
            return;
        }

        int[][] matrixB = new int[dimensionsB[0]][dimensionsB[1]];
        while (!inputMatrix(matrixB, scanner)) {
            System.out.println("Enter values for Matrix B (" + dimensionsB[0] + "x" + dimensionsB[1] + "):");
        }

        // Display input matrices
        System.out.println("\nMatrix A (" + dimensionsA[0] + "x" + dimensionsA[1] + "):");
        displayMatrix(matrixA);
        
        System.out.println("\nMatrix B (" + dimensionsB[0] + "x" + dimensionsB[1] + "):");
        displayMatrix(matrixB);

        // Perform matrix multiplication and display result
        int[][] matrixC = multiplyMatrices(matrixA, matrixB);
        System.out.println("\nResult of Matrix Multiplication (C = A × B):");
        System.out.println("Matrix C (" + dimensionsA[0] + "x" + dimensionsB[1] + "):");
        displayMatrix(matrixC);

        scanner.close();
    }

    // Helper class for validation results
    private static class ValidationResult {
        boolean isValid;
        String message;
        int[] dimensions;

        ValidationResult(boolean isValid, String message, int[] dimensions) {
            this.isValid = isValid;
            this.message = message;
            this.dimensions = dimensions;
        }
    }

    // Method to validate dimensions with detailed error messages
    private static ValidationResult validateDimensions(String input) {
        try {
            String[] parts;
            if (input.contains(",")) {
                parts = input.split(",");
            } else {
                parts = input.trim().split("\\s+");
            }

            if (parts.length != 2) {
                return new ValidationResult(false, 
                    "Invalid input! Please enter two numbers separated by either a comma or space (e.g., '2,3' or '2 3')",
                    null);
            }

            // Check for decimal numbers
            if (input.contains(".")) {
                return new ValidationResult(false,
                    "Please enter integer values only. Decimal numbers are not allowed.",
                    null);
            }

            // Try parsing the numbers
            for (String part : parts) {
                if (!part.trim().matches("-?\\d+")) {
                    return new ValidationResult(false,
                        "Invalid input! '" + part.trim() + "' is not a valid integer number.",
                        null);
                }
            }

            int rows = Integer.parseInt(parts[0].trim());
            int cols = Integer.parseInt(parts[1].trim());

            if (rows <= 0 || cols <= 0) {
                return new ValidationResult(false,
                    "Dimensions must be positive integers. Please enter values greater than 0.",
                    null);
            }

            return new ValidationResult(true, "", new int[]{rows, cols});
        } catch (NumberFormatException e) {
            return new ValidationResult(false,
                "Invalid input! Please enter valid integer numbers.",
                null);
        }
    }

    // Method to input and validate matrix values
    private static boolean inputMatrix(int[][] matrix, Scanner scanner) {
        System.out.println("Enter " + matrix[0].length + " integer values separated by spaces for each row:");
        
        try {
            for (int i = 0; i < matrix.length; i++) {
                System.out.println("Enter row " + (i + 1) + ":");
                String line = scanner.nextLine().trim();
                String[] values = line.split("\\s+");

                // Check if correct number of values is provided
                if (values.length != matrix[0].length) {
                    System.out.println("Error: Please enter exactly " + matrix[0].length + " values for each row.");
                    return false;
                }

                // Validate each value is an integer
                for (String value : values) {
                    if (!value.matches("-?\\d+")) {
                        if (value.contains(".")) {
                            System.out.println("Error: '" + value + "' is a decimal number. Please enter only integer values.");
                        } else {
                            System.out.println("Error: '" + value + "' is not a valid integer number.");
                        }
                        return false;
                    }
                }

                // Parse values after validation
                for (int j = 0; j < matrix[0].length; j++) {
                    matrix[i][j] = Integer.parseInt(values[j]);
                }
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter valid integer numbers only.");
            return false;
        }
    }

    // Method to multiply matrices
    private static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        int rowsA = matrixA.length;
        int colsB = matrixB[0].length;
        int[][] result = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < matrixB.length; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return result;
    }

    // Method to display matrix in formatted output
    private static void displayMatrix(int[][] matrix) {
        int maxWidth = 0;
        for (int[] row : matrix) {
            for (int value : row) {
                maxWidth = Math.max(maxWidth, String.valueOf(value).length());
            }
        }

        for (int[] row : matrix) {
            System.out.print("| ");
            for (int value : row) {
                System.out.printf("%-" + (maxWidth + 1) + "d", value);
            }
            System.out.println("|");
        }
    }
}