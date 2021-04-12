package ru.itmo;
import java.util.*;
import static ru.itmo.ResultSet.printMatrix;
import static ru.itmo.Utils.createMatrixFromKeyBoard;
import static ru.itmo.Utils.readMatrixFromFile;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("1. Ввод с клавиатуры\n2. Ввод с файла");
        String line = scanner.nextLine();
        if (line.equals("1")) {
            System.out.println("Вводим матрицу с консоли");
            double[][] matrix = createMatrixFromKeyBoard();
            findSolution(matrix);
        } else if (line.equals("2")) {
            System.out.println("Вводим матрицу с файла");
            double[][] matrix;
            while (true) {
                try {
                    System.out.println("Имя файла:");
                    String path = scanner.nextLine();
                    matrix = readMatrixFromFile(path);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            findSolution(matrix);
        }
    }

    // Нахождение решения с проверкой и исправлением диагонального преобладания
    public static void findSolution(double[][] matrix) {
        printMatrix(matrix);
        ResultSet resultSet = gauss(matrix);
        resultSet.print();
    }

    /*Метод Гаусса*/
    public static ResultSet gauss(double[][] matrix) {
        //matrix[N][N+1]
        double[] x = new double[matrix.length];
        double buffer;
        ResultSet resultSet  = new ResultSet();


        /*
        Прямой ход -
        */
        for (int i = 0; i < matrix.length; i++) {
            buffer = matrix[i][i];
            for (int j = matrix.length; j >= i; j--) {
                matrix[i][j] = matrix[i][j] / buffer;
            }
            for (int j = i + 1; j < matrix.length; j++) {
                buffer = matrix[j][i];
                for (int k = matrix.length; k >= i; k--) {
                    matrix[j][k] -= buffer * matrix[i][k];
                }
            }
        }

        /*
        обратный ход -
        */
        x[matrix.length - 1] = matrix[matrix.length - 1][matrix.length];
        for (int i = matrix.length - 2; i >= 0; i--) {
            x[i] = matrix[i][matrix.length];
            for (int j = i + 1; j < matrix.length; j++) {
                x[i] -= matrix[i][j] * x[j];
            }
        }

        resultSet.addX(x);
        return resultSet;
    }
}
