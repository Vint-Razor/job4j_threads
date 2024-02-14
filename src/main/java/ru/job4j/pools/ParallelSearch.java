package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T value;
    private final int from;
    private final int to;

    public ParallelSearch(T[] array, T value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    private int linearSearchIndex() {
        int result = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(value)) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearchIndex();
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(array, value, from, mid);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(array, value, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        return Math.max(leftSearch.join(), rightSearch.join());
    }

    public static <T> int search(T search, T[] arr) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(arr, search, 0, arr.length - 1));
    }
}
