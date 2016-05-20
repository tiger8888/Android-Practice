package com.mathworks.matlabmobile.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ScrollView;

import com.chensuworks.nativeeditor.MatlabEditText;

public class LinedScrollView extends ScrollView {

    private MatlabEditText mEditText;

    private Rect mDrawingRect;
    private Rect mLineRect;

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
    private int mGutterWidth = 0;

    public LinedScrollView(Context context) {
        super(context);
        init(context);
    }

    public LinedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LinedScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mDrawingRect = new Rect();
        mLineRect = new Rect();

        mGutterNumberPaint = new Paint();
        mGutterNumberPaint.setStyle(Paint.Style.STROKE);
        mGutterNumberPaint.setAntiAlias(true);
        mGutterNumberPaint.setColor(mGutterNumberColor);

        mGutterBackgroundPaint = new Paint();
        mGutterBackgroundPaint.setStyle(Paint.Style.FILL);
        mGutterBackgroundPaint.setColor(mGutterBackgroundColor);

        mNotepadUnderlinePaint = new Paint();
        mNotepadUnderlinePaint.setStyle(Paint.Style.STROKE);
        mNotepadUnderlinePaint.setColor(mNotepadUnderlineColor);

        mHighlightLinePaint = new Paint();
        mHighlightLinePaint.setColor(mHighlightLineColor);
        mHighlightLinePaint.setAlpha(mHighlightLineAlpha);
    }

    public void setEditText(MatlabEditText editText) {
        mEditText = editText;
        // TODO: maybe this should be more lazily initialized
        mGutterNumberPaint.setTextSize(mEditText.getTextSize());
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mEditText == null) {
            super.onDraw(canvas);
            return;
        }

        int lineCount = mEditText.getLineCount();
        int digitNumber = (int) Math.floor(Math.log10(lineCount)) + 1;

        // TODO: what's the best mGutterWidth to set? Paint.measureText("0") gets the width of number 0, which is different from getTextSize();
        mGutterWidth = (int) mGutterNumberPaint.getTextSize() * digitNumber;
        //mGutterWidth = (int) mGutterNumberPaint.measureText("0") * digitNumber;
        setPadding(mGutterWidth, 0, 0, 0);

        //Log.d("WIDTH", "mGutterWidth = " + mGutterWidth + ", mGutterNumberPaint.measureText(\"0\") = " + mGutterNumberPaint.measureText("0") + ", mGutterNumberPaint.getTextSize() = " + mGutterNumberPaint.getTextSize());


        getDrawingRect(mDrawingRect);
        // Draw a gutter that spans the whole height of EditText.
        //canvas.drawRect(getScrollX(), 0, getScrollX() + mGutterWidth, getHeight(), mGutterBackgroundPaint);
        canvas.drawRect(mDrawingRect.left, mDrawingRect.top, mDrawingRect.left + mGutterWidth, mDrawingRect.bottom, mGutterBackgroundPaint);


        int firstVisibleLineIndex = 0;
        int lastVisibleLineIndex = lineCount;

        mEditText.getLineBounds(0, mLineRect);
        int firstLineTop = mLineRect.top;
        int firstLineBottom = mLineRect.bottom;

        mEditText.getLineBounds(lineCount - 1, mLineRect);
        int lastLineTop = mLineRect.top;
        int lastLineBottom = mLineRect.bottom;


        // TODO: what's the best algorithm to get visible line range?

        if (lineCount > 1 && lastLineBottom > firstLineBottom && lastLineTop > firstLineTop) {
            firstVisibleLineIndex = Math.max(firstVisibleLineIndex, ((mDrawingRect.top - firstLineBottom) * (lineCount - 1)) / (lastLineBottom - firstLineBottom));
            lastVisibleLineIndex = Math.min(lastVisibleLineIndex, ((mDrawingRect.bottom - firstLineTop) * (lineCount - 1)) / (lastLineTop - firstLineTop) + 1);
        }
        //Log.d("TAG", "firstVisibleLineIndex = " + firstVisibleLineIndex + ", lastVisibleLineIndex = " + lastVisibleLineIndex);
        //Log.d("TAG", "getMinLines() = " + getMinLines() + ", getMaxLines() = " + getMaxLines());

        float leftMargin = (mGutterNumberPaint.getTextSize() - mGutterNumberPaint.measureText("0")) * digitNumber / 2;

        for (int i = firstVisibleLineIndex; i < lastVisibleLineIndex; i++) {
            int baseline = mEditText.getLineBounds(i, mLineRect);

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

            if (mEditText.getEditorSettings().getUseNotepadStyleUnderline()) {
                // Draw the notepad style underline.
                canvas.drawLine(mLineRect.left, baseline + 1, mLineRect.right, baseline + 1, mNotepadUnderlinePaint);
            }

            if (mEditText.getEditorSettings().getUseLineHighlight()) {
                // Calculate which line the cursor is at.
                String texts = mEditText.getText().toString();
                int selectionStart = mEditText.getSelectionStart();

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



}
