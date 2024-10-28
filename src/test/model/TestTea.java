package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTea {
    private Tea testTea1;
    private Tea testTea2;
    private Tea testTea3;

    @BeforeEach
    void setUp() {
        testTea1 = new Tea("China", "Green Tea", 1, 39.99);
        testTea2 = new Tea("China", "Oolong Tea", 3, 99.99);
        testTea3 = new Tea("India", "Black Tea", 2, 53.98);
    }

    @Test
    void testConstructor() {
        assertEquals(testTea1.getRegion(), "China");
        assertEquals(testTea1.getType(), "Green Tea");
        assertEquals(testTea1.getYear(), 1);
        assertEquals(testTea1.getPrice(), 39.99, 0.0001);
        assertEquals(testTea2.getPrice(), 99.99, 0.0001);
        assertEquals(testTea3.getRegion(), "India");
    }

    @Test
    void testToString() {
        String expected = "China - Green Tea - 1 years - 39.99";
        assertEquals(expected, testTea1.toString());
    }
}
