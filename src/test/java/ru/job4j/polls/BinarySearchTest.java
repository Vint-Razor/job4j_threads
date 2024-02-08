package ru.job4j.polls;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class BinarySearchTest {

    @Test
    void whenFindInteger3InBigArrThen3() {
        int index = 3;
        Integer[] arr = IntStream.range(0, 30).boxed().toArray(Integer[]::new);
        Integer element = arr[index];
        var expected = ParallelSearch.search(element, arr);
        assertThat(expected).isEqualTo(index);
    }

    @Test
    void whenNotFoundInArrThenMinusOne() {
        int notFound = -1;
        Integer actual = 0;
        Integer[] arr = IntStream.range(1, 30).boxed().toArray(Integer[]::new);
        var expected = ParallelSearch.search(actual, arr);
        assertThat(expected).isEqualTo(notFound);
    }

    @Test
    void whenStringHelloThenIndexOfHello() {
        String actual = "hello";
        Integer indexOfActual = 3;
        String[] arr = new String[30];
        Arrays.fill(arr, "word");
        arr[indexOfActual] = actual;
        var expected = ParallelSearch.search(actual, arr);
        assertThat(expected).isEqualTo(indexOfActual);
    }
}