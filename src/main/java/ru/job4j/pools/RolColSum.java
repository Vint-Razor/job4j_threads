package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public record Sums(int rowSum, int colSum) {
    }

    private static Sums serialSum(int index, int[][] matrix) {
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            rowSum += matrix[index][i];
            colSum += matrix[i][index];
        }
        return new Sums(rowSum, colSum);
    }

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] arrSums = new Sums[size];
        for (int i = 0; i < size; i++) {
            arrSums[i] = serialSum(i, matrix);
        }
        return arrSums;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        int size = matrix.length;
        Sums[] arrSums = new Sums[size];
        for (int i = 0; i < size; i++) {
            try {
                arrSums[i] = Objects.requireNonNull(getTask(i, matrix)).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return arrSums;
    }

    private static CompletableFuture<Sums> getTask(int index, int[][] matrix) {
        return CompletableFuture.supplyAsync(() -> serialSum(index, matrix));
    }

    public static void main(String[] args) {
        String ln = System.lineSeparator();
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] sums = sum(matrix);
        System.out.println("Serial");
        for (Sums sum : sums) {
            System.out.printf("row: %d\t col: %d%s", sum.rowSum, sum.colSum, ln);
        }
        Sums[] asyncSum = asyncSum(matrix);
        System.out.println("Async");
        for (Sums sums1 : asyncSum) {
            System.out.printf("row: %d\t col: %d%s", sums1.rowSum, sums1.colSum, ln);
        }
    }
}
