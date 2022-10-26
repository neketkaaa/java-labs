import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Program1Test {
    @Test
    void shouldReturnTrue_ifNumberContainsAInHexRepresentation() {
        int number = 10;

        boolean actual = Program1.containsDigitAInHexadecimalRepresentation(number);

        assertTrue(actual);
    }

    @Test
    void shouldReturnFalse_ifNumberDoesNotContainAInHexRepresentation() {
        int number = 9;

        boolean actual = Program1.containsDigitAInHexadecimalRepresentation(number);

        assertFalse(actual);
    }
}