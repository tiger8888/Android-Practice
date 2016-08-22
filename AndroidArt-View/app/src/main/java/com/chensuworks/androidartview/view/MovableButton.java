package com.chensuworks.androidartview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import com.nineoldandroids.view.ViewHelper;

public class MovableButton extends Button {

    private static final String TAG = "MovableButton";

    private int mLastX;
    private int mLastY;

    public MovableButton(Context context) {
        super(context);
    }

    public MovableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MovableButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        Log.d(TAG, "x = " + x + ", y = " + y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_UP:
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                int translationX = (int) ViewHelper.getTranslationX(this) + deltaX;
                int translationY = (int) ViewHelper.getTranslationY(this) + deltaY;

                ViewHelper.setTranslationX(this, translationX);
                ViewHelper.setTranslationY(this, translationY);
                break;

            default:
                break;
        }

        mLastX = x;
        mLastY = y;

        return true;
    }

}
