package com.chensuworks.nativeeditor.plugins.autoindent;

import android.content.Context;
import android.test.ActivityTestCase;
import android.test.mock.MockContext;
import android.test.mock.MockResources;
import android.util.AttributeSet;

import com.chensuworks.nativeeditor.plugins.autoindent.Formatter;
import com.chensuworks.nativeeditor.tokenizer.Document;
import com.chensuworks.nativeeditor.tokenizer.MatlabTokens;
import com.chensuworks.nativeeditor.tokenizer.Token;
import com.chensuworks.nativeeditor.tokenizer.TokenConstants;

public class FormatterUnitTest extends ActivityTestCase {

    private Document document = new Document("");
    private MatlabTokens tokenizerService = new MatlabTokens(document);

    public void testHandleDocumentChange() {
        Formatter formatter = new Formatter(null, null, document, tokenizerService);

        formatter.handleDocumentChange("function foo", 0);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(0), 4);

        formatter.handleDocumentChange("", 1);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(1), 1);
    }

    public void testUpdateState() {
        Formatter formatter = new Formatter(null, null, document, tokenizerService);

        formatter.handleDocumentChange("function foo", 0);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(0), 4);

        formatter.handleDocumentChange("if true", 1);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(1), 4);

        // delete line 1
        formatter.updateState("function foo", 1);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(0), 4);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(1), -1);
    }

    public void testFindFirstNonWhitespaceTokenInLine() {
        Formatter formatter = new Formatter(null, null, document, tokenizerService);
        Token firstToken;
        Token token;

        formatter.handleDocumentChange("function foo", 0);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(0), 4);
        firstToken = formatter.findFirstNonWhitespaceTokenInLine(0);
        token = new Token(0, TokenConstants.tokenConstants.get("FUNCTION"), 0, 0, 8, "function");
        assertTrue(firstToken.equals(token));

        formatter.handleDocumentChange("", 1);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(1), 1);
        firstToken = formatter.findFirstNonWhitespaceTokenInLine(1);
        assertTrue(firstToken == null);

        formatter.handleDocumentChange("    if true", 2);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(2), 5);
        firstToken = formatter.findFirstNonWhitespaceTokenInLine(2);
        token = new Token(1, TokenConstants.tokenConstants.get("IF"), 2, 4, 2, "if");
        assertTrue(firstToken.equals(token));
    }

    public void testFormat() {
        Formatter formatter = new Formatter(null, null, document, tokenizerService);
        int indentValue;

        /**
         * function foo
         *     disp('hi')
         * end
         */
        document.setCodes("function foo");
        formatter.handleDocumentChange("function foo", 0);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(0), 4);
        indentValue = formatter.format(0);
        assertEquals(indentValue, 0);
        assertEquals(document.getIndentLevel(0), 0);

        document.appendNewLine("disp('hi')");
        formatter.handleDocumentChange("disp('hi')", 1);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(1), 5);
        indentValue = formatter.format(1);
        assertEquals(indentValue, 4);
        assertEquals(document.getIndentLevel(1), 4);

        document.appendNewLine("end");
        formatter.handleDocumentChange("end", 2);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(2), 2);
        indentValue = formatter.format(2);
        assertEquals(indentValue, 0);
        assertEquals(document.getIndentLevel(2), 0);

        /**
         * switch a
         *     case 1,
         *         a=1
         *     otherwise,
         *         a=2
         * end
         */
        document.setCodes("switch a");
        formatter.handleDocumentChange("switch a", 0);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(0), 4);
        indentValue = formatter.format(0);
        assertEquals(indentValue, 0);
        assertEquals(document.getIndentLevel(0), 0);

        document.appendNewLine("case 1,");
        formatter.handleDocumentChange("case 1,", 1);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(1), 5);
        indentValue = formatter.format(1);
        assertEquals(indentValue, 4);
        assertEquals(document.getIndentLevel(1), 4);

        document.appendNewLine("a=1");
        formatter.handleDocumentChange("a=1", 2);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(2), 3);
        indentValue = formatter.format(2);
        assertEquals(indentValue, 8);
        assertEquals(document.getIndentLevel(2), 8);

        document.appendNewLine("otherwise,");
        formatter.handleDocumentChange("otherwise,", 3);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(3), 3);
        indentValue = formatter.format(3);
        assertEquals(indentValue, 4);
        assertEquals(document.getIndentLevel(3), 4);

        document.appendNewLine("a=2");
        formatter.handleDocumentChange("a=2", 4);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(4), 3);
        indentValue = formatter.format(4);
        assertEquals(indentValue, 8);
        assertEquals(document.getIndentLevel(4), 8);

        document.appendNewLine("end");
        formatter.handleDocumentChange("end", 5);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(5), 2);
        indentValue = formatter.format(5);
        assertEquals(indentValue, 0);
        assertEquals(document.getIndentLevel(5), 0);

        /**
         * function foo
         *      if true
         *          disp('hi')
         *          for i=1:10
         *              a=i*i;
         *          end
         *      end
         *      switch a
         *          case 1,
         *              a=1
         *          otherwise,
         *              a=2
         *      end
         *      function bar
         *          disp('hi')
         *      end
         * end
         * function foobar
         * end
         */
        document.setCodes("function foo");
        formatter.handleDocumentChange("function foo", 0);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(0), 4);
        indentValue = formatter.format(0);
        assertEquals(indentValue, 0);
        assertEquals(document.getIndentLevel(0), 0);

        document.appendNewLine("if true");
        formatter.handleDocumentChange("if true", 1);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(1), 4);
        indentValue = formatter.format(1);
        assertEquals(indentValue, 4);
        assertEquals(document.getIndentLevel(1), 4);

        document.appendNewLine("disp('hi')");
        formatter.handleDocumentChange("disp('hi')", 2);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(2), 5);
        indentValue = formatter.format(2);
        assertEquals(indentValue, 8);
        assertEquals(document.getIndentLevel(2), 8);

        document.appendNewLine("for i=1:10");
        formatter.handleDocumentChange("for i=1:10", 3);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(3), 8);
        indentValue = formatter.format(3);
        assertEquals(indentValue, 8);
        assertEquals(document.getIndentLevel(3), 8);

        document.appendNewLine("a=i*i;");
        formatter.handleDocumentChange("a=i*i;", 4);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(4), 6);
        indentValue = formatter.format(4);
        assertEquals(indentValue, 12);
        assertEquals(document.getIndentLevel(4), 12);

        document.appendNewLine("end");
        formatter.handleDocumentChange("end", 5);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(5), 2);
        indentValue = formatter.format(5);
        assertEquals(indentValue, 8);
        assertEquals(document.getIndentLevel(5), 8);

        document.appendNewLine("end");
        formatter.handleDocumentChange("end", 6);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(6), 2);
        indentValue = formatter.format(6);
        assertEquals(indentValue, 4);
        assertEquals(document.getIndentLevel(6), 4);

        document.appendNewLine("switch a");
        formatter.handleDocumentChange("switch a", 7);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(7), 4);
        indentValue = formatter.format(7);
        assertEquals(indentValue, 4);
        assertEquals(document.getIndentLevel(7), 4);

        document.appendNewLine("case 1,");
        formatter.handleDocumentChange("case 1,", 8);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(8), 5);
        indentValue = formatter.format(8);
        assertEquals(indentValue, 8);
        assertEquals(document.getIndentLevel(8), 8);

        document.appendNewLine("a=1");
        formatter.handleDocumentChange("a=1", 9);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(9), 3);
        indentValue = formatter.format(9);
        assertEquals(indentValue, 12);
        assertEquals(document.getIndentLevel(9), 12);

        document.appendNewLine("otherwise,");
        formatter.handleDocumentChange("otherwise,", 10);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(10), 3);
        indentValue = formatter.format(10);
        assertEquals(indentValue, 8);
        assertEquals(document.getIndentLevel(10), 8);

        document.appendNewLine("a=2");
        formatter.handleDocumentChange("a=2", 11);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(11), 3);
        indentValue = formatter.format(11);
        assertEquals(indentValue, 12);
        assertEquals(document.getIndentLevel(11), 12);

        document.appendNewLine("end");
        formatter.handleDocumentChange("end", 12);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(12), 2);
        indentValue = formatter.format(12);
        assertEquals(indentValue, 4);
        assertEquals(document.getIndentLevel(12), 4);

        document.appendNewLine("function bar");
        formatter.handleDocumentChange("function bar", 13);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(13), 4);
        indentValue = formatter.format(13);
        assertEquals(indentValue, 4);
        assertEquals(document.getIndentLevel(13), 4);

        document.appendNewLine("disp('hi')");
        formatter.handleDocumentChange("disp('hi')", 14);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(14), 5);
        indentValue = formatter.format(14);
        assertEquals(indentValue, 8);
        assertEquals(document.getIndentLevel(14), 8);

        document.appendNewLine("end");
        formatter.handleDocumentChange("end", 15);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(15), 2);
        indentValue = formatter.format(15);
        assertEquals(indentValue, 4);
        assertEquals(document.getIndentLevel(15), 4);

        document.appendNewLine("end");
        formatter.handleDocumentChange("end", 16);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(16), 2);
        indentValue = formatter.format(16);
        assertEquals(indentValue, 0);
        assertEquals(document.getIndentLevel(16), 0);

        document.appendNewLine("function foobar");
        formatter.handleDocumentChange("function foobar", 17);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(17), 4);
        indentValue = formatter.format(17);
        assertEquals(indentValue, 0);
        assertEquals(document.getIndentLevel(17), 0);

        document.appendNewLine("end");
        formatter.handleDocumentChange("end", 18);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(18), 2);
        indentValue = formatter.format(18);
        assertEquals(indentValue, 0);
        assertEquals(document.getIndentLevel(18), 0);
    }

    /**
     * Tests that line continuations are correctly indented
     */
    public void testProcessLineContinuations() {
        Formatter formatter = new Formatter(null, null, document, tokenizerService);
        int indentValue;

        /**
         * disp ...   // Indent value should be 0
         *      x ... // Indent value should be 4
         *      y     // Indent value should still be 4
         */
        document.setCodes("disp ...");
        formatter.handleDocumentChange("disp ...", 0);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(0), 4);
        indentValue = formatter.format(0);
        assertEquals(indentValue, 0);
        assertEquals(document.getIndentLevel(0), 0);

        document.appendNewLine("x ...");
        formatter.handleDocumentChange("x ...", 1);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(1), 4);
        indentValue = formatter.format(1);
        assertEquals(indentValue, 4);
        assertEquals(document.getIndentLevel(1), 4);

        document.appendNewLine("y");
        formatter.handleDocumentChange("y", 2);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(2), 1);
        indentValue = formatter.format(2);
        assertEquals(indentValue, 4);
        assertEquals(document.getIndentLevel(2), 4);
    }

    public void testFormatWithNoLineContinuations() {
        Formatter formatter = new Formatter(null, null, document, tokenizerService);
        int indentValue;

        /**
         * disp ...   // Indent value should be 0
         *      x ... // Indent value should be 4
         *            // Indent value should still be 4
         * y          // Indent value should be 0
         */
        document.setCodes("disp ...");
        formatter.handleDocumentChange("disp ...", 0);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(0), 4);
        indentValue = formatter.format(0);
        assertEquals(indentValue, 0);
        assertEquals(document.getIndentLevel(0), 0);

        document.appendNewLine("x ...");
        formatter.handleDocumentChange("x ...", 1);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(1), 4);
        indentValue = formatter.format(1);
        assertEquals(indentValue, 4);
        assertEquals(document.getIndentLevel(1), 4);

        document.appendNewLine("");
        formatter.handleDocumentChange("", 2);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(2), 1);
        indentValue = formatter.format(2);
        assertEquals(indentValue, 4);
        assertEquals(document.getIndentLevel(2), 4);

        document.appendNewLine("y");
        formatter.handleDocumentChange("y", 3);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(3), 1);
        indentValue = formatter.format(3);
        assertEquals(indentValue, 0);
        assertEquals(document.getIndentLevel(3), 0);
    }

    public void testIsNotWhitespaceOrComment() {
        Formatter formatter = new Formatter(null, null, document, tokenizerService);
        boolean returnValue;
        Token token;

        document.setCodes("function foo");
        formatter.handleDocumentChange("function foo", 0);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(0), 4);
        token = new Token(2, TokenConstants.tokenConstants.get("ID"), 0, 9, 3, "foo");
        returnValue = formatter._isNotWhitespaceOrComment(token);
        assertTrue(returnValue);

        document.appendNewLine("if true %comment");
        formatter.handleDocumentChange("if true %comment", 1);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(1), 6);
        token = new Token(4, TokenConstants.tokenConstants.get("COMMENT"), 1, 8, 8, "%comment");
        returnValue = formatter._isNotWhitespaceOrComment(token);
        assertFalse(returnValue);

        document.appendNewLine("disp('hi')");
        formatter.handleDocumentChange("disp('hi') ", 2);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(2), 6);
        token = new Token(4, TokenConstants.tokenConstants.get("WHITE"), 2, 10, 1, " ");
        returnValue = formatter._isNotWhitespaceOrComment(token);
        assertFalse(returnValue);

        document.appendNewLine("%comment2");
        formatter.handleDocumentChange("%comment2", 3);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(3), 2);
        token = new Token(0, TokenConstants.tokenConstants.get("COMMENT"), 3, 0, 9, "%comment2");
        returnValue = formatter._isNotWhitespaceOrComment(token);
        assertTrue(returnValue);
    }

    public void testFindCorrespondingStartToken() {
        Formatter formatter = new Formatter(null, null, document, tokenizerService);
        Token returnToken;
        Token token;

        document.setCodes("function foo");
        formatter.handleDocumentChange("function foo", 0);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(0), 4);
        formatter.format(0);

        document.appendNewLine("if true");
        formatter.handleDocumentChange("if true", 1);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(1), 4);
        formatter.format(1);

        document.appendNewLine("disp('hi')");
        formatter.handleDocumentChange("disp('hi')", 2);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(2), 5);
        formatter.format(2);

        document.appendNewLine("else");
        formatter.handleDocumentChange("else", 3);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(3), 2);
        token = formatter.getMatlabTokens().getTokenState().getLastToken(3);
        returnToken = formatter._findCorrespondingStartToken(token, 3);
        // There is no whitespace added to lines, so IF is the first token
        token = new Token(0, TokenConstants.tokenConstants.get("IF"), 1, 0, 2, "if");
        assertTrue(returnToken.equals(token));
        formatter.format(3);

        document.appendNewLine("end");
        formatter.handleDocumentChange("end", 4);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(4), 2);
        token = formatter.getMatlabTokens().getTokenState().getLastToken(4);
        returnToken = formatter._findCorrespondingStartToken(token, 4);
        // There is no whitespace added to lines, so IF is the first token
        token = new Token(0, TokenConstants.tokenConstants.get("IF"), 1, 0, 2, "if");
        assertTrue(returnToken.equals(token));
        formatter.format(4);

        document.appendNewLine("end");
        formatter.handleDocumentChange("end", 5);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(5), 2);
        token = formatter.getMatlabTokens().getTokenState().getLastToken(5);
        returnToken = formatter._findCorrespondingStartToken(token, 5);
        token = new Token(0, TokenConstants.tokenConstants.get("FUNCTION"), 0, 0, 8, "function");
        assertTrue(returnToken.equals(token));
    }

    public void testSearchBackAndCalculateIndent() {
        Formatter formatter = new Formatter(null, null, document, tokenizerService);
        Token startToken;
        Token endToken;
        int indent;

        document.setCodes("function foo");
        formatter.handleDocumentChange("function foo", 0);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(0), 4);
        document.setLineIndent(0, 0);

        document.appendNewLine("switch a");
        formatter.handleDocumentChange("switch a", 1);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(1), 4);
        document.setLineIndent(1, 4);

        document.appendNewLine("case 1,");
        formatter.handleDocumentChange("case 1,", 2);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(2), 5);
        document.setLineIndent(2, 8);

        document.appendNewLine("end");
        formatter.handleDocumentChange("end", 3);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(3), 2);
        document.setLineIndent(3, 4);

        document.appendNewLine("if true disp('hi') end");
        formatter.handleDocumentChange("if true disp('hi') end", 4);
        assertEquals(formatter.getMatlabTokens().getNumberOfTokensOnLine(4), 11);
        document.setLineIndent(4, 4);

        startToken = formatter.getMatlabTokens().getTokenState().getLastToken(4);
        endToken = formatter.getMatlabTokens().getTokenState().getFirstToken(4);
        indent = formatter._searchBackAndCalculateIndent(startToken, endToken);
        assertEquals(indent, 4);
    }

}
