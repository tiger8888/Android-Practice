package com.chensuworks.nativeeditor.plugins.syntaxhighlighter;

import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.chensuworks.nativeeditor.tokenizer.ColorMapDefaults;
import com.chensuworks.nativeeditor.tokenizer.Document;
import com.chensuworks.nativeeditor.tokenizer.MatlabTokens;
import com.chensuworks.nativeeditor.tokenizer.Token;
import com.chensuworks.nativeeditor.tokenizer.TokenConstants;
import com.chensuworks.nativeeditor.utils.SpanUtil;

import java.util.HashMap;
import java.util.LinkedList;

public class SyntaxHighlighter {

    private Editable s;

    private Document matlabDocument;
    private MatlabTokens matlabTokens;

    private MatlabTokens.TokensData tokensData;
    private HashMap<Integer, LinkedList<NewToken>> tokenMap;

    public SyntaxHighlighter(Editable s, Document matlabDocument, MatlabTokens matlabTokens) {
        this.s = s;
        this.matlabDocument = matlabDocument;
        this.matlabTokens = matlabTokens;

        this.tokensData = matlabTokens.getTokensData();
    }

    public void setS(Editable s) {
        this.s = s;
    }

    public void _updateTokens(MatlabTokens.TokensData tokensData) {
        this.tokensData = tokensData;

        redrawSyntaxHighlighting();
    }

    public void redrawSyntaxHighlighting() {

        int lineCount = s.toString().split("\n", -1).length;

        int lineNumberOfLastTokenEvent = tokensData.getLastEventStartLine();
        int totalLinesInDocument = lineCount;
        //Log.d("TAG", "check tokensData");

        HashMap<Integer, LinkedList<NewToken>> processedTokens = processTokens(tokensData.getTokens(), 0, lineCount);
        //Log.d("TAG", "check processedTokens");

        for (int line = lineNumberOfLastTokenEvent; line < totalLinesInDocument; line++) {
            LinkedList<NewToken> lineTokens = processedTokens.get(line);
            SpanUtil.removeForegroundColor(s, matlabDocument.getLineRange(line).getStart(), matlabDocument.getLineRange(line).getEnd());

            if (lineTokens != null && lineTokens.size() > 0) {
                for (int i = 0; i < lineTokens.size(); i++) {
                    NewToken token = lineTokens.get(i);
                    int startIndex = matlabDocument.getIndex(token.getRange().getStartRow(), token.getRange().getStartColumn());
                    int endIndex = matlabDocument.getIndex(token.getRange().getEndRow(), token.getRange().getEndColumn());
                    SpanUtil.addForegroundColor(s, startIndex, endIndex, token.getColor());
                }
            }
        }


    }

    public HashMap<Integer, LinkedList<NewToken>> processTokens(HashMap<Integer, LinkedList<Token>> tokensMatrix, int lineNumberToProcessFrom, int totalLinesInDocument) {

        tokenMap = new HashMap<Integer, LinkedList<NewToken>>();
        //tokenMap.put(0, new LinkedList<NewToken>());

        for (int lineNumber = lineNumberToProcessFrom; lineNumber < totalLinesInDocument; lineNumber++) {
            LinkedList<Token> tokensArray = tokensMatrix.get(lineNumber);

            Group group = new Group();

            for (int tokenIndex = 0; tokenIndex < tokensArray.size(); tokenIndex++) {
                Token token = tokensArray.get(tokenIndex);

                Integer color;
                Integer category = TokenConstants.getCategoryOfToken(token.getType());
                if (category == null) {
                    color = ColorMapDefaults.colorMapDefaults.get(339);
                } else {
                    color = ColorMapDefaults.colorMapDefaults.get(category);

                    if (color == null) {
                        color = ColorMapDefaults.colorMapDefaults.get(339);
                    }
                }


                TokenRange range = new TokenRange(lineNumber, token.getColumn(), lineNumber, token.getColumn() + token.getLength());

                if (token.getLength() > 0 && color != Color.rgb(0, 0, 0)) {
                    if (color == group.getColor()) {
                        group.setEndRow(range.getEndRow());
                        group.setEndColumn(range.getEndColumn());
                        group.setValueBuffer(group.getValueBuffer() + token.getValue());

                    } else {
                        tokenMap = _createNewTokenAndAppendToMap(group, tokenMap);

                        group.setStartRow(range.getStartRow());
                        group.setStartColumn(range.getStartColumn());
                        group.setEndRow(range.getEndRow());
                        group.setEndColumn(range.getEndColumn());
                        group.setColor(color);
                        group.setValueBuffer(token.getValue());
                    }

                } else if (token.getValue().equals(" ")) {
                    group.setEndRow(range.getEndRow());
                    group.setEndColumn(range.getEndColumn());
                    group.setValueBuffer(group.getValueBuffer() + token.getValue());

                } else {
                    tokenMap = _createNewTokenAndAppendToMap(group, tokenMap);

                    group.reset();
                }

            }

            // If the buffer has not been flushed for this line, do it now
            tokenMap = _createNewTokenAndAppendToMap(group, tokenMap);
        }

        return tokenMap;
    }

    public void _appendTokenToMap(NewToken newToken, HashMap<Integer, LinkedList<NewToken>> tokenMap, int lineCounter) {
        if (newToken.getValue() != "" && lineCounter >= 0) {
            if (!tokenMap.containsKey(lineCounter)) {
                tokenMap.put(lineCounter, new LinkedList<NewToken>());
            }
            tokenMap.get(lineCounter).add(newToken);
        }
    }

    public NewToken _createNewToken(String value, Integer color, int startRow, int startColumn, int endRow, int endColumn) {
        return new NewToken(value, color, new TokenRange(startRow, startColumn, endRow, endColumn));
    }

    public HashMap<Integer, LinkedList<NewToken>> _createNewTokenAndAppendToMap(Group group, HashMap<Integer, LinkedList<NewToken>> tokenMap) {
        if (group.getValueBuffer() != "") {
            NewToken newToken = _createNewToken(group.getValueBuffer(), group.getColor(), group.getStartRow(), group.getStartColumn(), group.getEndRow(), group.getEndColumn());
            _appendTokenToMap(newToken, tokenMap, group.getStartRow());
        }
        return tokenMap;
    }


    public class NewToken {
        private String value;
        private Integer color;
        private TokenRange range;

        public NewToken() {
            this.value = "";
            this.color = null;
            this.range = new TokenRange();
        }

        public NewToken(String value, Integer color, TokenRange range) {
            this.value = value;
            this.color = color;
            this.range = range;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Integer getColor() {
            return color;
        }

        public void setColor(Integer color) {
            this.color = color;
        }

        public TokenRange getRange() {
            return range;
        }

        public void setRange(TokenRange range) {
            this.range = range;
        }
    }

    public class TokenRange {
        private int startRow;
        private int startColumn;
        private int endRow;
        private int endColumn;

        public TokenRange() {
            this.startRow = 0;
            this.startColumn = 0;
            this.endRow = 0;
            this.endColumn = 0;
        }

        public TokenRange(int startRow, int startColumn, int endRow, int endColumn) {
            this.startRow = startRow;
            this.startColumn = startColumn;
            this.endRow = endRow;
            this.endColumn = endColumn;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getStartColumn() {
            return startColumn;
        }

        public void setStartColumn(int startColumn) {
            this.startColumn = startColumn;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getEndColumn() {
            return endColumn;
        }

        public void setEndColumn(int endColumn) {
            this.endColumn = endColumn;
        }
    }

    public class Group {
        private String valueBuffer;
        private Integer color;
        private int startRow;
        private int startColumn;
        private int endRow;
        private int endColumn;

        public Group() {
            valueBuffer = "";
            color = Color.rgb(0, 0, 0);
            startRow = -1;
            startColumn = -1;
            endRow = -1;
            endColumn = -1;
        }

        public void reset() {
            valueBuffer = "";
            color = Color.rgb(0, 0, 0);
            startRow = -1;
            startColumn = -1;
            endRow = -1;
            endColumn = -1;
        }

        public String getValueBuffer() {
            return valueBuffer;
        }

        public void setValueBuffer(String valueBuffer) {
            this.valueBuffer = valueBuffer;
        }

        public Integer getColor() {
            return color;
        }

        public void setColor(Integer color) {
            this.color = color;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getStartColumn() {
            return startColumn;
        }

        public void setStartColumn(int startColumn) {
            this.startColumn = startColumn;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getEndColumn() {
            return endColumn;
        }

        public void setEndColumn(int endColumn) {
            this.endColumn = endColumn;
        }
    }



}
