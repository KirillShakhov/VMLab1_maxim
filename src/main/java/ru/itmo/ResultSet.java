package ru.itmo;

import java.util.ArrayList;

public class ResultSet {
    ArrayList<String> result = new ArrayList<>();
    private ArrayList<Double> x = new ArrayList<>();

    public void add(String s){
        result.add(s);
    }
    public void print(){

        int i = 1;
        for(Double item : x){
            System.out.println("x"+(i++)+" = "+item);
        }
    }

    //Вывод матрицы
    public static void printMatrix(double[][] matrix){
        for (double[] line : matrix){
            for(double i : line){
                System.out.print(i+" ");
            }
            System.out.println();
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

    public void addX(double[] x) {
        for(double item : x){
            this.x.add(item);
        }
    }
}
