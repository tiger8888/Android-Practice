package com.chensuworks.nativeeditor.tokenizer;

import java.util.HashMap;
import java.util.LinkedList;

public class TokenState {

    private static final int SHIFT_FOR_LENGTH = 8;
    private static final int AND_FOR_TYPE = 0x7F;

    private HashMap<Integer, LinkedList<Token>> _tokenMap = new HashMap<Integer, LinkedList<Token>>();
    private Tokenizer _tokenizer;
    private LinkedList<Boolean> _isLineExecutable = new LinkedList<>();
    private Tokenizer.LexState _lexState;
    private LinkedList<Tokenizer.LexState> _savedLexStates = new LinkedList<>();
    //private HashMap<Integer, Tokenizer.LexState> _savedLexStates = new HashMap<Integer, Tokenizer.LexState>();
    //private Tokenizer.LexState[] _savedLexStates = new Tokenizer.LexState[];

    public TokenState() {
        _tokenizer = new Tokenizer();
        //_tokenizer.initialize();
        _lexState = _tokenizer.make_lex_state();
        _savedLexStates.add(_lexState);
        //_savedLexStates[0] = _lexState;
    }

    /**
     * Tokenize the code provided; into instances of <code>Token</code>
     *
     * @param code String representing the code to tokenize
     * @param lineNumber integer specifying the line the code is on.
     * @param lexState - the current state of the lexer
     */
    public void tokenizeCode(String code, int lineNumber, Tokenizer.LexState lexState) {
        int maxNumberOfTokens = code.length() + 3;
        LinkedList<Integer> tokenArray = new LinkedList<>();

        Tokenizer.LexResults lexResults = _tokenizer.lex_line(tokenArray, maxNumberOfTokens, lexState, code, code.length());

        if (lineNumber >= _isLineExecutable.size()) {
            _isLineExecutable.add(lineNumber, lexResults.isExecutable());
        } else {
            _isLineExecutable.set(lineNumber, lexResults.isExecutable());
        }

        if (lineNumber + 1 >= _savedLexStates.size()) {
            _savedLexStates.add(_tokenizer.make_lex_state());
        } else {
            _savedLexStates.set(lineNumber + 1, _tokenizer.make_lex_state());
        }
        //_savedLexStates.put(lineNumber + 1, _tokenizer.make_lex_state());
        //_savedLexStates[lineNumber + 1] = _tokenizer.make_lex_state();

        _tokenizer.copy_lex_state(_savedLexStates.get(lineNumber + 1), lexResults.getNewState());
        //_tokenizer.copy_lex_state(_savedLexStates[lineNumber + 1], lexResults.getNewState());

        _parseTokens(tokenArray, code, lineNumber);
    }

    public Tokenizer.LexState getLexStateForLine(int lineNumber) {
        return _savedLexStates.get(lineNumber);
        //return _savedLexStates[lineNumber];
    }

    public LinkedList<Boolean> getExecutableLineData() {
        return _isLineExecutable;
    }

    public boolean isLineContinuation(Tokenizer.LexState state) {
        if (_tokenizer.is_contin(state) == 0) {
            return false;
        }
        return true;
    }

    public boolean areStatesEqual(Tokenizer.LexState state1, Tokenizer.LexState state2) {
        return _tokenizer.are_states_equal(state1, state2);
    }

    public int getNumberOfSavedStates() {
        return _savedLexStates.size();
        //return _savedLexStates.length;
    }

    // some optimization possible here.
    // do not tokenize everything after startline
    // try tokenizing just start and end line of a selection
    // adjust data structure of tokens based on number of lines deleted
    public void retokenize(String code, int startLine) {
        int index;
        int totalLinesInMap = _tokenMap.size();

        if (startLine >= 0) {
            String[] linesOfCode = code.split("\n", -1);
            int numberOfLinesInDocument = linesOfCode.length;

            // Remove the tokens for the deleted lines from the token map and also delete the saved lex state
            // for the deleted lines.
            if (totalLinesInMap >= numberOfLinesInDocument) {
                LinkedList<Integer> lines = new LinkedList<>();
                for (Integer lineNumber : _tokenMap.keySet()) {
                    int intLineNumber = lineNumber;
                    if (intLineNumber >= numberOfLinesInDocument) {
                        lines.add(intLineNumber);
                    }
                }
                for (int i = lines.size() - 1; i >= 0; i--) {
                    _tokenMap.remove(lines.get(i));
                    //_savedLexStates.remove((int) lines.get(i));
                    if (_savedLexStates.contains((int) lines.get(i))) {
                        _savedLexStates.remove((int) lines.get(i));
                    }
                }
            }

            //Back startLine up if it is skipping entries past the end of the map
            //This will typically happen if rich text is added before a new code line
            if (startLine > totalLinesInMap) {
                startLine = totalLinesInMap;
            }

            if (startLine <= numberOfLinesInDocument) {
                for (index = startLine; index < numberOfLinesInDocument; index += 1) {
                    tokenizeCode(linesOfCode[index], index, getLexStateForLine(index));
                }
            }

        }
    }

    public void _parseTokens(LinkedList<Integer> tokenArray, String code, int lineNumber) {
        int index;
        LinkedList<Token> tokens = new LinkedList<>();
        TokenInfo tokenInfo;
        String tokenValue;
        int numberOfTokens = tokenArray.size();
        int offset = 0;
        Token newToken;

        for (index = 0; index < numberOfTokens; index++) {
            tokenInfo = _extractTokenInfo(tokenArray.get(index));
            if (offset < code.length()) {
                tokenValue = code.substring(offset, offset + tokenInfo.getLength());
            } else {
                tokenValue = "";
            }
            newToken = new Token(index, tokenInfo.getType(), lineNumber, offset, tokenInfo.getLength(), tokenValue);
            tokens.add(newToken);
            offset += tokenInfo.getLength();
        }

        _tokenMap.put(lineNumber, tokens);
    }

    public TokenInfo _extractTokenInfo(int token) {
        TokenInfo tokenInfo = new TokenInfo(token & AND_FOR_TYPE, token >> SHIFT_FOR_LENGTH);
        return tokenInfo;
    }

    public class TokenInfo {
        private int type;
        private int length;

        public TokenInfo(int type, int length) {
            this.type = type;
            this.length = length;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }

    public HashMap<Integer, LinkedList<Token>> get_tokenMap() {
        return _tokenMap;
    }

    public int getNumberOfTokensOnLine(int lineNumber) {
        LinkedList<Token> tokensArrayForLine = _tokenMap.get(lineNumber);
        if (tokensArrayForLine == null) {
            return -1;
        }

        return tokensArrayForLine.size();
    }

    public Token getLastToken(int lineNumber) {
        Token lastToken = null;
        LinkedList<Token> tokensOnLine = _tokenMap.get(lineNumber);
        if (tokensOnLine != null && tokensOnLine.size() > 0) {
            int lastIndex = tokensOnLine.size() - 1;
            // get the last non end-of-line token in this line
            for (int i = 0; i <= lastIndex; i++) {
                Token token = tokensOnLine.get(i);
                if (token.getValue() != "") {
                    lastToken = token;
                }
            }
        }

        return lastToken;
    }

    public Token getFirstToken(int lineNumber) {
        Token firstToken = null;
        LinkedList<Token> tokensOnLine = _tokenMap.get(lineNumber);
        if (tokensOnLine != null && tokensOnLine.size() > 0) {
            firstToken = tokensOnLine.get(0);
            // if it is an end of line, then return null
            if (firstToken.getValue() == "") {
                firstToken = null;
            }
        }

        return firstToken;
    }

    public Token getNextToken(Token token) {
        Token nextToken = null;
        PositionInfo positionInfo = token.getPositionInfo();
        LinkedList<Token> tokensOnLine = _tokenMap.get(positionInfo.getLine());
        int tokenIndex = token.getIndex();

        // if there is a next token which is not an end of line, return it, else return null
        if (tokensOnLine != null && tokensOnLine.get(tokenIndex + 1) != null
                && tokensOnLine.get(tokenIndex + 1).getValue() != "") {
            nextToken = tokensOnLine.get(tokenIndex + 1);
        }

        return nextToken;
    }

    public Token getPreviousToken(Token token, int lineNumber) {
        int previousLine;
        if (token == null) {
            previousLine = lineNumber - 1;
            return _searchBackForToken(previousLine);
        }

        Token previousToken = null;
        PositionInfo positionInfo = token.getPositionInfo();
        LinkedList<Token> tokensOnLine = _tokenMap.get(positionInfo.getLine());
        int tokenIndex = token.getIndex();

        // Get previous token on same line
        if (tokenIndex != 0 && tokensOnLine != null && tokensOnLine.get(tokenIndex - 1) != null) {
            previousToken = tokensOnLine.get(tokenIndex - 1);
            return previousToken;
        }
        previousLine = positionInfo.getLine() - 1;
        return _searchBackForToken(previousLine);
    }

    public Token _searchBackForToken(int lineNumber) {
        Token lastToken = getLastToken(lineNumber);
        while (lastToken == null && lineNumber >= 0) {
            lastToken = getLastToken(lineNumber -= 1);
        }
        return lastToken;
    }

}
