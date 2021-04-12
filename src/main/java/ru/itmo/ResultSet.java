package ru.itmo;

import java.util.ArrayList;

public class ResultSet {
    ArrayList<String> result = new ArrayList<>();
    private ArrayList<Double> x = new ArrayList<>();
    private double[][] matrix;
    private double[][] triangleMatrix;

    public void add(String s){
        result.add(s);
    }
    public void print(){
        System.out.println("Матрица:");
        printMatrix();
        System.out.println("Треугольная матрица:");
        printTriangleMatrix();
        System.out.println("Определитель:");
//        det()
        System.out.println("Вектор неизвестных:");
        int i = 1;
        for(Double item : x){
            System.out.println("x"+(i++)+" = "+item);
        }
    }

    //Вывод матрицы
    public void printMatrix(){
        if(matrix == null){
            System.out.println("Матрица не установлена в ResultSet");
        }
        else {
            for (double[] line : matrix) {
                for (double i : line) {
                    System.out.print(i + " ");
                }
                System.out.println();
            }
        }
    }
    public void printTriangleMatrix(){
        if(triangleMatrix == null){
            System.out.println("Треугольная матрица не установлена в ResultSet");
        }
        else {
            for (double[] line : triangleMatrix) {
                for (double i : line) {
                    System.out.print(i + " ");
                }
                System.out.println();
            }
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

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[][] getTriangleMatrix() {
        return triangleMatrix;
    }

    public void setTriangleMatrix(double[][] triangleMatrix) {
        this.triangleMatrix = triangleMatrix;
    }
}
