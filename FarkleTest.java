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

    // Testing Four of A Kind

    @Test
    public void testFourOfAKind1() {
        assertEquals(1100, Farkle.calculateMeldScore(new int[] { 2, 4, 0, 0, 0, 0, 0 }));
    }

    @Test
    public void testFourOfAKind2() {
        assertEquals(400, Farkle.calculateMeldScore(new int[] { 2, 0, 4, 0, 0, 0, 0 }));
    }

    @Test
    public void testFourOfAKind3() {
        assertEquals(600, Farkle.calculateMeldScore(new int[] { 2, 0, 0, 4, 0, 0, 0 }));
    }

    @Test
    public void testFourOfAKind4() {
        assertEquals(800, Farkle.calculateMeldScore(new int[] { 2, 0, 0, 0, 4, 0, 0 }));
    }

    @Test
    public void testFourOfAKind5() {
        assertEquals(1000, Farkle.calculateMeldScore(new int[] { 2, 0, 0, 0, 0, 4, 0 }));
    }

    @Test
    public void testFourOfAKind6() {
        assertEquals(1200, Farkle.calculateMeldScore(new int[] { 2, 0, 0, 0, 0, 0, 4 }));
    }

    // Testing Five of A Kind
    @Test
    public void testFiveOfAKind1() {
        assertEquals(1200, Farkle.calculateMeldScore(new int[] { 1, 5, 0, 0, 0, 0, 0 }));
    }

    @Test
    public void testFiveOfAKind2() {
        assertEquals(600, Farkle.calculateMeldScore(new int[] { 1, 0, 5, 0, 0, 0, 0 }));
    }

    @Test
    public void testFiveOfAKind3() {
        assertEquals(900, Farkle.calculateMeldScore(new int[] { 1, 0, 0, 5, 0, 0, 0 }));
    }

    @Test
    public void testFiveOfAKind4() {
        assertEquals(1200, Farkle.calculateMeldScore(new int[] { 1, 0, 0, 0, 5, 0, 0 }));
    }

    @Test
    public void testFiveOfAKind5() {
        assertEquals(1500, Farkle.calculateMeldScore(new int[] { 1, 0, 0, 0, 0, 5, 0 }));
    }

    @Test
    public void testFiveOfAKind6() {
        assertEquals(1800, Farkle.calculateMeldScore(new int[] { 1, 0, 0, 0, 0, 0, 5 }));
    }

    // Testing Six of A Kind
    @Test
    public void testSixOfAKind1() {
        assertEquals(1300, Farkle.calculateMeldScore(new int[] { 0, 6, 0, 0, 0, 0, 0 }));
    }

    @Test
    public void testSixOfAKind2() {
        assertEquals(800, Farkle.calculateMeldScore(new int[] { 0, 0, 6, 0, 0, 0, 0 }));
    }

    @Test
    public void testSixOfAKind3() {
        assertEquals(1200, Farkle.calculateMeldScore(new int[] { 0, 0, 0, 6, 0, 0, 0 }));
    }

    @Test
    public void testSixOfAKind4() {
        assertEquals(1600, Farkle.calculateMeldScore(new int[] { 0, 0, 0, 0, 6, 0, 0 }));
    }

    @Test
    public void testSixOfAKind5() {
        assertEquals(2000, Farkle.calculateMeldScore(new int[] { 0, 0, 0, 0, 0, 6, 0 }));
    }

    @Test
    public void testSixOfAKind6() {
        assertEquals(2400, Farkle.calculateMeldScore(new int[] { 0, 0, 0, 0, 0, 0, 6 }));
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