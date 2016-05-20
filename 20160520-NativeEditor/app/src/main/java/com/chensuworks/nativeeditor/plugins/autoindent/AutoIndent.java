package com.chensuworks.nativeeditor.plugins.autoindent;

import android.text.Editable;
import android.util.Log;

import com.chensuworks.nativeeditor.MatlabEditText;
import com.chensuworks.nativeeditor.plugins.Utils;
import com.chensuworks.nativeeditor.tokenizer.Document;
import com.chensuworks.nativeeditor.tokenizer.MatlabTokens;
import com.chensuworks.nativeeditor.tokenizer.PositionInfo;

public class AutoIndent {

    public static final int INSERT_TEXT = 1;
    public static final int INSERT_BREAK_EVENT = 2;
    public static final int DELETION_EVENT = 3;
    public static final int FORMAT_CHANGE_EVENT = 4;

    private MatlabEditText editor;
    private Editable s;

    private Document _document;
    private MatlabTokens _tokenizerService;
    private Formatter _formatter;
    private FormatSupport _formatSupport;
    private boolean _isEnabled;

    public AutoIndent(MatlabEditText editor, Editable s, Document matlabDocument, MatlabTokens matlabTokens) {
        this.editor = editor;
        this.s = s;
        this._document = matlabDocument;
        this._tokenizerService = matlabTokens;

        this._formatter = new Formatter(this.editor, this.s, this._document, this._tokenizerService);
        this._formatSupport = new FormatSupport(this._document, this._formatter);

        this._isEnabled = true;
    }

    public void enable() {
        this._isEnabled = true;
    }

    public void disable() {
        this._isEnabled = false;
    }

    public void setS(Editable s) {
        this.s = s;
    }

    // called in client
    // autoIndentor.setS(s)
    // autoIndentor._documentChanged(1); // 1 is for insert_text for example.
    public void _documentChanged(int type, int cursorIndex, String text) {
        if (!this._isEnabled) {
            return;
        }

        switch (type) {
            case INSERT_TEXT:
                _handleInsertTextEvent(cursorIndex, text);
                break;
            case INSERT_BREAK_EVENT:
                _handleLineBreakEvent(cursorIndex);
                break;
            case DELETION_EVENT:
                _handleDeleteEvent(cursorIndex);
                break;
        }
    }

    public void _handleInsertTextEvent(int cursorIndex, String text) {
        // Do nothing for block inserts
        if (!text.contains("\n")) {
            PositionInfo cursorPos = _document.getLineColumn(cursorIndex);
            String codeOnCurrentLine = _document.getLineText(cursorPos.getLine());

            _formatter.handleDocumentChange(codeOnCurrentLine, cursorPos.getLine());
            _formatIfNecessary(text, cursorPos);

            // Set cursor to end of that line
            //TODO: is this logic solid?
            //TODO: THINK MORE ABOUT THIS!!!
            editor.setSelection(_document.getLineRange(cursorPos.getLine()).getEnd());
            //editor.setSelection(cursorIndex);
        }
    }

    public void _handleLineBreakEvent(int cursorIndex) {
        int lineNumber = _document.getLineColumn(cursorIndex).getLine();
        String codeOnCurrentLine = _document.getLineText(lineNumber);
        //Log.d("_handleLineBreakEvent", "codeOnCurrentLine = \"" + codeOnCurrentLine + "\"");

        // 'lineNumber' points to the new line added. We want to tokenize from line where ENTER
        // was pressed. If 'lineNumber' is zero, start from there.
        _updateState(Math.max(0, lineNumber - 1));
        int indentLevel = _formatter.format(lineNumber);
        //Log.d("_handleLineBreakEvent", "indentLevel = " + indentLevel);

        // Adjust cursor position accordingly
        _adjustCursorPosition(codeOnCurrentLine, lineNumber, indentLevel);
    }

    public void _handleDeleteEvent(int cursorIndex) {
        PositionInfo cursorPos = _document.getLineColumn(cursorIndex);
        int lineNumber = cursorPos.getLine();
        _updateState(lineNumber);
        // Using the same key for backspace and delete.
        String typedText = "\b";
        _formatIfNecessary(typedText, cursorPos);
    }

    public void _updateState(int startLineNumber) {
        // Assert startLineNumber >= 0
        this._formatter.updateState(this._document.getCodes(), startLineNumber);
    }

    public void _adjustCursorPosition(String codeOnCurrentLine, int lineNumber, int indent) {
        if (Utils.isWhitespace(codeOnCurrentLine)) {
            // If code is only whitespace implies that text was not carried over from previous line.
            // Move cursor to the end of the line after adding indent spaces.
            editor.setSelection(_document.getLineRange(lineNumber).getEnd());

        } else {
            // If text was carried over from previous line, maintain position of cursor. Move it
            // to after the added indent spaces but before the text.
            // Scroll cursor into view (3rd argument = true)
            editor.setSelection(_document.getLineRange(lineNumber).getStart() + indent);
        }
    }

    public void _formatIfNecessary(String typedText, PositionInfo cursorPos) {
        boolean triggerFormat = this._formatSupport.shouldKeyTriggerFormat(typedText, cursorPos);
        if (triggerFormat) {
            this._formatter.format(cursorPos.getLine());
        }
    }

    /**
     * Trigger Auto Indent in the following conditions:
     * 1. When Rich Text is changed to code.
     * 2. When the paragraph format change is limited to a single line.
     */
    public boolean _shouldFormatChangeTriggerIndent() {
        return true;
    }

}
