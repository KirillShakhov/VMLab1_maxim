package ru.itmo;

import java.util.ArrayList;

public class Result {
    private ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
    private ArrayList<ArrayList<Double>> triangle = new ArrayList<>();
    private double det;
    private double[] x;
    private double[] residuals;

    public void print(){
        //String.format("%.15f", i);
        System.out.println("Матрица:");
        printMatrix(this.matrix);
        System.out.println("Треугольная матрица:");
        printMatrix(this.triangle);
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
    public void printMatrix(ArrayList<ArrayList<Double>> m){
        for (ArrayList<Double> line : m) {
            for (Double i : line) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    /*
    Getter and Setter
     */
    public ArrayList<ArrayList<Double>> getMatrix() { return matrix; }
    public void setMatrix(double[][] matrix) {
        for(double[] line : matrix){
            ArrayList<Double> new_line = new ArrayList<>();
            for(Double d : line){
                new_line.add(d);
            }
            this.matrix.add(new_line);
        }
    }
    public ArrayList<ArrayList<Double>> getTriangleMatrix() { return triangle; }
    public void setTriangleMatrix(double[][] triangleMatrix) {
        for(double[] line : triangleMatrix){
            ArrayList<Double> new_line = new ArrayList<>();
            for(Double d : line){
                new_line.add(d);
            }
            this.triangle.add(new_line);
        }
    }
    public double getDet() { return det; }
    public void setDet(double det) { this.det = det; }
    public double[] getX() { return x; }
    public void setX(double[] x) { this.x = x; }
    public double[] getResiduals() { return residuals; }
    public void setResiduals(double[] residuals) { this.residuals = residuals; }
}
