import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Program3Test {

    @Test
    void firstTest() {

        int[][] matrix= {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[] output = Program3.flattenMatrix(matrix);
        int[] answer = {1, 4, 7, 2, 5, 8, 3, 6, 9};

        assertArrayEquals(answer, output);

    }


    @Test
    void secondTest() {

        int[][] matrix= {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        int[] output = Program3.flattenMatrix(matrix);
        int[] answer = {1, 0, 0, 0, 1, 0, 0, 0, 1};

        assertArrayEquals(answer, output);

    }
}