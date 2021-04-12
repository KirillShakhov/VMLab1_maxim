package ru.itmo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Utils {
    private static Scanner scanner = new Scanner(System.in);
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
