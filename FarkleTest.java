import static org.junit.Assert.*;

import org.junit.Test;

public class FarkleTest {
    @Test
    public void testStraight() {
        assertEquals(1000, Farkle.calculateMeldScore(new int[] { 0, 1, 1, 1, 1, 1, 1 }));
    }

    @Test
    public void testTriplePairs() {
        assertEquals(750, Farkle.calculateMeldScore(new int[] { 0, 2, 2, 2, 0, 0, 0 }));
    }

    @Test
    public void testTriples() {
        assertEquals(1500, Farkle.calculateMeldScore(new int[] { 0, 0, 3, 3, 0, 0, 0 }));
    }

    @Test
    public void testFullHousePlusOne() {
        assertEquals(1600, Farkle.calculateMeldScore(new int[] { 0, 1, 2, 3, 0, 0, 0 }));
    }

    @Test
    public void testFullHousePlusFive() {
        assertEquals(1550, Farkle.calculateMeldScore(new int[] { 0, 0, 2, 3, 0, 1, 0 }));
    }

    @Test
    public void testFullHouse() {
        assertEquals(1500, Farkle.calculateMeldScore(new int[] { 0, 0, 2, 3, 1, 0, 0 }));
    }

    @Test
    public void oneSingular() {
        assertEquals(100, Farkle.calculateMeldScore(new int[] { 0, 1, 2, 2, 1, 0, 1 }));
    }

    @Test
    public void fiveSingular() {
        assertEquals(50, Farkle.calculateMeldScore(new int[] { 0, 0, 2, 2, 1, 1, 1 }));
    }
}