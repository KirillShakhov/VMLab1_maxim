package ru.itmo;

import java.util.ArrayList;

public class ResultSet {
    ArrayList<String> result = new ArrayList<>();
    private ArrayList<Double> x = new ArrayList<>();
    private double[][] matrix;
    private double[][] triangleMatrix;
    private double[] residuals;
    private double det;

    public void add(String s){
        result.add(s);
    }
    public void print(){
        //String.format("%.15f", i);
        System.out.println("Матрица:");
        printMatrix(matrix);
        System.out.println("Треугольная матрица:");
        printMatrix(triangleMatrix);
        System.out.println("Определитель: "+det);
        System.out.println("Вектор неизвестных:");
        int i = 1;
        for(Double item : x){
            System.out.println("x"+(i++)+" = "+String.format("%.15f", item));
        }
        System.out.println("Вектор невязок:");
        i = 1;
        for(Double item : residuals){
            System.out.println("del x"+(i++)+" = "+String.format("%.15f", item));
        }
    }

    //Вывод матрицы
    public void printMatrix(double[][] matrix){
        for (double[] line : matrix) {
            for (double i : line) {
                System.out.print(i + " ");
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

    public double[] getResiduals() {
        return residuals;
    }

    public void setResiduals(double[] residuals) {
        this.residuals = residuals;
    }

    public double getDet() {
        return det;
    }

    public void setDet(double det) {
        this.det = det;
    }
}
