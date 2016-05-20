package com.chensuworks.nativeeditor.plugins.autoindent;

import android.test.ActivityTestCase;
import android.text.Editable;

import com.chensuworks.nativeeditor.MatlabEditText;
import com.chensuworks.nativeeditor.tokenizer.Document;
import com.chensuworks.nativeeditor.tokenizer.MatlabTokens;
import com.chensuworks.nativeeditor.tokenizer.PositionInfo;
import com.chensuworks.nativeeditor.tokenizer.Token;
import com.chensuworks.nativeeditor.tokenizer.TokenConstants;

public class FormatSupportUnitTest extends ActivityTestCase {

    private DocumentMock documentMock = new DocumentMock("");
    private MatlabTokens tokenizerService = new MatlabTokens(documentMock);
    private FormatterMock formatterMock = new FormatterMock(null, null, documentMock, tokenizerService);

    public class FormatterMock extends Formatter {

        private Token _token = null;

        public FormatterMock(MatlabEditText editor, Editable s, Document matlabDocument, MatlabTokens matlabTokens) {
            super(editor, s, matlabDocument, matlabTokens);
        }

        @Override
        public Token findFirstNonWhitespaceTokenInLine(int lineNumber) {
            return _token;
        }

        public void setToken(Token token) {
            _token = token;
        }

    }

    public class DocumentMock extends Document {

        private String[] _characters = null;
        private int _counter = -1;

        public DocumentMock(String codes) {
            super(codes);
        }

        public void reset() {
            _counter = -1;
        }

        public void setCharacters(String[] textArray) {
            _characters = textArray;
        }

        @Override
        public String getTextCharacters(int lineNumber, int startColumn, int length) {
            _counter += 1;
            if (_counter < _characters.length) {
                return _characters[_counter];
            } else {
                return null;
            }
        }

    }

    public void testShouldCharTriggerFormat() {


        FormatSupport formatSupport = new FormatSupport(documentMock, formatterMock);
        String[] strings = new String[1];

        // True cases
        formatterMock.setToken(new Token(0, TokenConstants.tokenConstants.get("ELSE"), 0, 0, 4, "else"));
        strings[0] = "else";
        documentMock.setCharacters(strings);
        boolean returnValue = formatSupport.shouldKeyTriggerFormat("e", new PositionInfo(0, 4));
        assertTrue(returnValue);

        documentMock.reset();
        formatterMock.setToken(new Token(0, TokenConstants.tokenConstants.get("ELSEIF"), 0, 0, 6, "elseif"));
        strings[0] = "elseif";
        documentMock.setCharacters(strings);
        returnValue = formatSupport.shouldKeyTriggerFormat("f", new PositionInfo(0, 6));
        assertTrue(returnValue);

        documentMock.reset();
        formatterMock.setToken(new Token(0, TokenConstants.tokenConstants.get("FUNCTION"), 0, 0, 8, "function"));
        strings[0] = "function";
        documentMock.setCharacters(strings);
        returnValue = formatSupport.shouldKeyTriggerFormat("n", new PositionInfo(0, 8));
        assertTrue(returnValue);

        documentMock.reset();
        formatterMock.setToken(new Token(0, TokenConstants.tokenConstants.get("CATCH"), 0, 0, 5, "catch"));
        strings[0] = "catch";
        documentMock.setCharacters(strings);
        returnValue = formatSupport.shouldKeyTriggerFormat("h", new PositionInfo(0, 5));
        assertTrue(returnValue);

        documentMock.reset();
        formatterMock.setToken(new Token(0, TokenConstants.tokenConstants.get("END"), 0, 0, 3, "end"));
        strings[0] = "end";
        documentMock.setCharacters(strings);
        returnValue = formatSupport.shouldKeyTriggerFormat("d", new PositionInfo(0, 3));
        assertTrue(returnValue);

        documentMock.reset();
        formatterMock.setToken(new Token(0, TokenConstants.tokenConstants.get("CASE"), 0, 0, 4, "case"));
        strings = new String[2];
        strings[0] = "case";
        strings[1] = "case";
        documentMock.setCharacters(strings);
        returnValue = formatSupport.shouldKeyTriggerFormat("e", new PositionInfo(0, 4));
        assertTrue(returnValue);

        documentMock.reset();
        formatterMock.setToken(new Token(0, TokenConstants.tokenConstants.get("OTHERWISE"), 0, 0, 9, "otherwise"));
        strings = new String[3];
        strings[0] = "otherwise";
        strings[1] = "otherwise";
        strings[2] = "otherwise";
        documentMock.setCharacters(strings);
        returnValue = formatSupport.shouldKeyTriggerFormat("e", new PositionInfo(0, 9));
        assertTrue(returnValue);

        // False case
        //documentMock = new DocumentMock("");
        documentMock.reset();
        formatterMock.setToken(new Token(0, TokenConstants.tokenConstants.get("ID"), 0, 0, 3, "foo"));
        strings = new String[1];
        strings[0] = "foo";
        documentMock.setCharacters(strings);
        returnValue = formatSupport.shouldKeyTriggerFormat("o", new PositionInfo(0, 3));
        assertFalse(returnValue);

        // False case with possible ending letter
        documentMock.reset();
        formatterMock.setToken(new Token(0, TokenConstants.tokenConstants.get("ID"), 0, 0, 3, "foe"));
        strings[0] = "foe";
        documentMock.setCharacters(strings);
        returnValue = formatSupport.shouldKeyTriggerFormat("e", new PositionInfo(0, 3));
        assertFalse(returnValue);

        // False case and typed text length greater than 1
        documentMock.reset();
        formatterMock.setToken(new Token(0, TokenConstants.tokenConstants.get("ID"), 0, 0, 3, "foo"));
        strings[0] = "foo";
        documentMock.setCharacters(strings);
        returnValue = formatSupport.shouldKeyTriggerFormat("oo", new PositionInfo(0, 3));
        assertFalse(returnValue);

        // Testing checkPreviousChar else -> elsei
        documentMock.reset();
        formatterMock.setToken(new Token(0, TokenConstants.tokenConstants.get("ID"), 0, 0, 5, "elsei"));
        // i for second else in _checkPreviousChars. The rest two are for subsequent calls to
        // getTextCharacters in _checkCursorPositionForToken.
        strings = new String[3];
        strings[0] = "i";
        strings[1] = "e";
        strings[2] = "else";
        documentMock.setCharacters(strings);
        returnValue = formatSupport.shouldKeyTriggerFormat("i", new PositionInfo(0, 5));
        assertTrue(returnValue);

        // Testing checkPreviousChar 'else' -> 'else '
        documentMock.reset();
        formatterMock.setToken(new Token(0, TokenConstants.tokenConstants.get("ELSE"), 0, 0, 4, "else"));
        strings = new String[2];
        strings[0] = "e";
        strings[1] = "else";
        documentMock.setCharacters(strings);
        returnValue = formatSupport.shouldKeyTriggerFormat(" ", new PositionInfo(0, 5));
        assertTrue(returnValue);

        // Testing checkPreviousChar backspace and delete
        documentMock.reset();
        formatterMock.setToken(new Token(0, TokenConstants.tokenConstants.get("ELSE"), 0, 0, 4, "else"));
        // e for second else in _checkPreviousChars. Next e for next call since we are
        // accessing same character again.
        strings = new String[3];
        strings[0] = "e";
        strings[1] = "e";
        strings[2] = "else";
        documentMock.setCharacters(strings);
        returnValue = formatSupport.shouldKeyTriggerFormat("\b", new PositionInfo(0, 4));
        assertTrue(returnValue);
    }

}
