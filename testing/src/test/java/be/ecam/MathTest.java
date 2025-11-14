package be.ecam;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathTest {

    @ParameterizedTest
    @CsvSource({
            "2, 3, 5",
            "-1, 1, 0",
            "100, 200, 300"
    })
    void testAddition(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }
}
