package com.chensuworks.nativeeditor;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;

import com.chensuworks.nativeeditor.plugins.undo.UndoManager;
import com.chensuworks.nativeeditor.tokenizer.Document;
import com.chensuworks.nativeeditor.utils.CharacterKeyMap;
import com.mathworks.matlabmobile.preferences.EditorPreferences;
import com.mathworks.matlabmobile.view.LinedScrollView;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class MatlabEditorActivity extends AppCompatActivity {

    private LinedScrollView scrollView;
    private MatlabEditText editor;

    //private static final String hardcodedContent = "";
    //private static final String hardcodedContent = "function foo\nif true plot(1:10)";
    private static final String hardcodedContent = StringGenerator.generateMultipleLines(100, 10);

    private EditorPreferences.EditorSettings mEditorSettings;

    private final Handler handler = new Handler();

    private int charCount = 0;

    //private FloatingActionButton fab;
    private ImageButton fab;
    private boolean expanded = false;

    private View fabActionUnderline;
    private float offsetUnderline;

    private View fabActionLineHighlight;
    private float offsetLineHighlight;

    private View fabActionRedo;
    private float offsetRedo;

    private View fabActionUndo;
    private float offsetUndo;

    private View fabActionMaxLineNumber;
    private float offsetMaxLineNumber;

    private View fabActionReadFile1;
    private float offsetReadFile1;

    //private View fabActionReadFile2;
    //private float offsetReadFile2;

    //private View fabActionReadFile3;
    //private float offsetReadFile3;

    private View fabActionShowUndoManager;
    private float offsetShowUndoManager;

    private View fabActionReadOnly;
    private float offsetReadOnly;

    private View fabActionMoveLeft;
    private float offsetMoveLeft;

    private View fabActionMoveRight;
    private float offsetMoveRight;

    private View fabActionGoToLine;
    private float offsetGoToLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_editor_main_demo);

        final ViewGroup fabContainer = (ViewGroup) findViewById(R.id.fab_container);
        //fab = (FloatingActionButton) findViewById(R.id.fab);
        fab = (ImageButton) findViewById(R.id.navigate_to_next_page);
        fabActionUnderline = findViewById(R.id.fab_action_underline);
        fabActionLineHighlight = findViewById(R.id.fab_action_line_highlight);
        fabActionRedo = findViewById(R.id.fab_action_redo);
        fabActionUndo = findViewById(R.id.fab_action_undo);
        fabActionMaxLineNumber = findViewById(R.id.fab_action_max_line_number);
        fabActionReadFile1 = findViewById(R.id.fab_action_read_file1);
        //fabActionReadFile2 = findViewById(R.id.fab_action_read_file2);
        //fabActionReadFile3 = findViewById(R.id.fab_action_read_file3);
        fabActionShowUndoManager = findViewById(R.id.fab_action_show_undomanager);
        fabActionReadOnly = findViewById(R.id.fab_action_readonly);
        fabActionMoveLeft = findViewById(R.id.fab_action_move_left);
        fabActionMoveRight = findViewById(R.id.fab_action_move_right);
        fabActionGoToLine = findViewById(R.id.fab_action_go_to_line);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expanded = !expanded;
                if (expanded) {
                    expandFab();
                } else {
                    collapseFab();
                }
            }
        });

        fabActionUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "UNDO", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                editor.undo();
            }
        });

        fabActionRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "REDO", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                editor.redo();
            }
        });

        fabActionLineHighlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "LINE HIGHLIGHT", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                //EditorPreferences.enableLineHighlight(getApplicationContext());
                EditorPreferences.flipLineHighlight(getApplicationContext());
                editor.reloadEditorSettings();
                editor.invalidate();
            }
        });

        fabActionUnderline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "UNDERLINE", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                EditorPreferences.flipUnderline(getApplicationContext());
                editor.reloadEditorSettings();
                editor.invalidate();
            }
        });

        fabActionMaxLineNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditorPreferences.incrementMaxLineNumberBy10(getApplicationContext());
                editor.reloadEditorSettings();
                editor.invalidate();

                Snackbar.make(v, "MAX LINE NUMBER = " + EditorPreferences.getMaxLineNumber(getApplicationContext()), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        fabActionReadFile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "ROBOT IS STARTING WRITING CODES ... ...", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                //readFromInputStream(getApplicationContext().getResources().openRawResource(R.raw.file1));
                inputFromFile("file1");
            }
        });

        /*fabActionReadFile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "READING FILE2 ... ...", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                //readFromInputStream(getApplicationContext().getResources().openRawResource(R.raw.file2));
                inputFromFile("file2");
            }
        });

        fabActionReadFile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "READING FILE3 ... ...", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                //readFromInputStream(getApplicationContext().getResources().openRawResource(R.raw.file3));
                inputFromFile("file3");
            }
        });*/

        fabActionShowUndoManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "SHOW UNDO MANAGER", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                UndoManager undoManager = editor.getUndoManager();
                Log.d("TAG", "showing undoManager");
            }
        });

        fabActionReadOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "READ ONLY", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                // Flip the readonly flag.
                if (editor.isEnabled()) {
                    editor.setNonEditable();

                    editor.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    //scrollView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                } else {
                    editor.setEditable();

                    editor.setBackgroundColor(Color.WHITE);
                    //scrollView.setBackgroundColor(Color.WHITE);
                }

                Log.d("TAG", "editor.getInputType() = " + editor.getInputType());
            }
        });

        fabActionMoveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Move cursor LEFT", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                int scrollX = editor.getScrollX();
                int scrollY = editor.getScrollY();
            }
        });

        fabActionMoveRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Move cursor RIGHT", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

            }
        });

        fabActionGoToLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Go to LINE 100", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                // Set cursor to line 100, column 0
                //Rect bounds = new Rect();
                //Paint textPaint = editor.getPaint();
                //textPaint.getTextBounds(editor.getMatlabDocument().getLineText(99), 0, 5, bounds);

                //editor.scrollTo(bounds.width(), editor.getLineHeight() * 99);
                //editor.scrollTo(Math.round(editor.getTextSize() * 4), editor.getLineHeight() * 99);
                //editor.scrollTo(Math.round(editor.getTextSize() * 2), editor.getLineHeight() * 99);
                //editor.setSelection(editor.getMatlabDocument().getIndex(99, 100));

                editor.goToLineColumn(3, 3);
            }
        });

        fabContainer.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                fabContainer.getViewTreeObserver().removeOnPreDrawListener(this);

                offsetMaxLineNumber = fab.getY() - fabActionMaxLineNumber.getY();
                fabActionMaxLineNumber.setTranslationY(offsetMaxLineNumber);

                offsetUnderline = fab.getY() - fabActionUnderline.getY();
                fabActionUnderline.setTranslationY(offsetUnderline);

                offsetLineHighlight = fab.getY() - fabActionLineHighlight.getY();
                fabActionLineHighlight.setTranslationY(offsetLineHighlight);

                offsetRedo = fab.getY() - fabActionRedo.getY();
                fabActionRedo.setTranslationY(offsetRedo);

                offsetUndo = fab.getY() - fabActionUndo.getY();
                fabActionUndo.setTranslationY(offsetUndo);

                offsetMoveLeft = fab.getY() - fabActionMoveLeft.getY();
                fabActionMoveLeft.setTranslationY(offsetMoveLeft);

                offsetMoveRight = fab.getY() - fabActionMoveRight.getY();
                fabActionMoveRight.setTranslationY(offsetMoveRight);

                offsetGoToLine = fab.getY() - fabActionGoToLine.getY();
                fabActionGoToLine.setTranslationY(offsetGoToLine);

                offsetReadFile1 = fab.getX() - fabActionReadFile1.getX();
                fabActionReadFile1.setTranslationX(offsetReadFile1);

                /*
                offsetReadFile2 = fab.getX() - fabActionReadFile2.getX();
                fabActionReadFile2.setTranslationX(offsetReadFile2);

                offsetReadFile3 = fab.getX() - fabActionReadFile3.getX();
                fabActionReadFile3.setTranslationX(offsetReadFile3);
                */

                offsetShowUndoManager = fab.getX() - fabActionShowUndoManager.getX();
                fabActionShowUndoManager.setTranslationX(offsetShowUndoManager);

                offsetReadOnly = fab.getX() - fabActionReadOnly.getX();
                fabActionReadOnly.setTranslationX(offsetReadOnly);

                return true;
            }
        });

        /*
        final ImageButton fab = (ImageButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setSelected(!fab.isSelected());
                fab.setImageResource(fab.isSelected() ? R.drawable.animated_plus : R.drawable.animated_minus);

                Drawable drawable = fab.getDrawable();
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
            }
        });
        */

        // Load EditorSettings from SharedPreferences.
        mEditorSettings = new EditorPreferences.EditorSettings();
        EditorPreferences.loadEditorPreferences(getApplicationContext(), mEditorSettings);
        //Log.d("EditorSettings", "mEditorSettings.getUseSyntaxHighlight() = " + mEditorSettings.getUseSyntaxHighlight());
        //Log.d("EditorSettings", "mEditorSettings.getUseAutoIndent() = " + mEditorSettings.getUseAutoIndent());
        //Log.d("EditorSettings", "mEditorSettings.getMaxLineNumber() = " + mEditorSettings.getMaxLineNumber());


        editor = (MatlabEditText) findViewById(R.id.native_editor_multiline_edittext);
        editor.setContentChangedByAutoIndent(true);
        editor.setMatlabDocument(new Document(hardcodedContent));
        editor.setText(hardcodedContent);

        //scrollView = (LinedScrollView) findViewById(R.id.linedScrollView);
        //scrollView.setSmoothScrollingEnabled(true);
        //scrollView.setEditText(editor);


        //readFromInputStream(getApplicationContext().getResources().openRawResource(R.raw.file1));
        //readFromInputStream(getApplicationContext().getResources().openRawResource(R.raw.file2));


    }

    private void collapseFab() {
        fab.setImageResource(R.drawable.animated_minus);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                createCollapseAnimatorVertical(fabActionMaxLineNumber, offsetMaxLineNumber),
                createCollapseAnimatorVertical(fabActionUnderline, offsetUnderline),
                createCollapseAnimatorVertical(fabActionLineHighlight, offsetLineHighlight),
                createCollapseAnimatorVertical(fabActionRedo, offsetRedo),
                createCollapseAnimatorVertical(fabActionUndo, offsetUndo),
                createCollapseAnimatorVertical(fabActionMoveLeft, offsetMoveLeft),
                createCollapseAnimatorVertical(fabActionMoveRight, offsetMoveRight),
                createCollapseAnimatorVertical(fabActionGoToLine, offsetGoToLine),
                createCollapseAnimatorHorizontal(fabActionReadFile1, offsetReadFile1),
                //createCollapseAnimatorHorizontal(fabActionReadFile2, offsetReadFile2),
                //createCollapseAnimatorHorizontal(fabActionReadFile3, offsetReadFile3),
                createCollapseAnimatorHorizontal(fabActionShowUndoManager, offsetShowUndoManager),
                createCollapseAnimatorHorizontal(fabActionReadOnly, offsetReadOnly)
        );
        animatorSet.start();
        animateFab();
    }

    private void expandFab() {
        fab.setImageResource(R.drawable.animated_plus);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                createExpandAnimatorVertical(fabActionMaxLineNumber, offsetMaxLineNumber),
                createExpandAnimatorVertical(fabActionUnderline, offsetUnderline),
                createExpandAnimatorVertical(fabActionLineHighlight, offsetLineHighlight),
                createExpandAnimatorVertical(fabActionRedo, offsetRedo),
                createExpandAnimatorVertical(fabActionUndo, offsetUndo),
                createExpandAnimatorVertical(fabActionMoveLeft, offsetMoveLeft),
                createExpandAnimatorVertical(fabActionMoveRight, offsetMoveRight),
                createExpandAnimatorVertical(fabActionGoToLine, offsetGoToLine),
                createExpandAnimatorHorizontal(fabActionReadFile1, offsetReadFile1),
                //createExpandAnimatorHorizontal(fabActionReadFile2, offsetReadFile2),
                //createExpandAnimatorHorizontal(fabActionReadFile3, offsetReadFile3),
                createExpandAnimatorHorizontal(fabActionShowUndoManager, offsetShowUndoManager),
                createExpandAnimatorHorizontal(fabActionReadOnly, offsetReadOnly)
        );
        animatorSet.start();
        animateFab();
    }

    private static final String TRANSLATION_Y = "translationY";

    private Animator createCollapseAnimatorVertical(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, 0, offset)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private Animator createExpandAnimatorVertical(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, offset, 0)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private static final String TRANSLATION_X = "translationX";

    private Animator createCollapseAnimatorHorizontal(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_X, 0, offset)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private Animator createExpandAnimatorHorizontal(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_X, offset, 0)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private void animateFab() {
        Drawable drawable = fab.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    private char[] readWholeFile(String file) {
        Context ctx = getApplicationContext();
        int resId = ctx.getResources().getIdentifier(file, "raw", ctx.getPackageName());
        InputStream inputStream = ctx.getResources().openRawResource(resId);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream), 8192);
        try {
            int length = inputStream.available();
            char[] contents = new char[length];
            reader.read(contents, 0, length);
            return contents;

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {
            try {
                if (reader != null) reader.close();
            } catch (Exception e) {

            }
        }
    }

    private LinkedList<String> readEntries(String file) {
        final int EXPECTED_ELEMENTS = 24500;

        char[] contents = readWholeFile(file);
        if (contents == null) {
            return new LinkedList<String>();
        }

        //List<String> entries = new ArrayList<String>(EXPECTED_ELEMENTS);
        LinkedList<String> entries = new LinkedList<String>();
        String test;
        BufferedReader reader = new BufferedReader(new CharArrayReader(contents));
        try {
            while ((test = reader.readLine()) != null) {
                entries.add(test);
            }
            return entries;
        } catch (IOException e) {
            e.printStackTrace();
            return new LinkedList<String>();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (Exception e) {

            }
        }
    }

    private void inputFromFile(String file) {
        LinkedList<String> listOfLines = readEntries(file);

        for (String line : listOfLines) {
            for (char c : line.toCharArray()) {
                handlerPostDelayed(charCount, c);
                charCount++;
            }

            handlerPostDelayed(charCount, '\n');
            charCount++;
        }
    }

    private void readFromInputStream(InputStream inputStream) {
        int i;
        char c;

        try {
            //int count = 0;

            // reads till the end of the stream
            while ((i = inputStream.read()) != -1) {
                //System.out.print("count = " + count + ": ");
                //System.out.print("int = " + i + ", ");

                if (i == 13) { // DO NOTHING 13,12 => \n ?
                    //System.out.print("char = " + "\n");
                    //handlerPostDelayed(count, '\n');
                } else if (i == 32) {
                    //System.out.print("char = " + " ");
                    //handlerPostDelayed(count, ' ');
                    handlerPostDelayed(charCount, ' ');
                } else {
                    c = (char) i;
                    //System.out.print("char = " + c);
                    //handlerPostDelayed(count, c);
                    handlerPostDelayed(charCount, c);
                }

                //System.out.println();
                //count++;
                charCount++;
            }

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlerPostDelayed(int index, char character) {
        final long interval = 100;
        final int keycode = CharacterKeyMap.getCharacterKeyMap().get(character);
        final int meta_shift_on = CharacterKeyMap.getCharacterUppercaseMap().get(character);
        long when = (index + 1) * interval;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(1, 1, KeyEvent.ACTION_DOWN, keycode, 0, meta_shift_on));
            }
        }, when);
    }

    private void executeSimulation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //final long eventTime = SystemClock.uptimeMillis();
                editor.dispatchKeyEvent(new KeyEvent(1, 1, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_F, 0, KeyEvent.META_SHIFT_ON));
                //editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_F));
            }
        }, 500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(1, 1, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_U, 0, 0));
            }
        }, 1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_N));
            }
        }, 1500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_C));
            }
        }, 2000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_T));
            }
        }, 2500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_I));
            }
        }, 3000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_O));
            }
        }, 3500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_N));
            }
        }, 4000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE));
            }
        }, 4500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_F));
            }
        }, 5000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_O));
            }
        }, 5500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_O));
            }
        }, 6000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
            }
        }, 6500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_E));
            }
        }, 7000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_N));
            }
        }, 7500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_D));
            }
        }, 8000);

    }

}
