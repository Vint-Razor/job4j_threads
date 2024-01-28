package ru.job4j.polls;

public class MergeSort {

    public static int[] sort(int[] array) {
        return sort(array, 0, array.length - 1);
    }

    private static int[] sort(int[] array, int from, int to) {
        if (from == to) {
            return new int[]{array[from]};
        }
        int middle = (from + to) / 2;
        return merge(sort(array, from, middle),
                sort(array, middle + 1, to));
    }

    public static int[] merge(int[] left, int[] right) {
        int leftI = 0;
        int rightI = 0;
        int resultI = 0;
        int[] result = new int[left.length + right.length];
        while (resultI != result.length) {
            if (leftI == left.length) {
                result[resultI++] = right[rightI++];
            } else if (rightI == right.length) {
                result[resultI++] = left[leftI++];
            } else if (left[leftI] <= right[rightI]) {
                result[resultI++] = left[leftI++];
            } else {
                result[resultI++] = right[rightI++];
            }
        }
        return result;
    }
}
