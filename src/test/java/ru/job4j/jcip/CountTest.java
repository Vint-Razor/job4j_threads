package ru.job4j.jcip;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CountTest {

    @Test
    void whenExecute2ThreadThen2() throws InterruptedException {
        var count = new Count();
        Thread first = new Thread(count::increment);
        Thread second = new Thread(count::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get()).isEqualTo(2);
    }
}