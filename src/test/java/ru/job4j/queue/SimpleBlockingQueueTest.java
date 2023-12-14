package ru.job4j.queue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    void whenOfferThenPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>();
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
}