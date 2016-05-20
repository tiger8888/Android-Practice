package com.chensuworks.nativeeditor.tokenizer;

import java.util.LinkedList;

public class Document {

    private String codes;
    private LinkedList<String> linesOfCodes = new LinkedList<>();

    public Document(String codes) {
        this.codes = codes;
        updateStates();
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
        updateStates();
    }

    public LinkedList<String> getLinesOfCodes() {
        return linesOfCodes;
    }

    /**
     * Generates lines of codes from the possibly very long string of codes.
     * Having this processed linesOfCodes will benefit:
     *      conversion between {index} and {row, column}
     *      easy access to each line of codes
     */
    private void updateStates() {
        linesOfCodes.clear();

        boolean findEOL = false;
        int startIndex = 0;
        int indexOfEOL = -1;
        // Iterate through the whole string of codes.
        for (int i = 0; i < codes.length(); i++) {
            if (codes.charAt(i) == '\n') {
                findEOL = true;
                indexOfEOL = i;
                linesOfCodes.add(codes.substring(startIndex, indexOfEOL));
                startIndex = indexOfEOL + 1;
            }
        }

        // If EOL is not found, then there's only 1 line of codes
        if (!findEOL) {
            linesOfCodes.add(codes);
            return;
        }

        // If EOL is found, differentiate the cases by checking if the last EOL is at the end of string or not.
        if (indexOfEOL != codes.length() - 1) {
            // EOL is not at the end of string, we need to add the rest of texts after EOL as another line.
            linesOfCodes.add(codes.substring(indexOfEOL + 1));

        } else {
            // EOL is at the end of string, we need to add another empty line.
            linesOfCodes.add("");
        }

    }

    /**
     * Simply appending an extra line of codes.
     * @param newLineCodes should NOT contain any \n within it.
     * @return true if succeeds, false if the codes contain \n
     */
    public boolean appendNewLine(String newLineCodes) {
        if (newLineCodes.contains("\n")) {
            return false;
        }

        setCodes(codes + "\n" + newLineCodes);
        return true;
    }

    /**
     * Convert {row, column} to index of the codes string.
     * @param row - 0 based
     * @param column - 0 based
     * @return index - 0 based
     */
    public int getIndex(int row, int column) {
        int index = 0;

        for (int i = 0; i < row; i++) {
            index += linesOfCodes.get(i).length();
            index += 1; // length of "\n"
        }
        index += column;

        return index;
    }

    /**
     * Get the range info of a specific line.
     * @param lineNumber - 0 based
     * @return Range - 0 based
     */
    public Range getLineRange(int lineNumber) {
        if (lineNumber < 0 || lineNumber >= linesOfCodes.size()) {
            return null;
        }

        int startIndex = 0;
        int endIndex;

        int i;
        for (i = 0; i < lineNumber; i++) {
            startIndex += linesOfCodes.get(i).length();
            startIndex += 1; // length of "\n"
        }

        endIndex = startIndex + linesOfCodes.get(i).length();

        return new Range(startIndex, endIndex);
    }

    /**
     * Get the texts of a specific line.
     * @param lineNumber - 0 based
     * @return String
     */
    public String getLineText(int lineNumber) {
        if (lineNumber < 0 || lineNumber >= linesOfCodes.size()) {
            return null;
        }

        return linesOfCodes.get(lineNumber);
    }

    /**
     * Returns the character string in a given line of the given length, starting from the
     * given column.
     * lineNumber and startColumn are zero based.
     *
     * @param lineNumber integer specifying the line to extract the characters from
     * @param startColumn integer specifying the start column in the given line
     * @param length integer specifying the amount of characters to extract
     * @ignore
     */
    public String getTextCharacters(int lineNumber, int startColumn, int length) {
        if (lineNumber < 0 || lineNumber >= linesOfCodes.size()) {
            return null;
        }

        String textOfLine = getLineText(lineNumber);
        if (textOfLine != null && (startColumn + length) <= textOfLine.length()) {
            return textOfLine.substring(startColumn, startColumn + length);

        } else if ((startColumn + length) == (textOfLine.length() + 1)) {
            return "\n";

        } else {
            // If startColumn + length exceeds the line range, return null.
            return null;
        }
    }

    /**
     * Returns the indent level of a specific line. Indent level is equal to the number of leading spaces.
     * @param lineNumber
     * @return -1 if lineNumber is invalid.
     */
    public int getIndentLevel(int lineNumber) {
        if (lineNumber < 0 || lineNumber >= linesOfCodes.size()) {
            return -1;
        }

        String line = getLineText(lineNumber);
        int indexOfFirstNonWhiteChar = -1;

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != ' ') {
                indexOfFirstNonWhiteChar = i;
                break;
            }
        }

        // If the line is all white spaces.
        if (indexOfFirstNonWhiteChar == -1) {
            return line.length();
        }

        return indexOfFirstNonWhiteChar;
    }

    /**
     * Insert leading white spaces to a specific line.
     * @param lineNumber
     * @param indentLevel
     * @return false if fails, true if succeeds
     */
    public boolean setLineIndent(int lineNumber, int indentLevel) {
        if (lineNumber < 0 || lineNumber >= linesOfCodes.size()) {
            return false;
        }

        String newLineText = "";
        String lineText = getLineText(lineNumber);
        int indexOfNonWhiteChar = -1;

        for (int i = 0; i < lineText.length(); i++) {
            if (lineText.charAt(i) != ' ') {
                indexOfNonWhiteChar = i;
                break;
            }
        }

        if (indexOfNonWhiteChar > 0) {
            newLineText = lineText.substring(indexOfNonWhiteChar);
        } else {
            newLineText = lineText;
        }

        linesOfCodes.set(lineNumber, generateWhitespaces(indentLevel) + newLineText);
        setCodes(generateCodesFromLines(linesOfCodes));

        return true;
    }

    /**
     * Helper method to generate one String from LinkedList<String>
     * @param linesOfCodes
     * @return
     */
    public static String generateCodesFromLines(LinkedList<String> linesOfCodes) {
        String codes = "";

        for (int i = 0; i < linesOfCodes.size(); i++) {
            codes += linesOfCodes.get(i);
            if (i != linesOfCodes.size() - 1) {
                codes += "\n";
            }
        }

        return codes;
    }

    /**
     * Helper method to generate consecutive white spaces.
     * @param number
     * @return
     */
    public static String generateWhitespaces(int number) {
        if (number < 0) {
            return null;
        }

        String spaces = "";

        for (int i = 0; i < number; i++) {
            spaces += " ";
        }

        return spaces;
    }

    /**
     * Return the line/column pair given the index within the codes string.
     * @param index
     * @return null if index exceeds the length of codes string.
     */
    public PositionInfo getLineColumn(int index) {
        if (index > codes.length() || index < 0) {
            return null;
        }

        int line = -1;
        int column = -1;

        int count = index;
        for (int i = 0; i < linesOfCodes.size(); i++) {
            if (count <= linesOfCodes.get(i).length()) {
                line = i;
                column = count;
                break;
            }

            count -= linesOfCodes.get(i).length();
            count -= 1; // length of EOL
        }

        return new PositionInfo(line, column);
    }

    public class Range {
        private int start;
        private int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }
    }


}
