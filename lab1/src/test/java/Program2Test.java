import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Program2Test {

    @Test
    void firstTest() {

        int[] array = {5, 6, 7, 1, 2, 3, 4, 4, 5};
        int[] output = Program2.segregateEvenAndOddNumbers(array);
        int[] answer = {6, 2, 4, 4, 5, 7, 1, 3, 5};

        assertArrayEquals(answer, output);

    }

    @Test
    void secondTest() {

        int[] array = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55};
        int[] output = Program2.segregateEvenAndOddNumbers(array);
        int[] answer = {0, 2, 8, 34, 1, 1, 3, 5, 13, 21, 55};

        assertArrayEquals(answer, output);

    }

}