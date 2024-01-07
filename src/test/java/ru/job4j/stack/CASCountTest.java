package ru.job4j.stack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    void whenIncrement() throws InterruptedException {
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
        assertThat(casCount.get()).isEqualTo(1000);
    }
}