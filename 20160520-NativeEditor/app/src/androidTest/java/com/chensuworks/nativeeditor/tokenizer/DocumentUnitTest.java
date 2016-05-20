package com.chensuworks.nativeeditor.tokenizer;

import android.test.ActivityTestCase;

import com.chensuworks.nativeeditor.tokenizer.Document;
import com.chensuworks.nativeeditor.tokenizer.PositionInfo;

import java.util.LinkedList;

public class DocumentUnitTest extends ActivityTestCase {

    public void testConstructor() {
        Document document = new Document("function foo");
        assertEquals(document.getCodes(), "function foo");
        assertEquals(document.getLinesOfCodes().size(), 1);
        assertEquals(document.getLinesOfCodes().get(0), "function foo");

        document = new Document("function foo\n");
        assertEquals(document.getCodes(), "function foo\n");
        assertEquals(document.getLinesOfCodes().size(), 2);
        assertEquals(document.getLinesOfCodes().get(0), "function foo");
        assertEquals(document.getLinesOfCodes().get(1), "");

        document = new Document("function foo\nfunction foo");
        assertEquals(document.getCodes(), "function foo\nfunction foo");
        assertEquals(document.getLinesOfCodes().size(), 2);
        assertEquals(document.getLinesOfCodes().get(0), "function foo");
        assertEquals(document.getLinesOfCodes().get(1), "function foo");

        document = new Document("\nfunction foo");
        assertEquals(document.getCodes(), "\nfunction foo");
        assertEquals(document.getLinesOfCodes().size(), 2);
        assertEquals(document.getLinesOfCodes().get(0), "");
        assertEquals(document.getLinesOfCodes().get(1), "function foo");

        document = new Document("\n");
        assertEquals(document.getCodes(), "\n");
        assertEquals(document.getLinesOfCodes().size(), 2);
        assertEquals(document.getLinesOfCodes().get(0), "");
        assertEquals(document.getLinesOfCodes().get(1), "");

        document = new Document("\n\n\n\n\n\n\n\n");
        assertEquals(document.getCodes(), "\n\n\n\n\n\n\n\n");
        assertEquals(document.getLinesOfCodes().size(), 9);
        assertEquals(document.getLinesOfCodes().get(0), "");
        assertEquals(document.getLinesOfCodes().get(1), "");
        assertEquals(document.getLinesOfCodes().get(2), "");
        assertEquals(document.getLinesOfCodes().get(3), "");
        assertEquals(document.getLinesOfCodes().get(4), "");
        assertEquals(document.getLinesOfCodes().get(5), "");
        assertEquals(document.getLinesOfCodes().get(6), "");
        assertEquals(document.getLinesOfCodes().get(7), "");
        assertEquals(document.getLinesOfCodes().get(8), "");

        document = new Document("\n\n\nfunction foo\n\n\n\n\n");
        assertEquals(document.getCodes(), "\n\n\nfunction foo\n\n\n\n\n");
        assertEquals(document.getLinesOfCodes().size(), 9);
        assertEquals(document.getLinesOfCodes().get(0), "");
        assertEquals(document.getLinesOfCodes().get(1), "");
        assertEquals(document.getLinesOfCodes().get(2), "");
        assertEquals(document.getLinesOfCodes().get(3), "function foo");
        assertEquals(document.getLinesOfCodes().get(4), "");
        assertEquals(document.getLinesOfCodes().get(5), "");
        assertEquals(document.getLinesOfCodes().get(6), "");
        assertEquals(document.getLinesOfCodes().get(7), "");
        assertEquals(document.getLinesOfCodes().get(8), "");

        document = new Document("%% Section1\nif true plot(1:10)\nend\n\ndisp('hi')");
        assertEquals(document.getCodes(), "%% Section1\nif true plot(1:10)\nend\n\ndisp('hi')");
        assertEquals(document.getLinesOfCodes().size(), 5);
        assertEquals(document.getLinesOfCodes().get(0), "%% Section1");
        assertEquals(document.getLinesOfCodes().get(1), "if true plot(1:10)");
        assertEquals(document.getLinesOfCodes().get(2), "end");
        assertEquals(document.getLinesOfCodes().get(3), "");
        assertEquals(document.getLinesOfCodes().get(4), "disp('hi')");
    }

    public void testAppendNewLine() {
        Document document = new Document("function foo");
        assertEquals(document.getCodes(), "function foo");
        assertEquals(document.getLinesOfCodes().size(), 1);

        boolean ret = document.appendNewLine("function foo");
        assertTrue(ret);
        assertEquals(document.getCodes(), "function foo\nfunction foo");
        assertEquals(document.getLinesOfCodes().size(), 2);

        ret = document.appendNewLine("\nif true");
        assertFalse(ret);
        assertEquals(document.getCodes(), "function foo\nfunction foo");
        assertEquals(document.getLinesOfCodes().size(), 2);

        ret = document.appendNewLine("if true\nplot(1:10)");
        assertFalse(ret);
        assertEquals(document.getCodes(), "function foo\nfunction foo");
        assertEquals(document.getLinesOfCodes().size(), 2);
    }

    public void testGetIndex() {
        Document document = new Document("function foo\nif true plot(1:10)\n");
        assertEquals(document.getIndex(0, 0), 0);
        assertEquals(document.getIndex(0, 9), 9);
        assertEquals(document.getIndex(1, 0), 13);
        assertEquals(document.getIndex(1, 3), 16);

        document = new Document("function foo\nif true plot(1:10)\nj k\nj k");
        assertEquals(document.getIndex(0, 0), 0);
        assertEquals(document.getIndex(0, 9), 9);
        assertEquals(document.getIndex(1, 0), 13);
        assertEquals(document.getIndex(1, 3), 16);
        assertEquals(document.getIndex(2, 0), 32);
        assertEquals(document.getIndex(2, 2), 34);
        assertEquals(document.getIndex(3, 0), 36);
        assertEquals(document.getIndex(3, 2), 38);
    }

    public void testGetLineRange() {
        Document document = new Document("function foo\nif true plot(1:10)\n");
        assertEquals(document.getLineRange(0).getStart(), 0);
        assertEquals(document.getLineRange(0).getEnd(), 12);
        assertEquals(document.getLineRange(1).getStart(), 13);
        assertEquals(document.getLineRange(1).getEnd(), 31);
        assertEquals(document.getLineRange(2).getStart(), 32);
        assertEquals(document.getLineRange(2).getEnd(), 32);
        assertEquals(document.getLineRange(3), null);
        assertEquals(document.getLineRange(-1), null);
    }

    public void testGetLineText() {
        Document document = new Document("%% Section1\nif true plot(1:10)\nend\n\ndisp('hi')");
        assertEquals(document.getLineText(-1), null);
        assertEquals(document.getLineText(0), "%% Section1");
        assertEquals(document.getLineText(1), "if true plot(1:10)");
        assertEquals(document.getLineText(2), "end");
        assertEquals(document.getLineText(3), "");
        assertEquals(document.getLineText(4), "disp('hi')");
        assertEquals(document.getLineText(5), null);

        document = new Document("%% Section1\nif true plot(1:10)\nend\n\ndisp('hi')\n");
        assertEquals(document.getLineText(-1), null);
        assertEquals(document.getLineText(0), "%% Section1");
        assertEquals(document.getLineText(1), "if true plot(1:10)");
        assertEquals(document.getLineText(2), "end");
        assertEquals(document.getLineText(3), "");
        assertEquals(document.getLineText(4), "disp('hi')");
        assertEquals(document.getLineText(5), "");
        assertEquals(document.getLineText(6), null);

        document = new Document("\n\n\n\n\n");
        assertEquals(document.getLineText(-1), null);
        assertEquals(document.getLineText(0), "");
        assertEquals(document.getLineText(1), "");
        assertEquals(document.getLineText(2), "");
        assertEquals(document.getLineText(3), "");
        assertEquals(document.getLineText(4), "");
        assertEquals(document.getLineText(5), "");
        assertEquals(document.getLineText(6), null);
    }

    public void testGetTextCharacters() {
        Document document = new Document("%% Section1\nif true plot(1:10)\nend\n\ndisp('hi')");
        assertEquals(document.getTextCharacters(-1, 0, 1), null);
        assertEquals(document.getTextCharacters(0, 0, 1), "%");
        assertEquals(document.getTextCharacters(0, 0, 11), "%% Section1");
        assertEquals(document.getTextCharacters(0, 0, 12), "\n");
        assertEquals(document.getTextCharacters(0, 0, 13), null);
        assertEquals(document.getTextCharacters(1, 0, 1), "i");
        assertEquals(document.getTextCharacters(2, 0, 1), "e");
        assertEquals(document.getTextCharacters(3, 0, 1), "\n");
        assertEquals(document.getTextCharacters(4, 0, 1), "d");
        assertEquals(document.getTextCharacters(5, 0, 1), null);
    }

    public void testGetIndentLevel() {
        Document document = new Document("%% Section1\nif true plot(1:10)\nend\n\ndisp('hi')");
        assertEquals(document.getIndentLevel(-1), -1);
        assertEquals(document.getIndentLevel(0), 0);
        assertEquals(document.getIndentLevel(1), 0);
        assertEquals(document.getIndentLevel(2), 0);
        assertEquals(document.getIndentLevel(3), 0);
        assertEquals(document.getIndentLevel(4), 0);
        assertEquals(document.getIndentLevel(5), -1);

        document = new Document("%% Section1\n    if true plot(1:10)  \n  end\n      \n\n        disp('hi')");
        assertEquals(document.getIndentLevel(-1), -1);
        assertEquals(document.getIndentLevel(0), 0);
        assertEquals(document.getIndentLevel(1), 4);
        assertEquals(document.getIndentLevel(2), 2);
        assertEquals(document.getIndentLevel(3), 6);
        assertEquals(document.getIndentLevel(4), 0);
        assertEquals(document.getIndentLevel(5), 8);
        assertEquals(document.getIndentLevel(6), -1);
    }

    public void testGenerateWhitespaces() {
        assertEquals(Document.generateWhitespaces(-1), null);
        assertEquals(Document.generateWhitespaces(0), "");
        assertEquals(Document.generateWhitespaces(3), "   ");
        assertEquals(Document.generateWhitespaces(10), "          ");
    }

    public void testGenerateCodesFromLines() {
        LinkedList<String> linesOfCodes = new LinkedList<>();
        linesOfCodes.add("%% Section1");
        linesOfCodes.add("if true plot(1:10)");
        linesOfCodes.add("end");

        assertEquals(Document.generateCodesFromLines(linesOfCodes), "%% Section1\nif true plot(1:10)\nend");
    }

    public void testSetIndentLevel() {
        Document document = new Document("%% Section1\nif true plot(1:10)\nend\n\ndisp('hi')\n    disp('hi')");

        boolean ret = document.setLineIndent(-1, 4);
        assertFalse(ret);

        ret = document.setLineIndent(0, 4);
        assertTrue(ret);
        assertEquals(document.getLineText(0), "    %% Section1");
        assertEquals(document.getCodes(), "    %% Section1\nif true plot(1:10)\nend\n\ndisp('hi')\n    disp('hi')");

        ret = document.setLineIndent(1, 8);
        assertTrue(ret);
        assertEquals(document.getLineText(1), "        if true plot(1:10)");
        assertEquals(document.getCodes(), "    %% Section1\n        if true plot(1:10)\nend\n\ndisp('hi')\n    disp('hi')");

        ret = document.setLineIndent(2, 2);
        assertTrue(ret);
        assertEquals(document.getLineText(2), "  end");
        assertEquals(document.getCodes(), "    %% Section1\n        if true plot(1:10)\n  end\n\ndisp('hi')\n    disp('hi')");

        ret = document.setLineIndent(3, 1);
        assertTrue(ret);
        assertEquals(document.getLineText(3), " ");
        assertEquals(document.getCodes(), "    %% Section1\n        if true plot(1:10)\n  end\n \ndisp('hi')\n    disp('hi')");

        ret = document.setLineIndent(4, 0);
        assertTrue(ret);
        assertEquals(document.getLineText(4), "disp('hi')");
        assertEquals(document.getCodes(), "    %% Section1\n        if true plot(1:10)\n  end\n \ndisp('hi')\n    disp('hi')");

        ret = document.setLineIndent(5, 0);
        assertTrue(ret);
        assertEquals(document.getLineText(5), "disp('hi')");
        assertEquals(document.getCodes(), "    %% Section1\n        if true plot(1:10)\n  end\n \ndisp('hi')\ndisp('hi')");

        ret = document.setLineIndent(6, 4);
        assertFalse(ret);
    }

    public void testGetLineColumn() {
        Document document = new Document("%% Section1\nif true plot(1:10)\nend\n\ndisp('hi')");

        PositionInfo positionInfo = document.getLineColumn(0);
        assertEquals(positionInfo.getLine(), 0);
        assertEquals(positionInfo.getColumn(), 0);

        positionInfo = document.getLineColumn(11);
        assertEquals(positionInfo.getLine(), 0);
        assertEquals(positionInfo.getColumn(), 11);

        positionInfo = document.getLineColumn(30);
        assertEquals(positionInfo.getLine(), 1);
        assertEquals(positionInfo.getColumn(), 18);

        positionInfo = document.getLineColumn(31);
        assertEquals(positionInfo.getLine(), 2);
        assertEquals(positionInfo.getColumn(), 0);

        positionInfo = document.getLineColumn(34);
        assertEquals(positionInfo.getLine(), 2);
        assertEquals(positionInfo.getColumn(), 3);

        positionInfo = document.getLineColumn(35);
        assertEquals(positionInfo.getLine(), 3);
        assertEquals(positionInfo.getColumn(), 0);

        positionInfo = document.getLineColumn(36);
        assertEquals(positionInfo.getLine(), 4);
        assertEquals(positionInfo.getColumn(), 0);

        positionInfo = document.getLineColumn(46);
        assertEquals(positionInfo.getLine(), 4);
        assertEquals(positionInfo.getColumn(), 10);

        positionInfo = document.getLineColumn(47);
        assertEquals(positionInfo, null);

        positionInfo = document.getLineColumn(-1);
        assertEquals(positionInfo, null);
    }

}
