package com.chensuworks.nativeeditor.plugins.parenmatch;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MatcherTest {

    @Test
    public void testScanLeftOrRight() {
        Matcher matcher = new Matcher("xlabel('Time (sec)');");

        // Outer () pair, index of 6 & 19
        matcher.scanLeftOrRight('(', 6);
        assertEquals(matcher.getLeft(), 6);
        assertEquals(matcher.getRight(), 19);

        matcher.scanLeftOrRight(')', 19);
        assertEquals(matcher.getLeft(), 6);
        assertEquals(matcher.getRight(), 19);

        // Inner () pair, index of 13 & 17
        matcher.scanLeftOrRight('(', 13);
        assertEquals(matcher.getLeft(), 13);
        assertEquals(matcher.getRight(), 17);

        matcher.scanLeftOrRight(')', 17);
        assertEquals(matcher.getLeft(), 13);
        assertEquals(matcher.getRight(), 17);


        matcher = new Matcher("axis([0 0.2 -1.2 1.2]);");

        // () pair, index of 4 & 21
        matcher.scanLeftOrRight('(', 4);
        assertEquals(matcher.getLeft(), 4);
        assertEquals(matcher.getRight(), 21);

        matcher.scanLeftOrRight(')', 21);
        assertEquals(matcher.getLeft(), 4);
        assertEquals(matcher.getRight(), 21);

        // [] pair, index of 5 & 20
        matcher.scanLeftOrRight('[', 5);
        assertEquals(matcher.getLeft(), 5);
        assertEquals(matcher.getRight(), 20);

        matcher.scanLeftOrRight(']', 20);
        assertEquals(matcher.getLeft(), 5);
        assertEquals(matcher.getRight(), 20);


        matcher = new Matcher("(([{[({[]})]}]))");

        // () pair, index of 0 & 15
        matcher.scanLeftOrRight('(', 0);
        assertEquals(matcher.getLeft(), 0);
        assertEquals(matcher.getRight(), 15);

        matcher.scanLeftOrRight(')', 15);
        assertEquals(matcher.getLeft(), 0);
        assertEquals(matcher.getRight(), 15);

        // () pair, index of 1 & 14
        matcher.scanLeftOrRight('(', 1);
        assertEquals(matcher.getLeft(), 1);
        assertEquals(matcher.getRight(), 14);

        matcher.scanLeftOrRight(')', 14);
        assertEquals(matcher.getLeft(), 1);
        assertEquals(matcher.getRight(), 14);

        // [] pair, index of 2 & 13
        matcher.scanLeftOrRight('[', 2);
        assertEquals(matcher.getLeft(), 2);
        assertEquals(matcher.getRight(), 13);

        matcher.scanLeftOrRight(']', 13);
        assertEquals(matcher.getLeft(), 2);
        assertEquals(matcher.getRight(), 13);

        // {} pair, index of 3 & 12
        matcher.scanLeftOrRight('{', 3);
        assertEquals(matcher.getLeft(), 3);
        assertEquals(matcher.getRight(), 12);

        matcher.scanLeftOrRight('}', 12);
        assertEquals(matcher.getLeft(), 3);
        assertEquals(matcher.getRight(), 12);

        // [] pair, index of 4 & 11
        matcher.scanLeftOrRight('[', 4);
        assertEquals(matcher.getLeft(), 4);
        assertEquals(matcher.getRight(), 11);

        matcher.scanLeftOrRight(']', 11);
        assertEquals(matcher.getLeft(), 4);
        assertEquals(matcher.getRight(), 11);

        // () pair, index of 5 & 10
        matcher.scanLeftOrRight('(', 5);
        assertEquals(matcher.getLeft(), 5);
        assertEquals(matcher.getRight(), 10);

        matcher.scanLeftOrRight(')', 10);
        assertEquals(matcher.getLeft(), 5);
        assertEquals(matcher.getRight(), 10);

        // {} pair, index of 6 & 9
        matcher.scanLeftOrRight('{', 6);
        assertEquals(matcher.getLeft(), 6);
        assertEquals(matcher.getRight(), 9);

        matcher.scanLeftOrRight('}', 9);
        assertEquals(matcher.getLeft(), 6);
        assertEquals(matcher.getRight(), 9);

        // [] pair, index of 7 & 8
        matcher.scanLeftOrRight('[', 7);
        assertEquals(matcher.getLeft(), 7);
        assertEquals(matcher.getRight(), 8);

        matcher.scanLeftOrRight(']', 8);
        assertEquals(matcher.getLeft(), 7);
        assertEquals(matcher.getRight(), 8);
    }

    @Test
    public void testScan() {
        Matcher matcher = new Matcher("xlabel('Time (sec)');");

        // Outer () pair, index of 6 & 19
        matcher.scan(6, 1, 0);
        assertEquals(matcher.getRight(), 19);
        matcher.scan(19, -1, 0);
        assertEquals(matcher.getLeft(), 6);

        // Inner () pair, index of 13 & 17
        matcher.scan(13, 1, 0);
        assertEquals(matcher.getRight(), 17);
        matcher.scan(17, -1, 0);
        assertEquals(matcher.getLeft(), 13);


        matcher = new Matcher("axis([0 0.2 -1.2 1.2]);");

        // () pair, index of 4 & 21
        matcher.scan(4, 1, 0);
        assertEquals(matcher.getRight(), 21);
        matcher.scan(21, -1, 0);
        assertEquals(matcher.getLeft(), 4);

        // [] pair, index of 5 & 20
        matcher.scan(5, 1, 1);
        assertEquals(matcher.getRight(), 20);
        matcher.scan(20, -1, 1);
        assertEquals(matcher.getLeft(), 5);

        matcher = new Matcher("(([{[({[]})]}]))");

        // () pair, index of 0 & 15
        matcher.scan(0, 1, 0);
        assertEquals(matcher.getRight(), 15);
        matcher.scan(15, -1, 0);
        assertEquals(matcher.getLeft(), 0);

        // () pair, index of 1 & 14
        matcher.scan(1, 1, 0);
        assertEquals(matcher.getRight(), 14);
        matcher.scan(14, -1, 0);
        assertEquals(matcher.getLeft(), 1);

        // [] pair, index of 2 & 13
        matcher.scan(2, 1, 1);
        assertEquals(matcher.getRight(), 13);
        matcher.scan(13, -1, 1);
        assertEquals(matcher.getLeft(), 2);

        // {} pair, index of 3 & 12
        matcher.scan(3, 1, 2);
        assertEquals(matcher.getRight(), 12);
        matcher.scan(12, -1, 2);
        assertEquals(matcher.getLeft(), 3);

        // [] pair, index of 4 & 11
        matcher.scan(4, 1, 1);
        assertEquals(matcher.getRight(), 11);
        matcher.scan(11, -1, 1);
        assertEquals(matcher.getLeft(), 4);

        // () pair, index of 5 & 10
        matcher.scan(5, 1, 0);
        assertEquals(matcher.getRight(), 10);
        matcher.scan(10, -1, 0);
        assertEquals(matcher.getLeft(), 5);

        // {} pair, index of 6 & 9
        matcher.scan(6, 1, 2);
        assertEquals(matcher.getRight(), 9);
        matcher.scan(9, -1, 2);
        assertEquals(matcher.getLeft(), 6);

        // [] pair, index of 7 & 8
        matcher.scan(7, 1, 1);
        assertEquals(matcher.getRight(), 8);
        matcher.scan(8, -1, 1);
        assertEquals(matcher.getLeft(), 7);
    }

    @Test
    public void testScanDocument() {
        Matcher matcher = new Matcher("xlabel('Time (sec)');");

        // Outer () pair, index of 6 & 19
        assertEquals(matcher.scanDocument(6, 1, 0), 19);
        assertEquals(matcher.scanDocument(19, -1, 0), 6);

        // Inner () pair, index of 13 & 17
        assertEquals(matcher.scanDocument(13, 1, 0), 17);
        assertEquals(matcher.scanDocument(17, -1, 0), 13);


        matcher = new Matcher("axis([0 0.2 -1.2 1.2]);");

        // () pair, index of 4 & 21
        assertEquals(matcher.scanDocument(4, 1, 0), 21);
        assertEquals(matcher.scanDocument(21, -1, 0), 4);

        // [] pair, index of 5 & 20
        assertEquals(matcher.scanDocument(5, 1, 1), 20);
        assertEquals(matcher.scanDocument(20, -1, 1), 5);


        matcher = new Matcher("(([{[({[]})]}]))");

        // () pair, index of 0 & 15
        assertEquals(matcher.scanDocument(0, 1, 0), 15);
        assertEquals(matcher.scanDocument(15, -1, 0), 0);

        // () pair, index of 1 & 14
        assertEquals(matcher.scanDocument(1, 1, 0), 14);
        assertEquals(matcher.scanDocument(14, -1, 0), 1);

        // [] pair, index of 2 & 13
        assertEquals(matcher.scanDocument(2, 1, 1), 13);
        assertEquals(matcher.scanDocument(13, -1, 1), 2);

        // {} pair, index of 3 & 12
        assertEquals(matcher.scanDocument(3, 1, 2), 12);
        assertEquals(matcher.scanDocument(12, -1, 2), 3);

        // [] pair, index of 4 & 11
        assertEquals(matcher.scanDocument(4, 1, 1), 11);
        assertEquals(matcher.scanDocument(11, -1, 1), 4);

        // () pair, index of 5 & 10
        assertEquals(matcher.scanDocument(5, 1, 0), 10);
        assertEquals(matcher.scanDocument(10, -1, 0), 5);

        // {} pair, index of 6 & 9
        assertEquals(matcher.scanDocument(6, 1, 2), 9);
        assertEquals(matcher.scanDocument(9, -1, 2), 6);

        // [] pair, index of 7 & 8
        assertEquals(matcher.scanDocument(7, 1, 1), 8);
        assertEquals(matcher.scanDocument(8, -1, 1), 7);
    }

}
