package ru.itmo;
import java.util.*;


public class Main {
    static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("1. Ввод с клавиатуры\n2. Ввод с файла");
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

//    public static boolean diagCheck(double[][] matrix){
//        for(int i = 1; i < matrix.length; i++){
//            for(int j = 0; j<i; j++){
//                if(matrix[i][j] != 0){
//                    return false;
//                }
//            }
//
//        }
//        return true;
//    }

    /*Метод Гаусса*/
    public static ResultSet gauss(double[][] matrix) {
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
                    matrix[j][k] -= buffer * matrix[i][k]; // формула (3)
                }
            }
        }
        /*
        Обратный ход -
        */
        //
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
        return resultSet;
    }
//    public static ResultSet gauss2(double[][] matrix) {
//        double[][] a = matrix;
//        double d, s;
//        double[] b = new double[matrix.length], x = new double[matrix.length];
//        int n = matrix.length-1;
//        ResultSet resultSet = new ResultSet();
//        // прямой ход
//        for (int k = 1; k <= n; k++) {
//            for (int j = k + 1; j <= n; j++) {
//                d = a[j][k] / a[k][k]; // формула (1)
//                for (int i = k; i <= n; i++) {
//                    a[j][i] = a[j][i] - d * a[k][i]; // формула (2)
//                }
//                b[j] = b[j] - d * b[k]; // формула (3)
//            }
//        }
//
//        //Обратный
//        for (int k = n; k >= 1; k--) {
//            d = 0;
//            for (int j = k + 1; j <= n; j++) {
//                s = a[k][j] * x[j]; // формула (4)
//                d = d + s; // формула (4)
//            }
//            x[k] = (b[k] - d) / a[k][k]; // формула (4)
//        }
//        resultSet.addX(x);
//        return resultSet;
//    }

//    public static double det(double[][] matrix){
//        double p=0, det = 0;
//        int n = matrix.length;
//        for (int i=0; i<n-1; i++)
//        {
//            int t=1;
//            while(matrix[i][i]==0)
//            {
//                for(int j=0; j<n; j++)
//                {
//                    matrix[i][j]=det;
//                    matrix[i][j]=matrix[i+t][j];
//                    matrix[i+t][j]=det;
//                }
//                p++;
//                t++;
//            }
//
//            for (int k=i+1; k<n; k++)
//            {
//                det=matrix[k][i]/matrix[i][i];
//                for(int j=0; j<n; j++)
//                    matrix[k][j]-=matrix[i][j]*det;
//            }
//        }
//
//        det=Math.pow(-1,p);
//        for(int i = 0; i<n; i++) {
//            det *= matrix[i][i];
//        }
//        return det;
//    }

//    //Функция для возврвтв определителя массива
//    public static double det(double[][] a){
//        double res = 1;
//        double j;
//        int n = a.length;
//        for (int i = 0; i<n;i++) {
//            j = max(range(i, n), key = lambda k:abs(a[k][i]))
//
//            if (i != j) _
//            a[i], a[j] = a[j], a[i]
//            res *= -1
//        #убеждаемся, что матрица не вырожденная
//            if a[i][i] == 0:
//            return 0
//            res *= a[i][i]
//        #"обнуляем" элементы в текущем столбце
//            for j in range(i + 1, n) {
//                b = a[j][i] / a[i][i]
//                a[j] = [a[j][k] - b * a[i][k] for k in range(n)]
//            }
//        }
//        return res
//    }
}
