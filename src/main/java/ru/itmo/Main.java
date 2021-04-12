package ru.itmo;


import java.util.Scanner;

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
        Result resultSet = gauss(matrix);
        resultSet.print();
    }

    /*Метод Гаусса*/
    public static Result gauss(double[][] matrix) {
        /*
        ResultSet - Класс который хранит все результаты метода.
        Нужен для избежания "Говорящих методов"(Когда методы используют System.out.println)
        Сохранение оригинальной матрицы.
         */
        double[][] original_matrix = matrix;
        Result resultSet  = new Result();
        resultSet.setMatrix(matrix);
        /*
        Нахождение определителя
         */
        resultSet.setDet(det(matrix));

        double buffer;
        double[] x = new double[matrix.length];
        /*
        Прямой ход - преобразование в треугольную матрицу
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
        Обратный ход - нахождение неизвестных x
        */
        x[matrix.length - 1] = matrix[matrix.length - 1][matrix.length];
        for (int i = matrix.length - 2; i >= 0; i--) {
            x[i] = matrix[i][matrix.length];
            for (int j = i + 1; j < matrix.length; j++) {
                x[i] -= matrix[i][j] * x[j];
            }
        }
        /*
        Добавление треугольной матрицы в ResultSet
         */
        resultSet.setTriangleMatrix(matrix);
        /*
        Добавление вектора неизвестных в ResultSet
         */
        resultSet.setX(x);
        /*
        Вычисление и добавления вектора невязок
         */
        resultSet.setResiduals(findResiduals(matrix, x));
        return resultSet;
    }
    /*
    Нахождение невязок
     */
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

    /*
    Нахождение определителя
     */
    public static double det(double[][] matrix){
        double EPS = 1E-9;
        double det = 1;
        int n = matrix.length;
        for (int i=0; i<n; ++i) {
            int k = i;
            for (int j=i+1; j<n; ++j) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[k][i])) {
                    k = j;
                }
            }
            if (Math.abs (matrix[k][i]) < EPS) {
                det = 0;
                break;
            }

            /*
            swab - обмен местами строки i и k
             */
            for(int j = 0; j < n; j++){
                double t = matrix[i][j];
                matrix[i][j] = matrix[k][j];
                matrix[k][j]=t;
            }

            if (i != k) det = -det;

            det *= matrix[i][i];
            for (int j=i+1; j<n; ++j) {
                matrix[i][j] /= matrix[i][i];
            }
            for (int j=0; j<n; ++j) {
                if (j != i && Math.abs(matrix[j][i]) > EPS) {
                    for (int t = i + 1; t < n; ++t) {
                        matrix[j][t] -= matrix[i][t] * matrix[j][i];
                    }
                }
            }
        }
        return det;
    }
}
