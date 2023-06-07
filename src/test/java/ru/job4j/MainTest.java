package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MainTest {

    @Test
    void whenTestThenTest() {
        String actual = "Test";
        String expected = Main.printTest();
        assertThat(expected).isEqualTo(actual);
    }
}