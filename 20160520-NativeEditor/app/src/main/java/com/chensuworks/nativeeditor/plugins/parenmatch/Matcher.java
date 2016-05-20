package com.chensuworks.nativeeditor.plugins.parenmatch;

import java.util.LinkedList;

public class Matcher {

    private LinkedList<Character> OPEN_PAREN;
    private LinkedList<Character> CLOSE_PAREN;

    private String texts;

    private int left;
    private int right;

    public Matcher(String texts) {
        this.OPEN_PAREN = new LinkedList<Character>();
        this.OPEN_PAREN.add('(');
        this.OPEN_PAREN.add('[');
        this.OPEN_PAREN.add('{');

        this.CLOSE_PAREN = new LinkedList<Character>();
        this.CLOSE_PAREN.add(')');
        this.CLOSE_PAREN.add(']');
        this.CLOSE_PAREN.add('}');

        this.left = -1;
        this.right = -1;

        this.texts = texts;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public LinkedList<Character> getOPEN_PAREN() {
        return OPEN_PAREN;
    }

    public LinkedList<Character> getCLOSE_PAREN() {
        return CLOSE_PAREN;
    }

    /**
     *
     * @param paren
     * @param charIndex
     */
    // Determines which direction to scan the document and scans for paren matches.
    // Sets this.left and this.right to the result of the scan.
    public void scanLeftOrRight(char paren, int charIndex) {
        int parenIndex = OPEN_PAREN.indexOf(paren);
        int direction = 0;
        left = -1;
        right = -1;

        if (parenIndex >= 0) {
            direction = 1;
            left = charIndex;
            right = -1;

        } else {
            parenIndex = CLOSE_PAREN.indexOf(paren);
            if (parenIndex >= 0) {
                direction = -1;
                right = charIndex;
                left = -1;
            }
        }

        if (direction == 0) {
            return; // no matching paren
        }

        // find matching paren in document
        scan(charIndex, direction, parenIndex);
    }

    /**
     *
     * @param charIndex
     * @param direction
     * @param parenIndex
     */
    public void scan(int charIndex, int direction, int parenIndex) {
        int found = scanDocument(charIndex, direction, parenIndex);

        if (direction == 1) {
            if (found != -1) {
                right = found;
            }
        } else if (direction == -1) {
            if (found != -1) {
                left = found;
            }
        }
    }

    /**
     * Find the index of correspondent matching parenthesis.
     * @param charIndex - use index of character instead of cursor
     * @param direction - could be 1 (forward) or -1 (backward)
     * @param parenIndex
     * @return
     */
    public int scanDocument(int charIndex, int direction, int parenIndex) {

        if (direction == 1) {
            char openParen = OPEN_PAREN.get(parenIndex);
            char closeParen = CLOSE_PAREN.get(parenIndex); // look for the matching char
            int counter = 0;
            for (int i = charIndex; i < texts.length(); i++) {
                if (texts.charAt(i) == closeParen) {
                    counter--;
                }
                if (texts.charAt(i) == openParen) {
                    counter++;
                }
                if (counter == 0) {
                    return i;
                }
            }

        } else if (direction == -1) {
            char closeParen = CLOSE_PAREN.get(parenIndex);
            char openParen = OPEN_PAREN.get(parenIndex); // look for the matching char
            int counter = 0;
            for (int i = charIndex; i >= 0; i--) {
                if (texts.charAt(i) == openParen) {
                    counter--;
                }
                if (texts.charAt(i) == closeParen) {
                    counter++;
                }
                if (counter == 0) {
                    return i;
                }
            }

        }

        return -1;
    }

}
