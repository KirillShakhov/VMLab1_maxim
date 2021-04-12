package ru.itmo;
import java.util.*;


public class Main {
    static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("1. Ввод с клавиатуры\n2. Ввод с файла\n3. Генерация рандомной матрицы");
        String line = scanner.nextLine();
        if (line.equals("1")) {
            System.out.println("Вводим матрицу с консоли");
            double[][] matrix = Utils.createMatrixFromKeyBoard();
            findSolution(matrix);
        } else if (line.equals("2")) {
            System.out.println("Вводим матрицу с файла");
            double[][] matrix;
            while (true) {
                try {
                    System.out.println("Имя файла:");
                    String path = scanner.nextLine();
                    matrix = Utils.readMatrixFromFile(path);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            findSolution(matrix);
        } else if (line.equals("3")) {
            System.out.println("Генерация рандомной матрицы");
            double[][] matrix;
            while (true) {
                try {
                    System.out.println("Какой размер матрицы::");
                    int size = Integer.parseInt(scanner.nextLine());
                    matrix = Utils.createRandomMatrix(size);
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
        ResultSet resultSet = gauss(matrix);
        resultSet.setMatrix(matrix);
        resultSet.print();
    }

    /*Метод Гаусса*/
    public static ResultSet gauss(double[][] matrix) {
        double det = 1;
        /*
        ResultSet - Класс который хранит все результаты метода.
        Нужен для избежания "Говорящих методов"(Когда методы используют System.out.println)
         */
        ResultSet resultSet  = new ResultSet();
        double buffer;
        double[] x = new double[matrix.length];
        /*
        Прямой ход - преобразование в треугольно
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
                    det*=buffer * matrix[i][k];
                }
            }
        }
        System.out.println("?????? "+det);
        /*
        Обратный ход -
        */
        x[matrix.length - 1] = matrix[matrix.length - 1][matrix.length];
        for (int i = matrix.length - 2; i >= 0; i--) {
            x[i] = matrix[i][matrix.length];
            for (int j = i + 1; j < matrix.length; j++) {
                x[i] -= matrix[i][j] * x[j];
            }
        }
        // Добавление треугольной матрицы в ResultSet
        resultSet.setTriangleMatrix(matrix);
        // Добавление вектора неизвестных в ResultSet
        resultSet.addX(x);
        resultSet.setResiduals(findResiduals(matrix, x));
        return resultSet;
    }
    // Нахождение невязок
    public static double[] findResiduals(double[][] matrix, double[] x) {
        double[] residuals = new double[matrix.length];
        for(int i = 0; i < matrix.length; i++)
        {
            float S=0;
            for(int j = 0; j < matrix.length; j++)
            {
                S += matrix[i][j] * x[j] ;
            }
            residuals[i] = S - matrix[i][matrix.length];
        }
        return residuals;
    }
}
