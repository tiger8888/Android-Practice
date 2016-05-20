package com.chensuworks.nativeeditor.tokenizer;

import android.test.ActivityTestCase;

import com.chensuworks.nativeeditor.tokenizer.Token;
import com.chensuworks.nativeeditor.tokenizer.TokenConstants;
import com.chensuworks.nativeeditor.tokenizer.TokenUtilities;

public class TokenUtilitiesUnitTest extends ActivityTestCase {

    public void testIsTokenStartOfBlock() {
        Token token = new Token(1, TokenConstants.tokenConstants.get("FOR"), 0, 3, 3, "for");
        assertTrue(TokenUtilities.isTokenStartOfBlock(token));

        token = new Token(2, TokenConstants.tokenConstants.get("PARFOR"), 1, 3, 6, "parfor");
        assertTrue(TokenUtilities.isTokenStartOfBlock(token));

        token = new Token(3, TokenConstants.tokenConstants.get("IF"), 2, 3, 2, "if");
        assertTrue(TokenUtilities.isTokenStartOfBlock(token));

        token = new Token(4, TokenConstants.tokenConstants.get("WHILE"), 3, 3, 5, "while");
        assertTrue(TokenUtilities.isTokenStartOfBlock(token));

        token = new Token(5, TokenConstants.tokenConstants.get("TRY"), 4, 3, 3, "try");
        assertTrue(TokenUtilities.isTokenStartOfBlock(token));

        token = new Token(6, TokenConstants.tokenConstants.get("SWITCH"), 5, 3, 6, "switch");
        assertTrue(TokenUtilities.isTokenStartOfBlock(token));

        token = new Token(7, TokenConstants.tokenConstants.get("FUNCTION"), 6, 3, 8, "function");
        assertTrue(TokenUtilities.isTokenStartOfBlock(token));

        token = new Token(7, TokenConstants.tokenConstants.get("CLASSDEF"), 6, 3, 5, "class");
        assertTrue(TokenUtilities.isTokenStartOfBlock(token));

        token = new Token(7, TokenConstants.tokenConstants.get("PROPERTIES"), 6, 3, 10, "properties");
        assertTrue(TokenUtilities.isTokenStartOfBlock(token));

        token = new Token(7, TokenConstants.tokenConstants.get("ENUMERATION"), 6, 3, 11, "enumeration");
        assertTrue(TokenUtilities.isTokenStartOfBlock(token));

        token = new Token(7, TokenConstants.tokenConstants.get("SPMD"), 6, 3, 4, "spmd");
        assertTrue(TokenUtilities.isTokenStartOfBlock(token));

        token = new Token(7, TokenConstants.tokenConstants.get("EVENTS"), 6, 3, 6, "events");
        assertTrue(TokenUtilities.isTokenStartOfBlock(token));

        token = new Token(7, TokenConstants.tokenConstants.get("METHODS"), 6, 3, 7, "methods");
        assertTrue(TokenUtilities.isTokenStartOfBlock(token));

        token = new Token(7, TokenConstants.tokenConstants.get("WHITE"), 6, 3, 2, "  ");
        assertFalse(TokenUtilities.isTokenStartOfBlock(token));

        token = new Token(7, TokenConstants.tokenConstants.get("END"), 6, 3, 3, "end");
        assertFalse(TokenUtilities.isTokenStartOfBlock(token));
    }

    public void testIsTokenEndOfBlock() {
        Token token = new Token(1, TokenConstants.tokenConstants.get("END"), 0, 3, 3, "end");
        assertTrue(TokenUtilities.isTokenEndOfBlock(token));

        token = new Token(1, TokenConstants.tokenConstants.get("ELSE"), 0, 3, 4, "else");
        assertFalse(TokenUtilities.isTokenEndOfBlock(token));
    }

    public void testIsTokenStartOfMidBlock() {
        Token token = new Token(1, TokenConstants.tokenConstants.get("ELSE"), 0, 3, 4, "else");
        assertTrue(TokenUtilities.isTokenStartOfMidBlock(token));

        token = new Token(2, TokenConstants.tokenConstants.get("ELSEIF"), 1, 3, 6, "elseif");
        assertTrue(TokenUtilities.isTokenStartOfMidBlock(token));

        token = new Token(3, TokenConstants.tokenConstants.get("CATCH"), 2, 3, 5, "catch");
        assertTrue(TokenUtilities.isTokenStartOfMidBlock(token));

        token = new Token(4, TokenConstants.tokenConstants.get("OTHERWISE"), 3, 3, 9, "otherwise");
        assertTrue(TokenUtilities.isTokenStartOfMidBlock(token));

        token = new Token(5, TokenConstants.tokenConstants.get("CASE"), 4, 3, 4, "case");
        assertTrue(TokenUtilities.isTokenStartOfMidBlock(token));

        token = new Token(6, TokenConstants.tokenConstants.get("END"), 5, 3, 3, "end");
        assertFalse(TokenUtilities.isTokenStartOfMidBlock(token));

        token = new Token(7, TokenConstants.tokenConstants.get("FUNCTION"), 6, 3, 8, "function");
        assertFalse(TokenUtilities.isTokenStartOfMidBlock(token));
    }

    public void testIsSectionTitleToken() {
        Token token = new Token(1, TokenConstants.tokenConstants.get("SECTION_TITLE"), 0, 3, 2, "%%");
        assertTrue(TokenUtilities.isSectionTitleToken(token));

        token = new Token(1, TokenConstants.tokenConstants.get("WHITE"), 0, 3, 3, "   ");
        assertFalse(TokenUtilities.isSectionTitleToken(token));
    }

    public void testIsWhitespaceToken() {
        Token token = new Token(1, TokenConstants.tokenConstants.get("WHITE"), 0, 3, 2, "  ");
        assertTrue(TokenUtilities.isWhitespaceToken(token));

        token = new Token(1, TokenConstants.tokenConstants.get("END"), 0, 3, 3, "end");
        assertFalse(TokenUtilities.isWhitespaceToken(token));
    }

    public void testIsTokenStartOfBlockIndent() {
        Token token = new Token(1, TokenConstants.tokenConstants.get("FOR"), 0, 3, 3, "for");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(2, TokenConstants.tokenConstants.get("PARFOR"), 1, 3, 6, "parfor");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(3, TokenConstants.tokenConstants.get("IF"), 2, 3, 2, "if");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(4, TokenConstants.tokenConstants.get("WHILE"), 3, 3, 5, "while");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(5, TokenConstants.tokenConstants.get("TRY"), 4, 3, 3, "try");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(6, TokenConstants.tokenConstants.get("SWITCH"), 5, 3, 6, "switch");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(7, TokenConstants.tokenConstants.get("FUNCTION"), 6, 3, 8, "function");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(7, TokenConstants.tokenConstants.get("CLASSDEF"), 6, 3, 5, "class");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(7, TokenConstants.tokenConstants.get("PROPERTIES"), 6, 3, 10, "properties");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(7, TokenConstants.tokenConstants.get("ENUMERATION"), 6, 3, 11, "enumeration");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(7, TokenConstants.tokenConstants.get("SPMD"), 6, 3, 4, "spmd");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(7, TokenConstants.tokenConstants.get("EVENTS"), 6, 3, 6, "events");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(7, TokenConstants.tokenConstants.get("METHODS"), 6, 3, 7, "methods");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(1, TokenConstants.tokenConstants.get("ELSE"), 0, 3, 4, "else");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(2, TokenConstants.tokenConstants.get("ELSEIF"), 1, 3, 6, "elseif");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(3, TokenConstants.tokenConstants.get("CATCH"), 2, 3, 5, "catch");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(4, TokenConstants.tokenConstants.get("OTHERWISE"), 3, 3, 9, "otherwise");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));

        token = new Token(5, TokenConstants.tokenConstants.get("CASE"), 4, 3, 4, "case");
        assertTrue(TokenUtilities.isTokenStartOfBlockIndent(token));
    }

    public void testIsCommentToken() {
        Token token = new Token(1, TokenConstants.tokenConstants.get("SECTION_TITLE"), 0, 3, 2, "%%");
        assertTrue(TokenUtilities.isCommentToken(token));

        token = new Token(1, TokenConstants.tokenConstants.get("BLKSTART"), 0, 3, 2, "%{");
        assertTrue(TokenUtilities.isCommentToken(token));

        token = new Token(1, TokenConstants.tokenConstants.get("BLKEND"), 0, 3, 2, "%}");
        assertTrue(TokenUtilities.isCommentToken(token));

        token = new Token(1, TokenConstants.tokenConstants.get("PRAGMA"), 0, 3, 2, "%#");
        assertTrue(TokenUtilities.isCommentToken(token));

        token = new Token(1, TokenConstants.tokenConstants.get("ELSE"), 0, 3, 4, "else");
        assertFalse(TokenUtilities.isCommentToken(token));
    }

    public void testIsBlockCommentToken() {
        Token token = new Token(1, TokenConstants.tokenConstants.get("BLKSTART"), 0, 3, 2, "%{");
        assertTrue(TokenUtilities.isBlockCommentToken(token));

        token = new Token(1, TokenConstants.tokenConstants.get("BLKEND"), 0, 3, 2, "%}");
        assertTrue(TokenUtilities.isBlockCommentToken(token));

        token = new Token(1, TokenConstants.tokenConstants.get("IF"), 0, 3, 2, "if");
        assertFalse(TokenUtilities.isBlockCommentToken(token));
    }

    public void testIsSwitchToken() {
        // TODO: different from js (0,3,6 vs 0,3,2)
        Token token = new Token(1, TokenConstants.tokenConstants.get("SWITCH"), 0, 3, 6, "switch");
        assertTrue(TokenUtilities.isSwitchToken(token));

        token = new Token(1, TokenConstants.tokenConstants.get("CASE"), 0, 3, 4, "case");
        assertFalse(TokenUtilities.isSwitchToken(token));
    }

    public void testIsEndToken() {
        Token token = new Token(1, TokenConstants.tokenConstants.get("END"), 0, 3, 3, "end");
        assertTrue(TokenUtilities.isTokenEndOfBlock(token));

        token = new Token(1, TokenConstants.tokenConstants.get("ELSE"), 0, 3, 4, "else");
        assertFalse(TokenUtilities.isTokenEndOfBlock(token));
    }

    public void testIsFunctionToken() {
        Token token = new Token(1, TokenConstants.tokenConstants.get("FUNCTION"), 0, 3, 8, "function");
        assertTrue(TokenUtilities.isFunctionToken(token));

        token = new Token(1, TokenConstants.tokenConstants.get("ELSE"), 0, 3, 4, "else");
        assertFalse(TokenUtilities.isFunctionToken(token));
    }

}
