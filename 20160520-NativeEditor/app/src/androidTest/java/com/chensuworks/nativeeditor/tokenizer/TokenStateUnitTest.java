package com.chensuworks.nativeeditor.tokenizer;

import android.test.ActivityTestCase;

import com.chensuworks.nativeeditor.tokenizer.Token;
import com.chensuworks.nativeeditor.tokenizer.TokenConstants;
import com.chensuworks.nativeeditor.tokenizer.TokenState;
import com.chensuworks.nativeeditor.tokenizer.Tokenizer;

import java.util.LinkedList;

public class TokenStateUnitTest extends ActivityTestCase {

    public void testDefaultTokenizerState() {
        TokenState tokenState = new TokenState();
        Tokenizer.LexState savedState = tokenState.getLexStateForLine(0);

        Tokenizer.LexState expectedState = new Tokenizer.LexState(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
        assertTrue(tokenState.areStatesEqual(savedState, expectedState));
    }

    public void testSizeOfSavedStatesArray() {
        TokenState tokenState = new TokenState();
        assertEquals(tokenState.getNumberOfSavedStates(), 1);

        tokenState.tokenizeCode("%% Section1", 0, tokenState.getLexStateForLine(0));
        tokenState.tokenizeCode("if true surf(peaks);", 1, tokenState.getLexStateForLine(1));
        tokenState.tokenizeCode("end", 2, tokenState.getLexStateForLine(2));
        tokenState.tokenizeCode("disp('hi')", 3, tokenState.getLexStateForLine(3));
        assertEquals(tokenState.getNumberOfSavedStates(), 5);

        // delete a couple of lines of code, and check that the saved states for deleted lines are deleted
        // delete line 2 and 3
        tokenState.retokenize("%% Section1\ndisp('hi')", 1);
        assertEquals(tokenState.getNumberOfSavedStates(), 3);
    }

    public void testTokenizeCode() {
        TokenState tokenState = new TokenState();
        LinkedList<Token> tokens;
        Token token;

        tokenState.tokenizeCode("function foo", 0, tokenState.getLexStateForLine(0));
        tokenState.tokenizeCode("if true plot(1:10);", 1, tokenState.getLexStateForLine(1));

        tokens = tokenState.get_tokenMap().get(0);
        token = new Token(0, TokenConstants.tokenConstants.get("FUNCTION"), 0, 0, 8, "function");
        assertTrue(token.equals(tokens.get(0)));
        token = new Token(1, TokenConstants.tokenConstants.get("WHITE"), 0, 8, 1, " ");
        assertTrue(token.equals(tokens.get(1)));
        token = new Token(2, TokenConstants.tokenConstants.get("ID"), 0, 9, 3, "foo");
        assertTrue(token.equals(tokens.get(2)));

        tokens = tokenState.get_tokenMap().get(1);
        token = new Token(0, TokenConstants.tokenConstants.get("IF"), 1, 0, 2, "if");
        assertTrue(token.equals(tokens.get(0)));
        token = new Token(1, TokenConstants.tokenConstants.get("WHITE"), 1, 2, 1, " ");
        assertTrue(token.equals(tokens.get(1)));
        token = new Token(2, TokenConstants.tokenConstants.get("ID"), 1, 3, 4, "true");
        assertTrue(token.equals(tokens.get(2)));
        token = new Token(3, TokenConstants.tokenConstants.get("WHITE"), 1, 7, 1, " ");
        assertTrue(token.equals(tokens.get(3)));
        token = new Token(4, TokenConstants.tokenConstants.get("ID"), 1, 8, 4, "plot");
        assertTrue(token.equals(tokens.get(4)));
        token = new Token(5, TokenConstants.tokenConstants.get("LP"), 1, 12, 1, "(");
        assertTrue(token.equals(tokens.get(5)));
        token = new Token(6, TokenConstants.tokenConstants.get("INT"), 1, 13, 1, "1");
        assertTrue(token.equals(tokens.get(6)));
        token = new Token(7, TokenConstants.tokenConstants.get("COLON"), 1, 14, 1, ":");
        assertTrue(token.equals(tokens.get(7)));
        token = new Token(8, TokenConstants.tokenConstants.get("INT"), 1, 15, 2, "10");
        assertTrue(token.equals(tokens.get(8)));
        token = new Token(9, TokenConstants.tokenConstants.get("RP"), 1, 17, 1, ")");
        assertTrue(token.equals(tokens.get(9)));
        token = new Token(10, TokenConstants.tokenConstants.get("SEMI"), 1, 18, 1, ";");
        assertTrue(token.equals(tokens.get(10)));
    }

    public void testRetokenize() {
        TokenState tokenState = new TokenState();
        LinkedList<Token> tokens;
        Token token;

        tokenState.tokenizeCode("%% Section1", 0, tokenState.getLexStateForLine(0));
        tokenState.tokenizeCode("if true surf(peaks);", 1, tokenState.getLexStateForLine(1));
        tokenState.tokenizeCode("end", 2, tokenState.getLexStateForLine(2));
        tokenState.tokenizeCode("disp('hi')", 3, tokenState.getLexStateForLine(3));

        assertEquals(tokenState.getNumberOfTokensOnLine(0), 2);
        assertEquals(tokenState.getNumberOfTokensOnLine(1), 10);
        assertEquals(tokenState.getNumberOfTokensOnLine(2), 2);
        assertEquals(tokenState.getNumberOfTokensOnLine(3), 5);

        // Simulate deletion of middle line, line 2
        tokenState.retokenize("%% Section1\nif true surf(peaks);\ndisp('hi')", 2);
        assertEquals(tokenState.getNumberOfTokensOnLine(0), 2);
        assertEquals(tokenState.getNumberOfTokensOnLine(1), 10);
        assertEquals(tokenState.getNumberOfTokensOnLine(2), 5);
        assertEquals(tokenState.getNumberOfTokensOnLine(3), -1);

        tokens = tokenState.get_tokenMap().get(2);
        token = new Token(0, TokenConstants.tokenConstants.get("ID"), 2, 0, 4, "disp");
        assertTrue(token.equals(tokens.get(0)));
        token = new Token(1, TokenConstants.tokenConstants.get("LP"), 2, 4, 1, "(");
        assertTrue(token.equals(tokens.get(1)));
        token = new Token(2, TokenConstants.tokenConstants.get("STRING"), 2, 5, 4, "'hi'");
        assertTrue(token.equals(tokens.get(2)));
        token = new Token(3, TokenConstants.tokenConstants.get("RP"), 2, 9, 1, ")");
        assertTrue(token.equals(tokens.get(3)));

        // Simulate deletion of last line
        tokenState.retokenize("%% Section1\nif true surf(peaks);", 2);
        assertEquals(tokenState.getNumberOfTokensOnLine(0), 2);
        assertEquals(tokenState.getNumberOfTokensOnLine(1), 10);
        assertEquals(tokenState.getNumberOfTokensOnLine(2), -1);

        // Simulate deletion of first line
        tokenState.retokenize("if true surf(peaks);", 0);
        assertEquals(tokenState.getNumberOfTokensOnLine(0), 10);
        assertEquals(tokenState.getNumberOfTokensOnLine(1), -1);

        tokens = tokenState.get_tokenMap().get(0);
        token = new Token(0, TokenConstants.tokenConstants.get("IF"), 0, 0, 2, "if");
        assertTrue(token.equals(tokens.get(0)));
        token = new Token(1, TokenConstants.tokenConstants.get("WHITE"), 0, 2, 1, " ");
        assertTrue(token.equals(tokens.get(1)));
        token = new Token(2, TokenConstants.tokenConstants.get("ID"), 0, 3, 4, "true");
        assertTrue(token.equals(tokens.get(2)));
        token = new Token(3, TokenConstants.tokenConstants.get("WHITE"), 0, 7, 1, " ");
        assertTrue(token.equals(tokens.get(3)));
        token = new Token(4, TokenConstants.tokenConstants.get("ID"), 0, 8, 4, "surf");
        assertTrue(token.equals(tokens.get(4)));
        token = new Token(5, TokenConstants.tokenConstants.get("LP"), 0, 12, 1, "(");
        assertTrue(token.equals(tokens.get(5)));
        token = new Token(6, TokenConstants.tokenConstants.get("ID"), 0, 13, 5, "peaks");
        assertTrue(token.equals(tokens.get(6)));
        token = new Token(7, TokenConstants.tokenConstants.get("RP"), 0, 18, 1, ")");
        assertTrue(token.equals(tokens.get(7)));
        token = new Token(8, TokenConstants.tokenConstants.get("SEMI"), 0, 19, 1, ";");
        assertTrue(token.equals(tokens.get(8)));
    }

    public void testGetLastToken() {
        TokenState tokenState = new TokenState();
        Token lastToken;
        Token token;

        tokenState.tokenizeCode("function bar", 0, tokenState.getLexStateForLine(0));
        tokenState.tokenizeCode("%% Section1", 1, tokenState.getLexStateForLine(1));

        assertEquals(tokenState.getNumberOfTokensOnLine(0), 4);
        assertEquals(tokenState.getNumberOfTokensOnLine(1), 2);

        // TODO: TokenStateUnitTest.js bug: assert.ok(lastToken.equals(lastToken), "Last...");
        // TODO: I need to either fix TokenStateUnitTest.js directly or inform RTC team.
        token = new Token(2, TokenConstants.tokenConstants.get("ID"), 0, 9, 3, "bar");
        lastToken = tokenState.getLastToken(0);
        assertTrue(lastToken.equals(token));

        // TODO: TokenStateUnitTest.js bug: token = new Token(0, TokenConstants.ID, 1, 0, 11, "%% Section1");
        // TODO: should be TokenConstants.SECTION_TITLE
        // TODO: TokenStateUnitTest.js didn't catch it because assert.ok(lastToken.equals(lastToken), "Last...");
        token = new Token(0, TokenConstants.tokenConstants.get("SECTION_TITLE"), 1, 0, 11, "%% Section1");
        lastToken = tokenState.getLastToken(1);
        assertTrue(lastToken.equals(token));

        lastToken = tokenState.getLastToken(2);
        assertTrue(lastToken == null);
    }

    public void testGetFirstToken() {
        TokenState tokenState = new TokenState();
        Token firstToken;
        Token token;

        tokenState.tokenizeCode("if true", 0, tokenState.getLexStateForLine(0));
        tokenState.tokenizeCode("    disp('hi')", 1, tokenState.getLexStateForLine(1));

        assertEquals(tokenState.getNumberOfTokensOnLine(0), 4);
        assertEquals(tokenState.getNumberOfTokensOnLine(1), 6);

        token = new Token(0, TokenConstants.tokenConstants.get("IF"), 0, 0, 2, "if");
        firstToken = tokenState.getFirstToken(0);
        assertTrue(firstToken.equals(token));

        token = new Token(0, TokenConstants.tokenConstants.get("WHITE"), 1, 0, 4, "    ");
        firstToken = tokenState.getFirstToken(1);
        assertTrue(firstToken.equals(token));

        firstToken = tokenState.getFirstToken(2);
        assertTrue(firstToken == null);
    }

    public void testGetNextToken() {
        TokenState tokenState = new TokenState();
        Token token;
        Token nextToken;

        tokenState.tokenizeCode("for i=1:10", 0, tokenState.getLexStateForLine(0));
        tokenState.tokenizeCode("disp('hi')", 1, tokenState.getLexStateForLine(1));
        tokenState.tokenizeCode("end", 2, tokenState.getLexStateForLine(2));

        assertEquals(tokenState.getNumberOfTokensOnLine(0), 8);
        assertEquals(tokenState.getNumberOfTokensOnLine(1), 5);
        assertEquals(tokenState.getNumberOfTokensOnLine(2), 2);

        token = tokenState.getFirstToken(0);
        nextToken = new Token(0, TokenConstants.tokenConstants.get("FOR"), 0, 0, 3, "for");
        assertTrue(nextToken.equals(token));

        nextToken = tokenState.getNextToken(token);
        token = new Token(1, TokenConstants.tokenConstants.get("WHITE"), 0, 3, 1, " ");
        assertTrue(nextToken.equals(token));

        nextToken = tokenState.getNextToken(nextToken);
        token = new Token(2, TokenConstants.tokenConstants.get("ID"), 0, 4, 1, "i");
        assertTrue(nextToken.equals(token));

        nextToken = tokenState.getLastToken(0);
        token = new Token(6, TokenConstants.tokenConstants.get("INT"), 0, 8, 2, "10");
        assertTrue(nextToken.equals(token));

        //test that if the current line ends, then return null
        nextToken = tokenState.getNextToken(nextToken);
        assertTrue(nextToken == null);

        nextToken = tokenState.getLastToken(2);
        token = new Token(0, TokenConstants.tokenConstants.get("END"), 2, 0, 3, "end");
        assertTrue(nextToken.equals(token));

        nextToken = tokenState.getNextToken(nextToken);
        assertTrue(nextToken == null);
    }

    public void testGetPreviousToken() {
        TokenState tokenState = new TokenState();
        Token token;
        Token prevToken;

        tokenState.tokenizeCode("for i=1:10", 0, tokenState.getLexStateForLine(0));
        tokenState.tokenizeCode("", 1, tokenState.getLexStateForLine(1));
        tokenState.tokenizeCode("", 2, tokenState.getLexStateForLine(2));
        tokenState.tokenizeCode("", 3, tokenState.getLexStateForLine(3));
        tokenState.tokenizeCode("disp('hi')", 4, tokenState.getLexStateForLine(4));
        tokenState.tokenizeCode("end", 5, tokenState.getLexStateForLine(5));

        assertEquals(tokenState.getNumberOfTokensOnLine(0), 8);
        assertEquals(tokenState.getNumberOfTokensOnLine(1), 1);
        assertEquals(tokenState.getNumberOfTokensOnLine(2), 1);
        assertEquals(tokenState.getNumberOfTokensOnLine(3), 1);
        assertEquals(tokenState.getNumberOfTokensOnLine(4), 5);
        assertEquals(tokenState.getNumberOfTokensOnLine(5), 2);

        prevToken = tokenState.getLastToken(5);
        token = new Token(0, TokenConstants.tokenConstants.get("END"), 5, 0, 3, "end");
        assertTrue(prevToken.equals(token));

        prevToken = tokenState.getPreviousToken(prevToken, 5);
        token = new Token(3, TokenConstants.tokenConstants.get("RP"), 4, 9, 1, ")");
        assertTrue(prevToken.equals(token));

        prevToken = tokenState.getFirstToken(4);
        token = new Token(0, TokenConstants.tokenConstants.get("ID"), 4, 0, 4, "disp");
        assertTrue(prevToken.equals(token));

        prevToken = tokenState.getPreviousToken(prevToken, 4);
        token = new Token(6, TokenConstants.tokenConstants.get("INT"), 0, 8, 2, "10");
        assertTrue(prevToken.equals(token));

        prevToken = tokenState.getPreviousToken(null, 3);
        token = new Token(6, TokenConstants.tokenConstants.get("INT"), 0, 8, 2, "10");
        assertTrue(prevToken.equals(token));

        prevToken = tokenState.getFirstToken(0);
        token = new Token(0, TokenConstants.tokenConstants.get("FOR"), 0, 0, 3, "for");
        assertTrue(prevToken.equals(token));

        prevToken = tokenState.getPreviousToken(prevToken, 0);
        assertTrue(prevToken == null);
    }

    public void testFirstLastTokensOnEmptyLines() {
        TokenState tokenState = new TokenState();
        tokenState.tokenizeCode("", 0, tokenState.getLexStateForLine(0));
        tokenState.tokenizeCode("", 1, tokenState.getLexStateForLine(1));

        Token firstToken;
        Token lastToken;

        firstToken = tokenState.getFirstToken(0);
        assertTrue(firstToken == null);

        lastToken = tokenState.getLastToken(1);
        assertTrue(lastToken == null);
    }

    public void testRetokenizeFillsUndefined() {
        TokenState tokenState = new TokenState();

        // Pass in a value higher than 0 so that 0-2 are filled with []
        tokenState.retokenize("x=1\ny=1\nz=1\na=1", 3);

        assertFalse(tokenState.get_tokenMap().get(0) == null);
        assertFalse(tokenState.get_tokenMap().get(1) == null);
        assertFalse(tokenState.get_tokenMap().get(2) == null);
        assertFalse(tokenState.get_tokenMap().get(3) == null);
    }

}
