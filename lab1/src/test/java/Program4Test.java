import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Program4Test {

    @Test
    void firstTest() {

        String array = "1,2,4,8,16";
        boolean output = Program4.isGeometricProgression(array);

        assertTrue(output);
    }

    @Test
    void secondTest() {

        String array = "16,2,8,1,4";
        boolean output = Program4.isGeometricProgression(array);

        assertTrue(output);
    }

    @Test
    void thirdTest() {

        String array = "1,2,3,4,5";
        boolean output = Program4.isGeometricProgression(array);

        assertFalse(output);
    }
}