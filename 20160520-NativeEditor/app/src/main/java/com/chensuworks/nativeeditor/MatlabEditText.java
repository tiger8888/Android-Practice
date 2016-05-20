package com.chensuworks.nativeeditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.chensuworks.nativeeditor.plugins.autoindent.AutoIndent;
import com.chensuworks.nativeeditor.plugins.parenmatch.ParenMatcher;
import com.chensuworks.nativeeditor.plugins.syntaxhighlighter.SyntaxHighlighter;
import com.chensuworks.nativeeditor.plugins.undo.Task;
import com.chensuworks.nativeeditor.plugins.undo.UndoManager;
import com.chensuworks.nativeeditor.tokenizer.Document;
import com.chensuworks.nativeeditor.tokenizer.MatlabTokens;
import com.chensuworks.nativeeditor.utils.SpanUtil;
import com.chensuworks.nativeeditor.utils.StringUtil;
import com.mathworks.matlabmobile.preferences.EditorPreferences;

import java.util.Iterator;
import java.util.LinkedList;

public class MatlabEditText extends EditText implements TextWatcher {
    private Context mContext;

    private Handler mHandler = new Handler();

    private Rect mDrawingRect;
    private Rect mLineRect;
    private Paint mPaint;
    private Paint mGutterNumberPaint;
    private Paint mGutterBackgroundPaint;
    private Paint mNotepadUnderlinePaint;
    private Paint mHighlightLinePaint;

    private static final int mGutterNumberColor = Color.rgb(126, 126, 126);
    private static final int mGutterBackgroundColor = Color.rgb(245, 245, 245);
    private static final int mNotepadUnderlineColor = Color.rgb(198, 198, 198);
    private static final int mHighlightLineColor = Color.rgb(0, 0, 255);
    private static final int mHighlightLineAlpha = 30;

    private int mGutterWidthDP = 20;
    private float mGutterNumberOffsetDP = mGutterWidthDP / 2;

    private int mGutterWidth = 0;
    private float mDensity;

    private boolean contentChangedByAutoIndent = false;

    private EditorPreferences.EditorSettings mEditorSettings;

    private Document matlabDocument;
    private MatlabTokens matlabTokens;
    private SyntaxHighlighter syntaxHighlighter;
    private AutoIndent autoIndent;
    private UndoManager undoManager;

    private String textAboutToChange;
    private String textNewlyAdded;
    private int start;
    private int before;
    private int count;

    private boolean undoManagerOperation = false;

    public void setMatlabDocument(Document matlabDocument) {
        // Initialize Document and MatlabTokens using the String passed in from a file. (either from server or local)
        this.matlabDocument = matlabDocument;
        this.matlabTokens = new MatlabTokens(matlabDocument);
        syntaxHighlighter = new SyntaxHighlighter(getEditableText(), matlabDocument, matlabTokens);
        autoIndent = new AutoIndent(this, getEditableText(), matlabDocument, matlabTokens);
    }

    public Document getMatlabDocument() {
        return matlabDocument;
    }

    public boolean getContentChangedByAutoIndent() {
        return contentChangedByAutoIndent;
    }

    public void setContentChangedByAutoIndent(boolean contentChangedByAutoIndent) {
        this.contentChangedByAutoIndent = contentChangedByAutoIndent;
    }

    public UndoManager getUndoManager() {
        return undoManager;
    }

    public MatlabEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        addTextChangedListener(this);

        setHorizontallyScrolling(true);

        // Makes the TextView at most this many lines tall.
        // Setting this value overrides any other (maximum) height setting.
        // setMaxLines just limit the height of the EditText, but it doesn't limit the inner lines.
        //setMaxLines(10);

        mDrawingRect = new Rect();
        mLineRect = new Rect();
/*
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0x800000FF);
*/
        mGutterNumberPaint = new Paint();
        mGutterNumberPaint.setStyle(Paint.Style.STROKE);
        mGutterNumberPaint.setAntiAlias(true);
        mGutterNumberPaint.setColor(mGutterNumberColor);
        mGutterNumberPaint.setTextSize(getTextSize());

        mGutterBackgroundPaint = new Paint();
        mGutterBackgroundPaint.setStyle(Paint.Style.FILL);
        mGutterBackgroundPaint.setColor(mGutterBackgroundColor);

        mNotepadUnderlinePaint = new Paint();
        mNotepadUnderlinePaint.setStyle(Paint.Style.STROKE);
        mNotepadUnderlinePaint.setColor(mNotepadUnderlineColor);

        mHighlightLinePaint = new Paint();
        mHighlightLinePaint.setColor(mHighlightLineColor);
        mHighlightLinePaint.setAlpha(mHighlightLineAlpha);

/*
        mDensity = context.getResources().getDisplayMetrics().density;
*/
        // Load EditorSettings from SharedPreferences.
        mEditorSettings = new EditorPreferences.EditorSettings();
        EditorPreferences.loadEditorPreferences(context, mEditorSettings);

        undoManager = new UndoManager();
    }

    public EditorPreferences.EditorSettings getEditorSettings() {
        return mEditorSettings;
    }

    public void reloadEditorSettings() {
        EditorPreferences.loadEditorPreferences(mContext, mEditorSettings);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int lineCount = getLineCount();
        int digitNumber = (int) Math.floor(Math.log10(lineCount)) + 1;

        // TODO: what's the best mGutterWidth to set? Paint.measureText("0") gets the width of number 0, which is different from getTextSize();
        mGutterWidth = (int) mGutterNumberPaint.getTextSize() * digitNumber;
        //mGutterWidth = (int) mGutterNumberPaint.measureText("0") * digitNumber;
        setPadding(mGutterWidth, 0, 0, 0);

        //Log.d("TAG", "lineCount = " + lineCount
        //        + ", getScrollX() = " + getScrollX()
        //        + ", getScrollY() = " + getScrollY()
        //        + ", getWidth() = " + getWidth()
        //        + ", getHeight() = " + getHeight()
        //        + ", getLineHeight() = " + getLineHeight());

        //Log.d("WIDTH", "mGutterWidth = " + mGutterWidth + ", mGutterNumberPaint.measureText(\"0\") = " + mGutterNumberPaint.measureText("0") + ", mGutterNumberPaint.getTextSize() = " + mGutterNumberPaint.getTextSize());


        getDrawingRect(mDrawingRect);
        // Draw a gutter that spans the whole height of EditText.
        //canvas.drawRect(getScrollX(), 0, getScrollX() + mGutterWidth, getHeight(), mGutterBackgroundPaint);
        canvas.drawRect(mDrawingRect.left, mDrawingRect.top, mDrawingRect.left + mGutterWidth, mDrawingRect.bottom, mGutterBackgroundPaint);


        int firstVisibleLineIndex = 0;
        int lastVisibleLineIndex = lineCount;

        getLineBounds(0, mLineRect);
        int firstLineTop = mLineRect.top;
        int firstLineBottom = mLineRect.bottom;

        getLineBounds(lineCount - 1, mLineRect);
        int lastLineTop = mLineRect.top;
        int lastLineBottom = mLineRect.bottom;

            //Log.d("TAG", "mDrawingRect.top = " + mDrawingRect.top
            //        + ", mDrawingRect.bottom = " + mDrawingRect.bottom
            //        + ", firstLineTop = " + firstLineTop
            //        + ", firstLineBottom = " + firstLineBottom
            //        + ", lastLineTop = " + lastLineTop
            //        + ", lastLineBottom = " + lastLineBottom);

        // TODO: what's the best algorithm to get visible line range?

        if (lineCount > 1 && lastLineBottom > firstLineBottom && lastLineTop > firstLineTop) {
            firstVisibleLineIndex = Math.max(firstVisibleLineIndex, ((mDrawingRect.top - firstLineBottom) * (lineCount - 1)) / (lastLineBottom - firstLineBottom));
            lastVisibleLineIndex = Math.min(lastVisibleLineIndex, ((mDrawingRect.bottom - firstLineTop) * (lineCount - 1)) / (lastLineTop - firstLineTop) + 1);
        }
        //Log.d("TAG", "firstVisibleLineIndex = " + firstVisibleLineIndex + ", lastVisibleLineIndex = " + lastVisibleLineIndex);
        //Log.d("TAG", "getMinLines() = " + getMinLines() + ", getMaxLines() = " + getMaxLines());

        float leftMargin = (mGutterNumberPaint.getTextSize() - mGutterNumberPaint.measureText("0")) * digitNumber / 2;

        for (int i = firstVisibleLineIndex; i < lastVisibleLineIndex; i++) {
            int baseline = getLineBounds(i, mLineRect);

            int offset;
            if (i < 9) {
                offset = 1;
            } else if (i < 99) {
                offset = 2;
            } else if ( i < 999){
                offset = 3;
            } else {
                // TODO: should we only support line number < 999?
                offset = 4;
            }

            // Make line numbers right aligned.
            //canvas.drawText(Integer.toString(i + 1), mDrawingRect.left + mGutterWidth - mGutterNumberPaint.measureText("0") * offset, baseline, mGutterNumberPaint);
            canvas.drawText(Integer.toString(i + 1), mDrawingRect.left + leftMargin + (digitNumber - offset) * mGutterNumberPaint.measureText("0"), baseline, mGutterNumberPaint);

            if (mEditorSettings.getUseNotepadStyleUnderline()) {
                // Draw the notepad style underline.
                canvas.drawLine(mLineRect.left, baseline + 1, mLineRect.right, baseline + 1, mNotepadUnderlinePaint);
            }

            if (mEditorSettings.getUseLineHighlight()) {
                // Calculate which line the cursor is at.
                String texts = getText().toString();
                int selectionStart = getSelectionStart();

                int index = 0;
                int line = 0;

                while (index < selectionStart) {
                    index = texts.indexOf("\n", index);
                    if (index < 0) {
                        break;
                    }
                    if (index < selectionStart) {
                        ++line;
                    }
                    ++index;
                }

                if (i == line) {
                    canvas.drawRect(mLineRect, mHighlightLinePaint);
                }
            }
        }

        super.onDraw(canvas);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        //Log.d("onSelectionChanged", "selStart = " + selStart + ", selEnd = " + selEnd);
        //Log.d("onSelectionChanged", this.getText().toString());

        //if (pluginParenMatchEnabled) {
        if (mEditorSettings != null && mEditorSettings.getUseParenMatch()) {
            final Editable editableText = getEditableText();
            final ParenMatcher parenMatcher = new ParenMatcher(editableText);

            //TODO: need more exception handling both in ParenMatcher.handleCursorChanged and underline.
            if (editableText.length() > 1) {
                parenMatcher.handleCursorChanged(selStart, selEnd);
                SpanUtil.underlinePair(editableText, parenMatcher.getLeftParen(), parenMatcher.getRightParen());

                Runnable timerRunnable = new Runnable() {
                    @Override
                    public void run() {
                        SpanUtil.removeUnderlinePair(editableText, parenMatcher.getLeftParen(), parenMatcher.getRightParen());
                        //Log.d("JavaEditor_CanvasDraw", "=========================timerRunnalbe====================");
                    }
                };

                mHandler.postDelayed(timerRunnable, 2000);

            }
        }
    }

    /**
     * This method is called to notify you that within s, the count characters beginning at start
     * are about to be replaced by new text with length after.
     *
     * Use it when you need to take a look at the old text which is about to change.
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d("beforeTextChanged", "getSelectionStart() = " + getSelectionStart() + ", getSelectionEnd() = " + getSelectionEnd());
        Log.d("onTextChanged", "start = " + start + ", count = " + count + ", after = " + after);
        //Log.d("beforeTextChanged", "s = \"" + s + "\", start = " + start + ", count = " + count + ", after = " + after);
        textAboutToChange = s.toString().substring(start, start + count);
        Log.d("beforeTextChanged", "textAboutToChange = \"" + textAboutToChange + "\"");
        //Log.d("beforeTextChanged", "start = " + start + ", count = " + count + ", after = " + after);

    }

    /**
     * This method is called to notify you that within s, the count characters beginning at start
     * have just replaced old text that had length before.
     *
     * Use it when you need to see which characters in the text are new.
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.start = start;
        this.before = before;
        this.count = count;

        //Log.d("onTextChanged", "s = \"" + s + "\", start = " + start + ", before = " + before + ", count = " + count);
        Log.d("onTextChanged", "getSelectionStart() = " + getSelectionStart() + ", getSelectionEnd() = " + getSelectionEnd());
        Log.d("onTextChanged", "start = " + start + ", before = " + before + ", count = " + count);

        textNewlyAdded = s.toString().substring(start, start + count);
        Log.d("onTextChanged", "textNewlyAdded = \"" + textNewlyAdded + "\"");

        /*
        // if start(beforeTextChanged) == end(beforeTextChanged), before == 0, count > 1, it's INSERT_MULTIPLE_CHARACTERS
        // if start(beforeTextChanged) == end(beforeTextChanged), before == 0, count == 1, textNewlyAdded != "\n", it's INSERT_SINGLE_CHARACTER
        // if start(beforeTextChanged) == end(beforeTextChanged), before == 0, count == 1, textNewlyAdded == "\n", it's INSERT_BREAK
        */
        // if before == 0, count > 1, it's INSERT_MULTIPLE_CHARACTERS
        // if before == 0, count == 1, textNewlyAdded != "\n", it's INSERT_SINGLE_CHARACTER
        // if before == 0, count == 1, textNewlyAdded == "\n", it's INSERT_BREAK
        // if before == 1, count == 0, it's BACKSPACE or DELETE_SINGLE_SELECTION // TODO: how to tell the difference?
        // if before > 1, count == 0, it's DELETE_SELECTION_MULTIPLE_CHARACTERS
        // if before >= 1, count > 1, it's REPLACE SELECTION

    }

    private void updateUndoStack() {
        if (before == 0 && count > 1) {
            undoManager.addTask(new Task(Task.INSERT_MULTIPLE_CHARACTERS, start, "", textNewlyAdded, false));
        }

        if (before == 0 && count == 1 && !textNewlyAdded.equals("\n")) {
            undoManager.addTask(new Task(Task.INSERT_SINGLE_CHARACTER, start, "", textNewlyAdded, true));
        }

        if (before == 0 && count == 1 && textNewlyAdded.equals("\n")) {
            undoManager.addTask(new Task(Task.INSERT_BREAK, start, "", textNewlyAdded, false));
        }

        if (before == 1 && count == 0) {
            undoManager.addTask(new Task(Task.DELETE_SINGLE_CHARACTER, start, textAboutToChange, "", true));
        }

        if (before > 1 && count == 0) {
            undoManager.addTask(new Task(Task.DELETE_MULTIPLE_CHARACTERS, start, textAboutToChange, "", false));
        }

        if (before >= 1 && count > 1) {
            undoManager.addTask(new Task(Task.REPLACE_SELECTION, start, textAboutToChange, textNewlyAdded, false));
        }

    }

    private void updateRedoStack() {

    }

    private void clearRedoStack() {
        undoManager.clearRedoStack();
    }

    /**
     * This method is called to notify you that somewhere within s, the text has been changed.
     *
     * Use it when you need to edit the new text.
     */
    @Override
    public void afterTextChanged(Editable s) {
/*
        Handler timerHandler = new Handler();

        for (int i = 0; i < matlabDocument.getLinesOfCodes().size(); i++) {

            final int line = i;

            timerHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getEditableText().setSpan(new ForegroundColorSpan(Color.GREEN), matlabDocument.getLineRange(line).getStart(), matlabDocument.getLineRange(line).getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }, (i + 1) * 500);

        }
*/




        if (!undoManagerOperation) {
            // If it's user's operation, clear the redoStack & update undoStack
            clearRedoStack();
            updateUndoStack();

        } else {
            //updateRedoStack();
        }


        //editor.removeTextChangedListener(textWatcher);

        matlabDocument.setCodes(s.toString()); // even last line should have an EOL

        matlabTokens._updateTokens(0);
        MatlabTokens.TokensData tokensData = matlabTokens.getTokensData();

        if (mEditorSettings.getUseSyntaxHighlight()) {
            syntaxHighlighter.setS(s);
            syntaxHighlighter._updateTokens(tokensData);
        }

        if (!getContentChangedByAutoIndent()) {
            if (mEditorSettings.getUseAutoIndent()) {
                int cursorIndex = getSelectionStart();
                autoIndent.setS(s);
                if (textNewlyAdded.equals("\n")) {
                    //Toast.makeText(getApplicationContext(), "INSERT_BREAK_EVENT, cursorIndex = " + cursorIndex, Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "cursorIndex = " + cursorIndex + ", editor.getText().toString().length() = " + getText().toString().length());
                    //Log.d("TAG", "editor.getText().toString().substring(0, cursorIndex - 1) = \"" + editor.getText().toString().substring(0, cursorIndex - 1) + "\"");
                    //Log.d("TAG", "editor.getText().toString().substring(cursorIndex) = \"" + editor.getText().toString().substring(cursorIndex) + "\"");
                    // TODO: add line limit
                    // TODO: CASE1: ENTER
                    // TODO: CASE2: When loading file

                    if (getLineCount() > mEditorSettings.getMaxLineNumber()) {
                        Toast.makeText(mContext, "EXCEEDING LINE LIMIT!", Toast.LENGTH_SHORT).show();
                        setText(StringUtil.removeChar(getText().toString(), cursorIndex - 1));
                        setSelection(cursorIndex - 1);
                        return;
                    }

                    // If the "\n" is entered by undoManager, we don't want to auto-indent.
                    if (!undoManagerOperation) {
                        autoIndent._documentChanged(AutoIndent.INSERT_BREAK_EVENT, cursorIndex, textNewlyAdded);
                    }

                } else if (textNewlyAdded.equals("")) {
                    //Toast.makeText(getApplicationContext(), "DELETION_EVENT, cursorIndex = " + cursorIndex, Toast.LENGTH_SHORT).show();
                    autoIndent._documentChanged(AutoIndent.DELETION_EVENT, cursorIndex, textNewlyAdded);

                } else {
                    //Toast.makeText(getApplicationContext(), "INSERT_TEXT, cursorIndex = " + cursorIndex, Toast.LENGTH_SHORT).show();
                    autoIndent._documentChanged(AutoIndent.INSERT_TEXT, cursorIndex, textNewlyAdded);
                }
            }

        } else {
            setContentChangedByAutoIndent(false);
        }

        //editor.addTextChangedListener(textWatcher);

        undoManagerOperation = false;

    }

    public void undo() {
        if (undoManager.canUndo()) {

            LinkedList<Task> tasks = undoManager.undo();

            if (tasks.size() > 1) {
                // Merge tasks and operate once

                if (tasks.getFirst().getType().equals(Task.INSERT_SINGLE_CHARACTER)) {
                    // Need to remove the consecutive insertion.
                    Task firstTask = tasks.getFirst();
                    Task lastTask = tasks.getLast();

                    int startIndex = firstTask.getStartIndex();
                    int endIndex = lastTask.getStartIndex();

                    undoManagerOperation = true;
                    this.getEditableText().delete(startIndex, endIndex + 1);
                    this.setSelection(startIndex);
                }

                if (tasks.getFirst().getType().equals(Task.DELETE_SINGLE_CHARACTER)) {
                    // Need to insert the consecutive characters.
                    Iterator<Task> iterator = tasks.descendingIterator();
                    String stringToInsert = "";

                    while (iterator.hasNext()) {
                        stringToInsert += iterator.next().getReplacedText();
                    }

                    Task lastTask = tasks.getLast();
                    int endIndex = lastTask.getStartIndex();

                    undoManagerOperation = true;
                    this.getEditableText().insert(endIndex, stringToInsert);
                    this.setSelection(endIndex + lastTask.getLengthOfCharsInserted());
                }

            } else {
                // There's only 1 task in the list.

                Task task = tasks.getFirst();

                // TODO: add more comments
                if (task.getType().equals(Task.INSERT_MULTIPLE_CHARACTERS)) {
                    undoManagerOperation = true;
                    this.getEditableText().delete(task.getStartIndex(), task.getStartIndex() + task.getLengthOfCharsInserted());
                    this.setSelection(task.getStartIndex());
                }

                if (task.getType().equals(Task.DELETE_MULTIPLE_CHARACTERS)) {
                    undoManagerOperation = true;
                    this.getEditableText().insert(task.getStartIndex(), task.getReplacedText());
                    this.setSelection(task.getStartIndex() + task.getLengthOfCharsReplaced());
                }

                if (task.getType().equals(Task.INSERT_BREAK)) {
                    undoManagerOperation = true;
                    this.getEditableText().delete(task.getStartIndex(), task.getStartIndex() + 1);
                    this.setSelection(task.getStartIndex());
                }

                if (task.getType().equals(Task.REPLACE_SELECTION)) {
                    // TODO: there're bugs and behaviors are different from RTC.
                    undoManagerOperation = true;
                    this.getEditableText().delete(task.getStartIndex(), task.getStartIndex() + task.getLengthOfCharsInserted());
                    undoManagerOperation = true;
                    this.getEditableText().insert(task.getStartIndex(), task.getReplacedText());
                    this.setSelection(task.getStartIndex() + task.getLengthOfCharsReplaced());
                }
            }

            /*
            Task task = undoManager.undoSingleTask();

            Log.d("undoSingleTask()", "task.getType() = " + task.getType() +
                    ", task.getReplacedText() = " + task.getReplacedText() +
                    ", task.getInsertedText() = " + task.getInsertedText() +
                    ", task.getStartIndex() = " + task.getStartIndex() +
                    ", task.getLengthOfCharsReplaced() = " + task.getLengthOfCharsReplaced() +
                    ", task.getLengthOfCharsInserted() = " + task.getLengthOfCharsInserted() +
                    ", task.canMergeWithPrevious() = " + task.canMergeWithPrevious());

            if (task.getType().equals(Task.INSERT_SINGLE_CHARACTER)) {
                this.getEditableText().delete(task.getStartIndex(), task.getStartIndex() + task.getLengthOfCharsInserted());
            }
            */

        }
    }

    public void redo() {
        if (undoManager.canRedo()) {

            LinkedList<Task> tasks = undoManager.redo();

            if (tasks.size() > 1) {
                // Merge tasks and operate once

                if (tasks.getFirst().getType().equals(Task.INSERT_SINGLE_CHARACTER)) {
                    // Need to insert the consecutive characters.
                    Iterator<Task> iterator = tasks.listIterator();
                    String stringToInsert = "";

                    while (iterator.hasNext()) {
                        stringToInsert += iterator.next().getInsertedText();
                    }

                    Task firstTask = tasks.getFirst();
                    int startIndex = firstTask.getStartIndex();

                    undoManagerOperation = true;
                    this.getEditableText().insert(startIndex, stringToInsert);
                    this.setSelection(startIndex + stringToInsert.length());
                }

                if (tasks.getFirst().getType().equals(Task.DELETE_SINGLE_CHARACTER)) {
                    // Need to remove the consecutive insertion.
                    Task firstTask = tasks.getLast();
                    Task lastTask = tasks.getFirst();

                    int startIndex = firstTask.getStartIndex();
                    int endIndex = lastTask.getStartIndex();

                    undoManagerOperation = true;
                    this.getEditableText().delete(startIndex, endIndex + 1);
                    this.setSelection(startIndex);
                }

            } else {
                // There's only 1 task in the list.

                Task task = tasks.getFirst();

                if (task.getType().equals(Task.INSERT_MULTIPLE_CHARACTERS)) {
                    undoManagerOperation = true;
                    this.getEditableText().insert(task.getStartIndex(), task.getInsertedText());
                    this.setSelection(task.getStartIndex() + task.getLengthOfCharsInserted());
                }

                if (task.getType().equals(Task.DELETE_MULTIPLE_CHARACTERS)) {
                    undoManagerOperation = true;
                    this.getEditableText().delete(task.getStartIndex(), task.getStartIndex() + task.getLengthOfCharsReplaced());
                    this.setSelection(task.getStartIndex());
                }

                if (task.getType().equals(Task.INSERT_BREAK)) {
                    undoManagerOperation = true;
                    this.getEditableText().insert(task.getStartIndex(), task.getInsertedText());
                    this.setSelection(task.getStartIndex() + 1);
                }

                if (task.getType().equals(Task.REPLACE_SELECTION)) {
                    // TODO: there're bugs and behaviors are different from RTC.
                    undoManagerOperation = true;
                    this.getEditableText().delete(task.getStartIndex(), task.getStartIndex() + task.getLengthOfCharsReplaced());
                    undoManagerOperation = true;
                    this.getEditableText().insert(task.getStartIndex(), task.getInsertedText());
                    this.setSelection(task.getStartIndex() + task.getLengthOfCharsInserted());
                }
            }

            /*
            Task task = undoManager.redoSingleTask();

            Log.d("undoSingleTask()", "task.getType() = " + task.getType() +
                    ", task.getReplacedText() = " + task.getReplacedText() +
                    ", task.getInsertedText() = " + task.getInsertedText() +
                    ", task.getStartIndex() = " + task.getStartIndex() +
                    ", task.getLengthOfCharsReplaced() = " + task.getLengthOfCharsReplaced() +
                    ", task.getLengthOfCharsInserted() = " + task.getLengthOfCharsInserted() +
                    ", task.canMergeWithPrevious() = " + task.canMergeWithPrevious());

            if (task.getType().equals(Task.INSERT_SINGLE_CHARACTER)) {
                this.getEditableText().insert(task.getStartIndex(), task.getInsertedText());
            }
            */

        }
    }

    public void setNonEditable() {
        setEnabled(false);

        // If edit text is empty, do nothing.
        if (length() == 0) {
            return;
        }

        // EditText.setEnabled(true) will change the black text color to gray, so we have to repaint the foregrounds.
        ForegroundColorSpan[] spans = getText().getSpans(0, length(), ForegroundColorSpan.class);

        // If there're no ForegroundColor spans, simply color the whole editable text to black.
        if (spans.length == 0) {
            getEditableText().setSpan(new ForegroundColorSpan(Color.BLACK), 0, length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return;
        }

        // All texts except those already have ForegroundColor should be repainted as Color.BLACK.
        int blackStart = 0;
        int blackEnd = length();

        // For each of the span, check if there're extra texts before it that need to be repainted as Color.BLACK.
        for (int i = 0; i < spans.length; i++) {
            int start = getEditableText().getSpanStart(spans[i]);
            int end = getEditableText().getSpanEnd(spans[i]);

            if (start == blackStart) {
                // there's no extra texts before start that need to be repainted as Color.BLACK.

            } else {
                getEditableText().setSpan(new ForegroundColorSpan(Color.BLACK), blackStart, start - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            blackStart = end;
        }

        // There could be texts left after the last span, which need to be repainted as Color.BLACK.
        if (blackStart >= length() + 1) {
            // no texts left after the last span

        } else {
            getEditableText().setSpan(new ForegroundColorSpan(Color.BLACK), blackStart, blackEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

    }

    public void setEditable() {
        setEnabled(true);

        // Remove the extra BLACK spans added when setting the edit text readonly.
        ForegroundColorSpan[] spans = getText().getSpans(0, length(), ForegroundColorSpan.class);
        for (int i = 0; i < spans.length; i++) {
            if (spans[i].getForegroundColor() == Color.BLACK) {
                getEditableText().removeSpan(spans[i]);
            }
        }

    }

    /**
     * Scroll the editor to the location of {line, column}
     *      For example: there's an error starting at the first apostrophe in command a = {'hi}
     *      MATLAB will show there's an error in Line: 1 Column: 6
     *      1a2 3=4 5{6'hi} or you can think of the 6th character which is apostrophe here.
     * @param line Matlab code line is 1 based
     * @param column Matlab code column is 1 based
     */
    public void goToLineColumn(int line, int column) {
        // Measure the bounds of the characters from column 0 to the specified column on the specified line
        Rect charactersBounds = new Rect();
        Paint textPaint = getPaint();
        // Convert 1 based line/column to 0 based line/column in Document's context.
        textPaint.getTextBounds(getMatlabDocument().getLineText(line - 1), 0, column - 1, charactersBounds);

        // Scroll editor to the position of {line, column}
        scrollTo(charactersBounds.width(), getLineHeight() * (line - 1));

        // Set cursor to the position of {lien, column}
        setSelection(getMatlabDocument().getIndex(line - 1, column - 1));
    }

}
