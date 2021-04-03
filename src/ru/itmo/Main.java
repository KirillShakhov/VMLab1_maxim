package ru.itmo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.abs;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("1. Ввод с клавиатуры\n2. Ввод с файла");
        String line = scanner.nextLine();
        if (line.equals("1")) {
            System.out.println("Вводим матрицу с консоли");
            double[][] matrix = createMatrixFromKeyBoard();
            double eps;
            while (true) {
                try {
                    String buffer = scanner.nextLine();
                    eps = Double.parseDouble(buffer);
                    break;
                } catch (Exception ignored) {
                }
            }
            findSolution(matrix, eps);
        } else if (line.equals("2")) {
            System.out.println("Вводим матрицу с файла");
            double eps;
            double[][] matrix;
            while (true) {
                try {
                    System.out.println("Имя файла:");
                    String path = scanner.nextLine();
                    System.out.println("Введите точность:");
                    String buffer = scanner.nextLine();
                    eps = Double.parseDouble(buffer);
                    matrix = readMatrixFromFile(path);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            findSolution(matrix, eps);
        }
    }

    // Нахождение решения с проверкой и исправлением диагонального преобладания
    public static void findSolution(double[][] matrix, double eps) {
        if (checkDiagonals(matrix)) {
            solve(matrix, eps);
            return;
        }
        printMatrix(matrix);
        matrix = permuteMatrixHelper(matrix);
        System.out.println("Произведена перестановка строк");
        printMatrix(matrix);
        solve(matrix, eps);
    }

    public static void printMatrix(double[][] matrix){
        for (double[] line : matrix){
            for(double i : line){
                System.out.print(i+" ");
            }
            System.out.println();
        }
    }

    // Метод для проверки матрицы на диагональное преаобладание
    public static boolean checkDiagonals(double[][] matrix) {
        boolean isD = true;
        for (int i = 0; i < matrix.length-1; i++) {
            if(!checkDiagonal(matrix, i)) isD = false;
        }
        return isD;
    }

    // Метод для проверки строки на диагональное преобладание. На вход приходит матрица и номер строки.
    public static boolean checkDiagonal(double[][] matrix, int line){
        double sum = 0;
        for (double i : matrix[line]) {
            sum += abs(i);
        }
        sum -= abs(matrix[line][line]);
        return sum < abs(matrix[line][line]);
    }
    public static int checkLine(double[] line){
        //Сумма всех значений в линии
        double sum = 0.0;
        for (int i = 0; i < line.length-1; i++) {
            double abs = abs(line[i]);
            sum += abs;
        }
        for(int i = 0; i < line.length; i++){
            sum -= abs(line[i]);
            if(sum < abs(line[i])) {
                return i;
            }
        }
        return -1;
    }


    //Метод для перестановки строк
    private static double[][] permuteMatrixHelper(double[][] matrix) {
        double[][] result = new double[matrix.length][matrix.length+1];
        HashMap<double[], Integer> map1 = new HashMap<>();
        for (double[] line : matrix) {
            int value = checkLine(line);
            if (value != -1) {
                map1.put(line, value);
            } else {
                System.out.println("Диагонального преобладание не удалось достичь");
                System.exit(0);
            }
        }
        ArrayList<double[]> arrayList = new ArrayList<>();
        map1.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(x -> arrayList.add(x.getKey())); // или любой другой конечный метод
        result = arrayList.toArray(result);
        return result;
    }

    //Метод простых итераций, на вход приходит матрица[N][N+1] и точность
    private static void solve(double[][] matrix, double eps) {
        double[] x= new double[matrix.length];
        double norma = 0, sum, t;
        do
        {
            ArrayList<Double> es = new ArrayList<>();
            norma = 0;
            //  k++;
            for(int i = 0; i < matrix.length; i++)
            {
                t = x[i];
                sum = 0;

                for(int j = 0; j < matrix.length; j++)
                {
                    if(j != i)
                        sum += matrix[i][j] * x[j];
                }
                x[i] = (getVector(matrix)[i] - sum) / matrix[i][i];
                es.add(abs(x[i] - t));
                if (abs(x[i] - t) > norma)
                    norma = abs(x[i] - t);
            }
        }
        while(norma > eps);
        System.out.println("Результат");
        for(int i = 0; i < x.length; i++){ System.out.println("x"+(i+1)+" = "+String.format("%.6f",x[i])); }
        System.out.println("Вектор невязки:");
        for(int i = 0; i < matrix.length; i++)
        {
            float S=0;
            for(int j = 0; j < matrix.length; j++)
            {
                S += matrix[i][j] * x[j] ;
            }
            System.out.println("del x"+(i+1)+" = "+String.format("%.6f",S - getVector(matrix)[i]));
        }
    }
    // Получение вектора свободных членов из матрицы[N][N+1]
    public static double[] getVector(double[][] matrix){
        double [] vector = new double[matrix.length];
        for(int i = 0; i < matrix.length; i++){
            vector[i]=matrix[i][matrix.length];
        }
        return vector;
    }
    // Метод для получения матрицы с клавиатуры
    public static double[][] createMatrixFromKeyBoard(){
        try {
            System.out.println("Введите размерность матрицы");
            String buffer = scanner.nextLine();
            buffer = buffer.trim();
            int size = Integer.parseInt(buffer);
            if (size > 20 || size <= 0) {
                throw new Exception();
            }
            System.out.println("Введите строки матрицы");
            double [][] matrix = new double[size][size+1];
            String [][] arr = new String[size][size+1];
            for (int i = 0; i < size;i++) {
                buffer = scanner.nextLine();
                arr[i] = buffer.trim().split(" ");
                buffer = "";
            }
            for (int i = 0; i < size;i++){
                for (int j = 0; j < size+1;j++) {
                    matrix[i][j] = Double.parseDouble(arr[i][j].trim());
                }
            }
            return matrix;
        } catch (Exception e) {
            System.out.println("Введена неверная размерность");
        }
        return null;
    }
    // Метод для получение матрицы из файла
    public static double[][] readMatrixFromFile(String fileName) {
        try {
            BufferedReader file = new BufferedReader(new FileReader(new File(fileName)));
            int size = Integer.parseInt(file.readLine().trim());
            double [][] matrix = new double[size][size + 1];
            for (int i = 0; i < size; i++) {
                String[] row = file.readLine().trim().split(" ");
                if (row.length > size + 1)
                    throw new ArrayIndexOutOfBoundsException();
                for (int j = 0; j < size + 1; j++) {
                    matrix[i][j] = Double.parseDouble(row[j].trim());
                }
            }
            return matrix;
        } catch (IOException e) {
            System.out.println("Ошибка ввода");
        }
        return null;
    }
}
