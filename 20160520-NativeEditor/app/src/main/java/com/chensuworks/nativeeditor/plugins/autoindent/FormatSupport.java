package com.chensuworks.nativeeditor.plugins.autoindent;

import com.chensuworks.nativeeditor.plugins.Utils;
import com.chensuworks.nativeeditor.tokenizer.Document;
import com.chensuworks.nativeeditor.tokenizer.PositionInfo;
import com.chensuworks.nativeeditor.tokenizer.Token;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class FormatSupport {

    private Formatter formatter;
    private Document document;

    private static final HashMap<String, LinkedList<String>> KEYWORD_MAP;

    static {
        KEYWORD_MAP = new HashMap<String, LinkedList<String>>();

        LinkedList<String> listE = new LinkedList<>();
        listE.add("else");
        listE.add("case");
        listE.add("otherwise");
        KEYWORD_MAP.put("e", listE);

        LinkedList<String> listF = new LinkedList<>();
        listF.add("elseif");
        KEYWORD_MAP.put("f", listF);

        LinkedList<String> listN = new LinkedList<>();
        listN.add("function");
        KEYWORD_MAP.put("n", listN);

        LinkedList<String> listH = new LinkedList<>();
        listH.add("catch");
        KEYWORD_MAP.put("h", listH);

        LinkedList<String> listD = new LinkedList<>();
        listD.add("end");
        KEYWORD_MAP.put("d", listD);
    }

    public FormatSupport(Document document, Formatter formatter) {
        this.document = document;
        this.formatter = formatter;
    }

    /**
     * Checks if the given text/string is at the end of any of the outdenting keywords.
     *
     * @param text string whose value is to be checked against the keywords
     * @return boolean true if any outdenting keywords end in the given text.
     * @private
     */
    public boolean _isStringAtEndOfKeyword(String text) {
        return KEYWORD_MAP.containsKey(text);
    }

    public LinkedList<String> _getKeywordsEndingInString(String text) {
        return KEYWORD_MAP.get(text);
    }

    public boolean _doesTokenMatch(Token token, LinkedList<String> tokenArray, int index, PositionInfo cursorPosition) {
        PositionInfo pos = token.getPositionInfo();
        return (pos.getColumn() >= 0) &&
                (pos.getColumn() + tokenArray.get(index).length() == cursorPosition.getColumn()) &&
                document.getTextCharacters(cursorPosition.getLine(), pos.getColumn(), tokenArray.get(index).length()).equals(tokenArray.get(index));
    }

    public NoWhitespaceChar _goBackUntilNoWhitespace(String typedText, PositionInfo cursorPosition) {
        while (typedText != null && Utils.isWhitespace(typedText) && cursorPosition.getColumn() > 1) {
            cursorPosition.setColumn(cursorPosition.getColumn() - 1);
            typedText = document.getTextCharacters(cursorPosition.getLine(), cursorPosition.getColumn() - 1, 1);
        }
        return new NoWhitespaceChar(typedText, cursorPosition);
    }

    public class NoWhitespaceChar {
        private String typedText;
        private PositionInfo cursorPosition;

        public NoWhitespaceChar(String typedText, PositionInfo cursorPosition) {
            this.typedText = typedText;
            this.cursorPosition = cursorPosition;
        }

        public String getTypedText() {
            return typedText;
        }

        public void setTypedText(String typedText) {
            this.typedText = typedText;
        }

        public PositionInfo getCursorPosition() {
            return cursorPosition;
        }

        public void setCursorPosition(PositionInfo cursorPosition) {
            this.cursorPosition = cursorPosition;
        }
    }

    public boolean _didDeletionOccur(PositionInfo cursorPosition, String typedText) {
        return cursorPosition.getColumn() > 0 &&
                document.getTextCharacters(cursorPosition.getLine(), cursorPosition.getColumn() - 1, 1) != typedText;
    }

    public boolean _checkCursorPositionForToken(PositionInfo cursorPosition) {
        String typedText = document.getTextCharacters(cursorPosition.getLine(), cursorPosition.getColumn() - 1, 1);
        return _checkTextMatchesToken(typedText, cursorPosition);
    }

    /**
     * Check if adding a new character caused the previous word to change from keyword to
     * non-keyword or vice versa.
     *
     * @param typedText string specifying the inserted character
     *                  Example: 'a'
     * @param cursorPosition Object containing the position of the cursor
     *                       Example: {
     *                          line: 0,
     *                          column: 5
     *                       }
     * @return {boolean}
     * @private
     */
    public boolean _checkPreviousChar(String typedText, PositionInfo cursorPosition) {
        boolean retVal = false;

        if (Utils.isWhitespace(typedText)) {
            // Go back one character at a time, until we find a non whitespace
            NoWhitespaceChar retObj = _goBackUntilNoWhitespace(typedText, cursorPosition);
            // Check if the non whitespace character is end of a keyword
            retVal = _checkTextMatchesToken(retObj.getTypedText(), retObj.getCursorPosition());

        } else if (_didDeletionOccur(cursorPosition, typedText)) {
            // If the typed character is not in the document, a delete or backspace occurred
            // Use the cursor position in such a case.
            retVal = _checkCursorPositionForToken(cursorPosition);

        } else if (cursorPosition.getColumn() > 1) {
            // Else try backing up one character to see if typed text changed a keyword into
            // an identifier or a non-keyword into one.
            cursorPosition.setColumn(cursorPosition.getColumn() - 1);
            retVal = _checkCursorPositionForToken(cursorPosition);
        }

        return retVal;
    }

    /**
     * Checks if the token corresponding to the text entered by user matches the last character
     * of any of the outdenting keywords.
     *
     * @param typedText string containing inserted text
     * @param cursorPosition Object containing the position of the cursor
     *                       Example: {
     *                          line: 0,
     *                          column: 5
     *                       }
     * @return {boolean}
     */
    public boolean _checkTextMatchesToken(String typedText, PositionInfo cursorPosition) {
        boolean doesMatchToken = false;

        if (_isStringAtEndOfKeyword(typedText)) {
            Token firstNonWhiteToken = formatter.findFirstNonWhitespaceTokenInLine(cursorPosition.getLine());
            LinkedList<String> tokens = _getKeywordsEndingInString(typedText);
            int index;
            if (firstNonWhiteToken != null) {
                for (index = 0; index < tokens.size(); index++) {
                    if (_doesTokenMatch(firstNonWhiteToken, tokens, index, cursorPosition)) {
                        doesMatchToken = true;
                        break;
                    }
                }
            }
        }

        return doesMatchToken;
    }

    /**
     * Checks if the inserted character should trigger an indent of the line
     *
     * @param pressedKey string specifying the pressed key character
     * @param cursorPosition Object containing the position of the cursor
     *                       Example: {
     *                          line: 0,
     *                          column: 5
     *                       }
     * @return boolean true if line should be re-indented, false otherwise
     */
    public boolean shouldKeyTriggerFormat(String pressedKey, PositionInfo cursorPosition) {
        boolean ret = _checkTextMatchesToken(pressedKey, cursorPosition);

        if (ret == false && pressedKey.length() == 1) {
            // Check if a previous keyword didn't "unbecome" OR "become" a keyword due
            // to a new character.
            ret = _checkPreviousChar(pressedKey, cursorPosition);
        }

        return ret;
    }


}
