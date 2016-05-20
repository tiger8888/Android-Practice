package com.chensuworks.nativeeditor.tokenizer;

import android.test.ActivityTestCase;

import com.chensuworks.nativeeditor.tokenizer.PositionInfo;
import com.chensuworks.nativeeditor.tokenizer.Token;
import com.chensuworks.nativeeditor.tokenizer.TokenConstants;

public class TokenUnitTest extends ActivityTestCase {

    public void testTokenAPI() {
        Token token = new Token(1, TokenConstants.tokenConstants.get("END"), 0, 3, 3, "end");

        assertEquals(token.getIndex(), 1);
        assertEquals(token.getType(), (int) TokenConstants.tokenConstants.get("END"));
        assertEquals(token.getValue(), "end");

        PositionInfo positionInfo = token.getPositionInfo();
        assertEquals(positionInfo.getLine(), 0);
        assertEquals(positionInfo.getColumn(), 3);
    }

    public void testEquals() {
        Token token1 = new Token(1, TokenConstants.tokenConstants.get("END"), 0, 3, 3, "end");
        Token token2 = new Token(1, TokenConstants.tokenConstants.get("END"), 0, 3, 3, "end");
        assertTrue(token1.equals(token2));

        // Column is different
        token1 = new Token(1, TokenConstants.tokenConstants.get("END"), 0, 3, 3, "end");
        token2 = new Token(1, TokenConstants.tokenConstants.get("END"), 0, 7, 3, "end");
        assertFalse(token1.equals(token2));

        // Index is different
        token1 = new Token(1, TokenConstants.tokenConstants.get("END"), 0, 3, 3, "end");
        token2 = new Token(2, TokenConstants.tokenConstants.get("END"), 0, 3, 3, "end");
        assertFalse(token1.equals(token2));

        // Type and length are different
        token1 = new Token(1, TokenConstants.tokenConstants.get("END"), 0, 3, 3, "end");
        token2 = new Token(1, TokenConstants.tokenConstants.get("ELSE"), 0, 3, 4, "else");
        assertFalse(token1.equals(token2));

        // Index, line, column and type are different
        token1 = new Token(1, TokenConstants.tokenConstants.get("END"), 0, 3, 3, "end");
        token2 = new Token(2, TokenConstants.tokenConstants.get("ELSE"), 1, 3, 4, "else");
        assertFalse(token1.equals(token2));
    }

}
