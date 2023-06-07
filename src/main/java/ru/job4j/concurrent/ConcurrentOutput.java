package ru.job4j.concurrent;

public class ConcurrentOutput {

    public static void main(String[] args) {
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        second.start();
        System.out.println(Thread.currentThread().getName());
    }
}
