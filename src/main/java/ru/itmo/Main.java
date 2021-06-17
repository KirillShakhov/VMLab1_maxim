package ru.itmo;


import org.w3c.dom.ls.LSOutput;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // \n - перенос на другую строку
        System.out.println("1. Ввод с клавиатуры\n2. Ввод с файла\n3. Генерация рандомной матрицы");
        // Ввод с клавиатуры
        String line = scanner.nextLine();


        if (line.equals("1")) {
            System.out.println("Вводим матрицу с консоли");
            // Получение матрицы с клавиатуры
            double[][] matrix = Utils.createMatrixFromKeyBoard();
            // Нахождение решения
            findSolution(matrix);
        } else if (line.equals("2")) {
            System.out.println("Вводим матрицу с файла");
            double[][] matrix;
            // while true - бесконечный цикл, пока не будет введен правильный файл.
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
            // Нахождение решения
            findSolution(matrix);
        } else if (line.equals("3")) {
            System.out.println("Генерация рандомной матрицы");
            double[][] matrix;
            while (true) {
                try {
                    System.out.println("Какой размер матрицы:");
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

    private static double[][] check(double[][] matrix) {
        if (check_matrix(matrix)) return matrix;
        else return permuteMatrixHelper(matrix, 0);
    }

    private static boolean check_matrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][i] == 0) return false;
        }
        return true;
    }
//    private static double[][] fix_matrix(double[][] matrix) {
//        int size = matrix.length-1;
//        for(int i=-1; i<matrix.length-1;i++){
//            if(i>-1){
//                double[] tmp = matrix[size-(i+1)];
//                matrix[size-(i+1)]=matrix[size-i];
//                matrix[size-i]=tmp;
//            }
//            for (double[] doubles : matrix) {
//                StringBuilder s = new StringBuilder();
//                for (int f = 0; f < matrix.length; f++) {
//                    s.append(doubles[f]).append(" ");
//                }
//                System.out.println(s);
//            }
//        }
//        return null;
//    }

    public static double[][] permuteMatrixHelper(double[][] matrix, int index) {
        for (int i = index; i < matrix.length; i++) {
            if (check_matrix(matrix)) {
                return matrix;
            }
            double[] t = matrix[index];
            matrix[index] = matrix[i];
            matrix[i] = t;
            if (permuteMatrixHelper(matrix, index + 1) != null) {
                if (check_matrix(Objects.requireNonNull(permuteMatrixHelper(matrix, index + 1)))) {
                    return matrix;
                }
            }
            t = matrix[index];
            matrix[index] = matrix[i];
            matrix[i] = t;
        }
        return null;
    }

    // Нахождение решения с проверкой и исправлением диагонального преобладания
    public static void findSolution(double[][] matrix) {
        // Нахождение решения
        matrix = check(matrix);
        if (matrix != null) {
            // Получение решения
            Result resultSet = gauss(matrix);
            // вызов метода print, который печатает результат из Result
            resultSet.print();
        } else {
            System.out.println("В диагонали есть нули и это не исправить");
        }
    }

    /*Метод Гаусса*/
    public static Result gauss(double[][] matrix) {
        /*
        ResultSet - Класс который хранит все результаты метода.
        Нужен для избежания "Говорящих методов"(Когда методы используют System.out.println)
        Сохранение оригинальной матрицы.
         */
        double[][] original_matrix = matrix;
        Result resultSet = new Result();
        resultSet.setMatrix(matrix);

        double buffer;
        double[] x = new double[matrix.length];
        /*
        Прямой ход - преобразование в треугольную матрицу
        */
        for(int j=0; j< matrix.length; j++) {
            for(int i=j+1; i< matrix.length; i++) {
                double temp = matrix[i][j]/matrix[j][j];
                for(int k=0; k <= matrix.length; k++)
                    matrix[i][k]-=matrix[j][k]*temp;
            }
        }
        /*
        Поиск определителя
        */
        int det = 1;
        for (int i = 0; i < matrix.length; i++) {
            det *= matrix[i][i];
        }
        resultSet.setDet(det);
        /*
        Обратный ход - нахождение неизвестных x
        */
//        x[matrix.length - 1] = matrix[matrix.length - 1][matrix.length];
//        for (int i = matrix.length - 2; i >= 0; i--) {
//            x[i] = matrix[i][matrix.length];
//            for (int j = i + 1; j < matrix.length; j++) {
//                x[i] -= matrix[i][j] * x[j];
//            }
//        }
        double d;
        double s;
        for (int k = matrix.length - 1; k >= 0; k--) {
            d = 0;
            for (int j = k; j < matrix.length; j++) {
                s = matrix[k][j] * x[j]; // формула (4)
                d = d + s; // формула (4)
            }
            x[k] = (matrix[k][matrix.length] - d) / matrix[k][k]; // формула (4)
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
        Вычисление и добавления вектора невязок.
        original_matrix - оригинальная матрица, отличается от треугольной, но разницы нет.
        x - вектор с найденными x1, x2, ..., xn.
         */
        resultSet.setResiduals(findResiduals(original_matrix, x));
        return resultSet;
    }

    private static double det2(double[][] matrix) {
        double s = 1;
        for (double[] doubles : matrix) {
            s *= 1 * doubles[matrix.length];
        }
        return s;

    }

    /*
    Нахождение невязок.
    Методом подстановки в матрицу.
    x1, x2, ..., xn подставляются в матрицу на свои места
    и ищется разница между результатом и результатом из вектора свободных членов.
    Подробнее: https://www.cyberforum.ru/numerical-methods/thread1224615.html
     */
    public static double[] findResiduals(double[][] matrix, double[] x) {
        double[] residuals = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            float S = 0;
            for (int j = 0; j < matrix.length; j++) {
                S += matrix[i][j] * x[j];
            }
            residuals[i] = S - matrix[i][matrix.length];
        }
        return residuals;
    }

    /*
    Нахождение определителя методом Гаусса.
    Можно было бы встроить в основной метод Гаусса, но так быстрее.
    EPS нужен для округления в сторону нуля, тк double дает погрешность.
    Math.abs - метод для получения модуля.
     */
    public static double det(double[][] matrix) {
        double EPS = 1E-9;
        double det = 1;
        for (int i = 0; i < matrix.length; ++i) {
            int k = i;

            for (int j = i + 1; j < matrix.length; ++j) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[k][i])) {
                    k = j;
                }
            }

            // Округление к нулю
            if (Math.abs(matrix[k][i]) < EPS) {
                det = 0;
                break;
            }

            /*
            swab - обмен местами строки i и k
             */
            for (int j = 0; j < matrix.length; j++) {
                double t = matrix[i][j];
                matrix[i][j] = matrix[k][j];
                matrix[k][j] = t;
            }


            if (i != k) det = -det;

            det *= matrix[i][i];
            for (int j = i + 1; j < matrix.length; ++j) {
                matrix[i][j] /= matrix[i][i];
            }

            for (int j = 0; j < matrix.length; ++j) {
                if (j != i && Math.abs(matrix[j][i]) > EPS) {
                    for (int t = i + 1; t < matrix.length; ++t) {
                        matrix[j][t] -= matrix[i][t] * matrix[j][i];
                    }
                }
            }
        }
        return det;
    }
}
