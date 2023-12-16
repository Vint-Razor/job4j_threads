package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= limit) {
            this.wait();
        }
        queue.add(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queueIsEmpty()) {
            this.wait();
        }
        T result = queue.poll();
        this.notifyAll();
        return result;
    }

    private synchronized boolean queueIsEmpty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(3);
        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            Thread.sleep(1000);
                            System.out.printf("poll %d\n", blockingQueue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                },
                "consumer"
        );
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            blockingQueue.offer(i);
                            Thread.sleep(100);
                            System.out.printf("offer %d\n", i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                },
                "producer"
        );
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
    }
}
