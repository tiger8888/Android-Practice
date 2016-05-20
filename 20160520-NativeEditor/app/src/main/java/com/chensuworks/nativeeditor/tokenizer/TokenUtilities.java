package com.chensuworks.nativeeditor.tokenizer;

public class TokenUtilities {

    /**
     * Returns true if token corresponds to a token that starts a block statement
     * (a block that is indented).
     *
     * @param token an instance of <code>Token</code>
     * @return {boolean} true if token is start of block statement
     */
    public static boolean isTokenStartOfBlock(Token token) {
        int tokenType = token.getType();

        return tokenType == TokenConstants.tokenConstants.get("FOR") ||
                tokenType == TokenConstants.tokenConstants.get("PARFOR") ||
                tokenType == TokenConstants.tokenConstants.get("IF") ||
                tokenType == TokenConstants.tokenConstants.get("WHILE") ||
                tokenType == TokenConstants.tokenConstants.get("TRY") ||
                tokenType == TokenConstants.tokenConstants.get("SWITCH") ||
                tokenType == TokenConstants.tokenConstants.get("FUNCTION") ||
                tokenType == TokenConstants.tokenConstants.get("NESTED") ||
                tokenType == TokenConstants.tokenConstants.get("CLASSDEF") ||
                tokenType == TokenConstants.tokenConstants.get("PROPERTIES") ||
                tokenType == TokenConstants.tokenConstants.get("ENUMERATION") ||
                tokenType == TokenConstants.tokenConstants.get("SPMD") ||
                tokenType == TokenConstants.tokenConstants.get("EVENTS") ||
                tokenType == TokenConstants.tokenConstants.get("METHODS");
    }

    /**
     * Returns true if token corresponds to a token that ends a block statement
     * (a block that is indented).
     *
     * @param token an instance of <code>Token</code>
     * @return {boolean} true if token is end of block statement
     */
    public static boolean isTokenEndOfBlock(Token token) {
        return token.getType() == TokenConstants.tokenConstants.get("END");
    }

    /**
     * Returns true if token corresponds to a token that begins a middle block. For example,
     * statements like else, elseif, catch, otherwise, or case.
     *
     * @param token an instance of <code>Token</code>
     * @return {boolean} true if token is start of middle block
     */
    public static boolean isTokenStartOfMidBlock(Token token) {
        int tokenType = token.getType();
        return tokenType == TokenConstants.tokenConstants.get("ELSE") ||
                tokenType == TokenConstants.tokenConstants.get("ELSEIF") ||
                tokenType == TokenConstants.tokenConstants.get("CATCH") ||
                tokenType == TokenConstants.tokenConstants.get("OTHERWISE") ||
                tokenType == TokenConstants.tokenConstants.get("CASE");
    }

    /**
     * Returns true if token corresponds to a section title i.e. %%.
     * There may be whitespace before and after the section title.
     *
     * @param token an instance of <code>Token</code>
     * @return {boolean}
     */
    public static boolean isSectionTitleToken(Token token) {
        return token.getType() == TokenConstants.tokenConstants.get("SECTION_TITLE");
    }

    /**
     * Returns true if token corresponds to a whitespace
     *
     * @param token an instance of <code>Token</code>
     * @return {boolean}
     */
    public static boolean isWhitespaceToken(Token token) {
        return token.getType() == TokenConstants.tokenConstants.get("WHITE");
    }

    /**
     * Returns true if token corresponds to a token that causes an indent.
     * Tokens that start a block statement and mid block statement are included
     * (ELSE, ELSEIF etc.).
     *
     * @param token an instance of <code>Token</code>
     * @return {boolean}
     */
    public static boolean isTokenStartOfBlockIndent(Token token) {
        return (isTokenStartOfBlock(token) || isTokenStartOfMidBlock(token));
    }

    /**
     * Returns true if token corresponds to a comment.
     *
     * @param token an instance of <code>Token</code>
     * @return {boolean}
     */
    public static boolean isCommentToken(Token token) {
        int tokenType = token.getType();
        return tokenType == TokenConstants.tokenConstants.get("COMMENT") ||
                tokenType == TokenConstants.tokenConstants.get("SECTION_TITLE") ||
                tokenType == TokenConstants.tokenConstants.get("BLKCOM") ||
                tokenType == TokenConstants.tokenConstants.get("BLKSTART") ||
                tokenType == TokenConstants.tokenConstants.get("BLKEND") ||
                tokenType == TokenConstants.tokenConstants.get("PRAGMA");

    }

    /**
     * Returns true if token is inside of a block comment or is the start and end of a block
     * comment.
     *
     * @param token an instance of <code>Token</code>
     * @return {boolean}
     */
    public static boolean isBlockCommentToken(Token token) {
        int tokenType = token.getType();
        return tokenType == TokenConstants.tokenConstants.get("BLKCOM") ||
                tokenType == TokenConstants.tokenConstants.get("BLKSTART") ||
                tokenType == TokenConstants.tokenConstants.get("BLKEND");
    }

    /**
     * Returns true if token corresponds to a switch statement.
     *
     * @param token an instance of <code>Token</code>
     * @return {boolean}
     */
    public static boolean isSwitchToken(Token token) {
        return token.getType() == TokenConstants.tokenConstants.get("SWITCH");
    }

    /**
     * Returns true if the token corresponds to an end of line token
     * @param token an instance of <code>token</code>
     * @return {boolean}
     */
    public static boolean isEndOfLineToken(Token token) {
        return token.getType() == TokenConstants.tokenConstants.get("EOL") ||
                token.getType() == TokenConstants.tokenConstants.get("SEOL") ||
                token.getType() == TokenConstants.tokenConstants.get("CEOL") ||
                token.getType() == TokenConstants.tokenConstants.get("IEOL");
    }

    /**
     * Returns true if token corresponds to an end statement
     *
     * @param token an instance of <code>Token</code>
     * @return {boolean}
     */
    public static boolean isEndToken(Token token) {
        return token.getType() == TokenConstants.tokenConstants.get("END");
    }

    /**
     * Returns true if token corresponds to a function statement.
     *
     * @param token an instance of <code>Token</code>
     * @return {boolean}
     */
    public static boolean isFunctionToken(Token token) {
        return token.getType() == TokenConstants.tokenConstants.get("FUNCTION");
    }

}
