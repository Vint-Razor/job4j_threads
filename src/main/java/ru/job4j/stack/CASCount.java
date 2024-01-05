package ru.job4j.stack;

import java.util.concurrent.atomic.AtomicInteger;

public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        count.incrementAndGet();
    }

    public int get() {
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 500; i++) {
                        casCount.increment();
                    }
                }
        );
        first.start();
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 500; i++) {
                        casCount.increment();
                    }
                }
        );
        second.start();
        first.join();
        second.join();
        System.out.println(casCount.get());
    }
}
