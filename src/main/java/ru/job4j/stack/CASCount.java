package ru.job4j.stack;

import java.util.concurrent.atomic.AtomicInteger;

public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int temp;
        int numb;
        do {
            temp = count.get();
            numb = temp + 1;
        } while (!count.compareAndSet(temp, numb));
    }

    public int get() {
        return count.get();
    }
}
