package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {
    private final int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };
    private final int[] actualRow = {6, 15, 24};
    private final int[] actualCol = {12, 15, 18};

    @Test
    void checkSum() {
        RolColSum.Sums[] sums = RolColSum.sum(matrix);
        Integer[] expectedRow = Arrays.stream(sums).map(RolColSum.Sums::rowSum).toArray(Integer[]::new);
        Integer[] expectedCol = Arrays.stream(sums).map(RolColSum.Sums::colSum).toArray(Integer[]::new);
        assertThat(actualRow).containsOnlyOnce(expectedRow);
        assertThat(actualCol).containsOnlyOnce(expectedCol);
    }

    @Test
    void checkAsyncSum() {
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);
        Integer[] expectedRow = Arrays.stream(sums).map(RolColSum.Sums::rowSum).toArray(Integer[]::new);
        Integer[] expectedCol = Arrays.stream(sums).map(RolColSum.Sums::colSum).toArray(Integer[]::new);
        assertThat(actualRow).containsOnlyOnce(expectedRow);
        assertThat(actualCol).containsOnlyOnce(expectedCol);
    }
}