package ru.job4j;

public class CountBarrier {
    private final Object monitor = this;
    private final int total;
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (total > count) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(5);
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        countBarrier.count();
                        System.out.println(countBarrier.count);
                    }
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName()
                            + " started");
                },
                "first"
        );
        Thread second = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName()
                            + " started");
                },
                "second"
        );
        first.start();
        second.start();
    }
}
