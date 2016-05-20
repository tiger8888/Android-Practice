package com.chensuworks.nativeeditor.plugins.autoindent;

import android.text.Editable;
import android.util.Log;

import com.chensuworks.nativeeditor.MatlabEditText;
import com.chensuworks.nativeeditor.tokenizer.Document;
import com.chensuworks.nativeeditor.tokenizer.MatlabTokens;
import com.chensuworks.nativeeditor.tokenizer.PositionInfo;
import com.chensuworks.nativeeditor.tokenizer.Token;
import com.chensuworks.nativeeditor.tokenizer.TokenConstants;
import com.chensuworks.nativeeditor.tokenizer.TokenUtilities;
import com.chensuworks.nativeeditor.tokenizer.Tokenizer;

public class Formatter {

    private static final int INDENT_AMOUNT = 4;

    private MatlabEditText editor;
    private Editable s;

    private Document matlabDocument;
    private MatlabTokens matlabTokens;

    public Formatter(MatlabEditText editor, Editable s, Document matlabDocument, MatlabTokens matlabTokens) {
        this.editor = editor;
        this.s = s;
        this.matlabDocument = matlabDocument;
        this.matlabTokens = matlabTokens;
    }

    public void setS(Editable s) {
        this.s = s;
    }

    public MatlabTokens getMatlabTokens() {
        return matlabTokens;
    }

    /**
     * Tokenize the given code belonging to the specified line
     *
     * @param code string containing the code
     * @param lineNumber integer specifying the line
     */
    public void handleDocumentChange(String code, int lineNumber) {
        matlabTokens.tokenizeCodeOnLine(code, lineNumber);
    }

    public void updateState(String code, int startLine) {
        matlabTokens.retokenizeFromLine(code, startLine);
    }

    /**
     * Finds the first non white space token in the given line. If a token is not found, 'null'
     * is returned.
     *
     * @param lineNumber integer specifying the line
     * @return an instance of <code>Token</code>
     */
    public Token findFirstNonWhitespaceTokenInLine(int lineNumber) {
        Token token = matlabTokens.getFirstTokenOnLine(lineNumber);

        while (token != null) {
            if (!TokenUtilities.isWhitespaceToken(token)) {
                return token;
            }
            token = matlabTokens.getNextTokenOf(token);
        }

        return token;
    }

    /**
     * CASE WHERE WE HAVE A LINE CONTINUATION
     * disp ...  (continuation line **A**)
     *     x ... (first line after continuation, shift in one line **B**)
     *     y     (not the first line after continuation, don't bother shifting **C**
     * @param lineNumber - the line number to process
     * @return extraIndent - the amount of indent due to a line continuation
     */
    public int processLineContinuations(int lineNumber) {
        int extraIndent = 0;
        Tokenizer.LexState previousState = null;

        boolean firstContin;

        // get the lexer state for the previous line as well
        if (lineNumber > 0) {
            previousState = matlabTokens.getLexStateForLine(lineNumber - 1);
        }

        firstContin = true;

        if (previousState != null && matlabTokens.isLineContinuation(previousState)) {
            firstContin = false;
        }

        if (firstContin) {
            extraIndent = INDENT_AMOUNT;
        }

        return extraIndent;
    }

    /**
     * CASE WHERE WE DO NOT HAVE A LINE CONTINUATION -- MUST CHECK IF WE NEED TO "RECOVER"
     * FROM LAST CONTINUATION LINE
     *
     * disp ... (continuation line **A**)
     *     x    (non-continuation line **B**)
     *          (empty non-continuation line -- must keep looking up for possible contin line **C**)
     * z
     *
     * @param lineNumber - the line number to currently process
     */
    public int processNonLineContinuation(int lineNumber) {
        Tokenizer.LexState previousState = null;
        int extraIndent = 0;
        boolean done = false;
        int curLine = lineNumber;

        // important to know if line is empty as we may have to search further to find
        // the last continuation line in this case
        //var emptyLine = !firstNWSToken; // if the first NWS token on this line is null, then the line is empty
        while (!done) {
            boolean emptyLine = _isLineEmpty(curLine + 1);

            if (lineNumber > 0) {
                previousState = matlabTokens.getLexStateForLine(curLine - 1);
            }

            if (previousState != null && matlabTokens.isLineContinuation(previousState)) {
                // last non empty line was a continuation line, so shift back out
                extraIndent = -INDENT_AMOUNT;
                done = true;

            } else if (!emptyLine || previousState == null) {
                done = true; // found a non-empty, non-continuation line, can stop looking
            }
            curLine = curLine - 1;
        }

        return extraIndent;
    }

    /**
     * Format the line of code specified by the line number
     *
     * @param lineNumber integer specifying the line
     * @return integer specifying the amount of indentation performed
     */
    public int format(int lineNumber) {
        Tokenizer.LexState state = matlabTokens.getLexStateForLine(lineNumber);
        int extraIndent = 0;

        // Get the first non-whitespace token on the line
        Token firstNWSToken = findFirstNonWhitespaceTokenInLine(lineNumber);

        if (state != null && matlabTokens.isLineContinuation(state)) {
            extraIndent = processLineContinuations(lineNumber);

        } else if (state != null && !matlabTokens.isLineContinuation(state)) {
            extraIndent = processNonLineContinuation(lineNumber);
        }

        int indentLevel = _findIndentLevel(firstNWSToken, lineNumber, extraIndent);
        Log.d("TAG", "indentLevel = " + indentLevel);

        //TODO: this logic needs refactoring.
        int lineIndentLevel = matlabDocument.getIndentLevel(lineNumber);
        if (lineIndentLevel != indentLevel) {
            matlabDocument.setLineIndent(lineNumber, indentLevel);

            // Need null check for unit test, so that unit tests don't need to worry about editor.
            if (editor != null) {
                // if existing indent < indentLevel, need to insert
                if (lineIndentLevel < indentLevel) {
                    int start = matlabDocument.getLineRange(lineNumber).getStart();
                    editor.getEditableText().insert(start, Document.generateWhitespaces(indentLevel - lineIndentLevel));

                } else {
                    // else if existing indent > indentLevel, need to replace
                    //editor.getEditableText().append(Document.generateWhitespaces(indentLevel), editor.getSelectionStart(), editor.getSelectionStart() + lineIndentLevel);

                    int start = matlabDocument.getLineRange(lineNumber).getStart();
                    editor.getEditableText().delete(start, start + lineIndentLevel - indentLevel);
                }


                // Avoid editor.setText(), operations on EditText's SpannableStringBuilder (getEditableText()) won't destroy object, thus improving performance.
                //editor.setContentChangedByAutoIndent(true); // Must set the flag before calling setText.
                //editor.setText(matlabDocument.getCodes());
            }


        }
        return indentLevel;
    }

    /**
     * Returns true if the line is empty
     * @param lineNumber - the 1-based line number
     */
    public boolean _isLineEmpty(int lineNumber) {
        return matlabDocument.getLineText(lineNumber) == "";
    }

    /**
     * Returns true if given token does not represents whitespace or block comments.
     * Comments are important only if they are the first non whitespace tokens on that line.
     *
     * @param token
     * @return {boolean}
     */
    public boolean _isNotWhitespaceOrComment(Token token) {

        if (TokenUtilities.isWhitespaceToken(token) && !TokenUtilities.isSectionTitleToken(token)) {
            // May not be necessary to check for section title.
            return false;

        } else if (TokenUtilities.isBlockCommentToken(token)) {
            return false;

        } else if (TokenUtilities.isCommentToken(token)) {
            Token firstNWSToken = findFirstNonWhitespaceTokenInLine(token.getPositionInfo().getLine());
            if (!firstNWSToken.equals(token)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Find the first non-whitespace and non-comment token starting from the given line.
     * The search can be in the forward or backward direction, based on the specified argument.
     * If nothing is found, 'null' is returned.
     *
     * @param startToken instance of <code>Token</code> on the given line from which to start
     *                   the search.
     * @param endingToken instance of <code>Token</code> at which to stop searching. Used only
     *                    when searching backward. Can be 'null' indicating search till the
     *                    end or start based on the direction.
     * @param searchBackward boolean indicating the direction of the search.
     * @param lineNumber integer specifying the start line
     * @return an instance of <code>Token</code>
     * @private
     */
    public Token _findNonWhitespaceAndComment(Token startToken, Token endingToken, boolean searchBackward, int lineNumber) {
        // Go to the previous token for the backward search
        if (searchBackward) {
            // Since starting and ending token are the same, this is an empty search
            if (startToken != null && startToken.equals(endingToken)) {
                return null;
            }

            startToken = matlabTokens.getPreviousTokenOf(startToken, lineNumber);

            if (endingToken != null) {
                endingToken = matlabTokens.getPreviousTokenOf(endingToken, lineNumber);
            }
        }

        while (startToken != null && !startToken.equals(endingToken)) {
            if (_isNotWhitespaceOrComment(startToken)) {
                return startToken;
            }

            startToken = searchBackward ? matlabTokens.getPreviousTokenOf(startToken, lineNumber) : matlabTokens.getNextTokenOf(startToken);
        }

        return null;
    }

    /**
     * Returns the indent level of the specified token on the specified line number.
     *
     * @param token
     * @param lineNumber
     * @param extraIndent - the extra indenting information due to constructs like line continuations
     * @return {number}
     * @private
     */
    public int _findIndentLevel(Token token, int lineNumber, int extraIndent) {
        int indent = -1;

        // First check the given token
        if (token != null) {
            Token matchingToken = _findStartTokenForMidBlock(token, lineNumber);
            if (matchingToken != null) {
                indent = _getIndentLevelForMidToken(matchingToken);
            } else {
                if (TokenUtilities.isEndToken(token)) {
                    indent = _getIndentLevelForEndToken(token, lineNumber);
                } else if (TokenUtilities.isFunctionToken(token)) {
                    if (_isTokenNestedFunction(token, lineNumber)) {
                        indent = _getIndentLevelForFunctionToken(token, lineNumber);
                    }
                }
            }
        }

        // If indent not found
        if (indent < 0) {
            // Go back and find important token
            Token importantToken = _findNonWhitespaceAndComment(token, null, true, lineNumber);

            if (importantToken != null) {
                Token firstNWSToken = findFirstNonWhitespaceTokenInLine(importantToken.getPositionInfo().getLine());
                // If END is the only thing on the line, use its indent level
                if (TokenUtilities.isTokenEndOfBlock(importantToken) && importantToken.equals(firstNWSToken)) {
                    indent = _getTokenIndent(importantToken);
                } else {
                    if (firstNWSToken != null) {
                        // Go back and search. If start of block found,
                        // increment indent level. If end of block found, decrement level.
                        indent = _searchBackAndCalculateIndent(importantToken, firstNWSToken);
                    }
                }
            }

            indent += extraIndent;
        }

        // No important token found
        if (indent < 0) {
            indent = 0;
        }

        return indent;
    }

    /**
     * For the given mid keyword token (e.g. ELSE, ELSEIF, CATCH, CASE, OTHERWISE), finds
     * the corresponding matching token that started the mid block statement
     * (e.g. IF, TRY, SWITCH).
     * If nothing is found, 'null' is returned.
     *
     * @param token an instance of <code>Token</code> starts the mid block
     * @param lineNumber integer specifying the line
     * @return an instance of <code>Token</code>
     * @private
     */
    public Token _findStartTokenForMidBlock(Token token, int lineNumber) {
        Token matchingToken = null;

        //further simplification possible.
        // TODO: do those if statements make sense?
        if (token.getType() == TokenConstants.tokenConstants.get("ELSE") || token.getType() == TokenConstants.tokenConstants.get("ELSEIF")) {
            matchingToken = _findCorrespondingStartToken(token, lineNumber);

        } else if (token.getType() == TokenConstants.tokenConstants.get("CATCH")) {
            matchingToken = _findCorrespondingStartToken(token, lineNumber);

        } else if (token.getType() == TokenConstants.tokenConstants.get("CASE") || token.getType() == TokenConstants.tokenConstants.get("OTHERWISE")) {
            matchingToken = _findCorrespondingStartToken(token, lineNumber);

        }

        return matchingToken;
    }

    /**
     * Find the corresponding starting token that matches the specified 'end' token. An 'end'
     * can be started by any token that satisfies <code>TokenUtilities.isTokenStartOfBlock</code>.
     * This search always progresses backwards. If a token is not found, 'null' is returned.
     * Takes into account nesting.
     *
     * @param token instance of <code>Token</code> to start the search
     * @param lineNumber integer specifying the line
     * @return an instance of <code>Token</code>
     * @private
     */
    public Token _findCorrespondingStartToken(Token token, int lineNumber) {
        int braceDepth = 0; // depth of the braces
        token = _findToken(token, lineNumber);

        while (token != null) {
            if (TokenUtilities.isTokenStartOfBlock(token)) {
                /* Start of block found that matches the correct block construct start.
                E.g.
                function foo
                    if true
                        disp hi
                    end
                end --> This end should match FUNCTION and not IF. This is where braceDepth
                       helps.
                */
                if (braceDepth == 0) {
                    return token;
                    //braceDepth < 0 ?? decrement first and then check?
                } else if ((braceDepth -= 1) < 0) {
                    return null; // no corresponding end
                }
            } else if (TokenUtilities.isTokenEndOfBlock(token)) {
                braceDepth += 1;
            }
            token = _findToken(token, lineNumber);
        }
        return token;
    }

    public Token _findToken(Token token, int lineNumber) {
        // TODO: from js - Look at desktop code for some form of caching.
        Token previousStatement = null;
        Token previousToken = matlabTokens.getPreviousTokenOf(token, lineNumber);

        // Relies on _findCorrespondingStartToken to find correct answer
        while (previousToken != null) {
            if (TokenUtilities.isTokenStartOfBlock(previousToken) ||
                    TokenUtilities.isTokenEndOfBlock(previousToken) ||
                    TokenUtilities.isTokenStartOfMidBlock(previousToken)) {
                return previousToken;
            }

            previousToken = matlabTokens.getPreviousTokenOf(previousToken, lineNumber);
        }

        return null;
    }

    public boolean _isTokenNestedFunction(Token token, int lineNumber) {
        return TokenUtilities.isFunctionToken(token) && _findMatchingFunctionToken(token, lineNumber) != null;
    }

    public int _searchBackAndCalculateIndent(Token startToken, Token endToken) {
        int level = 0;
        Token iterator = startToken;
        int indent;

        while (iterator != null) {
            startToken = iterator;
            if (TokenUtilities.isTokenStartOfBlockIndent(startToken)) {
                // add if STARTS with indent block OR if statement block start, always add
                if (startToken.equals(endToken) || TokenUtilities.isTokenStartOfBlock(startToken)) {
                    level += 1;
                }
            } else if (TokenUtilities.isTokenEndOfBlock(startToken)) {
                // only subtract if the end marker is not the first thing on the line
                // if it is, indent level has already been decreased once
                if (!startToken.equals(endToken)) {
                    level -= 1;
                }
            }

            // TODO: js didn't implement the fourth argument lineNumber. Is it correct?
            iterator = _findNonWhitespaceAndComment(startToken, endToken, true, startToken.getPositionInfo().getLine());
        }

        indent = _getTokenIndent(startToken) + level * INDENT_AMOUNT;
        return indent;
    }

    public Token _findMatchingFunctionToken(Token token, int lineNumber) {
        Token matchingToken = null;

        if (token.getType() == TokenConstants.tokenConstants.get("FUNCTION") || token.getType() == TokenConstants.tokenConstants.get("NESTED")) {
            matchingToken = _findCorrespondingStartToken(token, lineNumber);
        }

        return matchingToken;
    }

    public int _getIndentLevelForMidToken(Token token) {
        int indent = _getTokenIndent(token);
        if (TokenUtilities.isSwitchToken(token)) {
            indent += INDENT_AMOUNT;
        }
        return indent;
    }

    public int _getIndentLevelForEndToken(Token token, int lineNumber) {
        int indent = -1;
        Token startToken = _findCorrespondingStartToken(token, lineNumber);
        if (startToken != null) {
            indent = _getTokenIndent(startToken);
        }

        return indent;
    }

    public int _getIndentLevelForFunctionToken(Token token, int lineNumber) {
        int indent = -1;
        Token nested = _findMatchingFunctionToken(token, lineNumber);
        if (nested != null) {
            indent = _getTokenIndent(nested) + INDENT_AMOUNT;
        }

        return indent;
    }

    public int _getTokenIndent(Token token) {
        PositionInfo positionInfo = token.getPositionInfo();
        // TODO: return this._document.getIndentLevel(positionInfo.line);
        return matlabDocument.getIndentLevel(positionInfo.getLine());
    }

}
