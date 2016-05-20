package com.chensuworks.nativeeditor.plugins.parenmatch;

import android.text.Editable;
import android.util.Log;

public class ParenMatcher {

    private Editable s;
    private Matcher matcher;
    private String texts;

    private int leftParen;
    private int rightParen;

    public int getLeftParen() {
        return leftParen;
    }

    public int getRightParen() {
        return rightParen;
    }

    public ParenMatcher(Editable s) {
        this.s = s;
        this.matcher = new Matcher(s.toString());
        this.texts = s.toString();

        this.leftParen = -1;
        this.rightParen = -1;
    }

    private void resetLeftAndRightParen() {
        leftParen = -1;
        rightParen = -1;
    }

    // In Android, selStart <= selEnd
    public boolean handleCursorChanged(int selStart, int selEnd) {
        if (texts.length() == 0 || !(selStart >= 0 && selEnd >=0 && selStart <= texts.length() && selEnd <= texts.length())) {
            return false;
        }

        String charBeforeCursor = null;
        String charAfterCursor = null;
        String selectedChar = null;

        if (selStart != selEnd) {
            // If there's a selection of a parenthesis, selStart + 1 = selEnd
            if (selStart + 1 == selEnd) {
                selectedChar = String.valueOf(texts.charAt(selStart));
                scanAndShowAnyMatch(selectedChar.charAt(0), selStart);
                return true;
            }

        } else {
            // It it's a caret, no selection
            if (selStart == 0) {
                charBeforeCursor = null;
                charAfterCursor = String.valueOf(texts.charAt(1));

            } else if (selStart == texts.length()) {
                charBeforeCursor = String.valueOf(texts.charAt(selStart - 1));
                charAfterCursor = null;

            } else if (selStart > 0) {
                charBeforeCursor = String.valueOf(texts.charAt(selStart - 1));
                charAfterCursor = String.valueOf(texts.charAt(selStart));
            }

            // if the char before cursor is a closing parenthesis
            if (charBeforeCursor != null && this.matcher.getCLOSE_PAREN().contains(charBeforeCursor.charAt(0))) {
                scanAndShowAnyMatch(charBeforeCursor.charAt(0), selStart - 1);
                return true;

            } else if (charAfterCursor != null && this.matcher.getOPEN_PAREN().contains(charAfterCursor.charAt(0))){
                // if the char after cursor is an opening parenthesis
                scanAndShowAnyMatch(charAfterCursor.charAt(0), selStart);
                return true;
            }

        }

        resetLeftAndRightParen();
        return false;

    }

    public void scanAndShowAnyMatch(char paren, int charIndex) {
        matcher.scanLeftOrRight(paren, charIndex);
        showMatch(matcher.getLeft(), matcher.getRight());
    }

    public void showMatch(int leftParen, int rightParen) {
        this.leftParen = leftParen;
        this.rightParen = rightParen;

        //Log.d("ParenMatcher", "showMatch: leftParen = " + leftParen + ", rightParen = " + rightParen);
    }

}
