package com.chensuworks.nativeeditor.tokenizer;

import java.util.HashMap;
import java.util.LinkedList;

public class MatlabTokens {

    private Document document;
    private TokenState tokenState;
    private TokensData tokensData;

    public MatlabTokens(Document document) {
        this.document = document;

        tokenState = new TokenState();
        tokensData = new TokensData();
    }

    public void requestUpdate(int startLine) {
        if (startLine < 0) {
            startLine = 0;
        }
        _updateTokens(startLine);
    }

    public TokensData getTokensData() {
        return tokensData;
    }

    public TokenState getTokenState() {
        return tokenState;
    }

    public void tokenizeCodeOnLine(String code, int lineNumber) {
        tokenState.tokenizeCode(code, lineNumber, tokenState.getLexStateForLine(lineNumber));
    }

    public void retokenizeFromLine(String code, int startLine) {
        tokenState.retokenize(code, startLine);
    }

    public Token getFirstTokenOnLine(int lineNumber) {
        return tokenState.getFirstToken(lineNumber);
    }

    public Token getNextTokenOf(Token token) {
        return tokenState.getNextToken(token);
    }

    public Token getPreviousTokenOf(Token token, int lineNumber) {
        return tokenState.getPreviousToken(token, lineNumber);
    }

    public int getNumberOfTokensOnLine(int lineNumber) {
        return tokenState.getNumberOfTokensOnLine(lineNumber);
    }

    public Tokenizer.LexState getLexStateForLine(int lineNumber) {
        return tokenState.getLexStateForLine(lineNumber);
    }

    public boolean isLineContinuation(Tokenizer.LexState state) {
        return tokenState.isLineContinuation(state);
    }

    public void _updateTokens(int lineToStartTokensFrom) {
        retokenizeFromLine(document.getCodes(), lineToStartTokensFrom);

        HashMap<Integer, LinkedList<Token>> tokens = tokenState.get_tokenMap();
        LinkedList<Boolean> executableLines = tokenState.getExecutableLineData();

        tokensData.setTokens(tokens);
        tokensData.setExecutableLines(executableLines);
        tokensData.setLastEventStartLine(lineToStartTokensFrom);
    }

    public class TokensData {
        private HashMap<Integer, LinkedList<Token>> tokens;
        private LinkedList<Boolean> executableLines;
        private int lastEventStartLine;

        public TokensData() {
            tokens = null;
            executableLines = null;
            lastEventStartLine = 0;
        }

        public HashMap<Integer, LinkedList<Token>> getTokens() {
            return tokens;
        }

        public void setTokens(HashMap<Integer, LinkedList<Token>> tokens) {
            this.tokens = tokens;
        }

        public LinkedList<Boolean> getExecutableLines() {
            return executableLines;
        }

        public void setExecutableLines(LinkedList<Boolean> executableLines) {
            this.executableLines = executableLines;
        }

        public int getLastEventStartLine() {
            return lastEventStartLine;
        }

        public void setLastEventStartLine(int lastEventStartLine) {
            this.lastEventStartLine = lastEventStartLine;
        }
    }
}
