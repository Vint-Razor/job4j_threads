package ru.job4j.queue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    void whenOfferThenPoll() throws InterruptedException {
        int sizeLimit = 3;
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(sizeLimit);
        List<Integer> actualList = List.of(1, 2, 3);
        List<Integer> expectedList = new ArrayList<>();
        Thread producer = new Thread(
                () -> actualList.forEach(value -> {
                    try {
                        blockingQueue.offer(value);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
        );
        Thread consumer = new Thread(
                () -> {
                    try {
                        expectedList.add(blockingQueue.poll());
                        expectedList.add(blockingQueue.poll());
                        expectedList.add(blockingQueue.poll());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(expectedList).isEqualTo(actualList);
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final int numbOffers = 5;
        int sizeLimit = 3;
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(sizeLimit);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < numbOffers; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }
}