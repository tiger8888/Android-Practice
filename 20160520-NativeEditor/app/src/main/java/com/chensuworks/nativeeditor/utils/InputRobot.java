package com.chensuworks.nativeeditor.utils;

import android.view.KeyEvent;

import java.io.InputStream;
import java.util.logging.Handler;

public class InputRobot {
/*
    public static void readFromInputStream(InputStream inputStream, Handler handler) {
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
                    handlerPostDelayed(handler, charCount, ' ');
                } else {
                    c = (char) i;
                    //System.out.print("char = " + c);
                    //handlerPostDelayed(count, c);
                    handlerPostDelayed(handler, charCount, c);
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

    private void handlerPostDelayed(Handler handler, int index, char character) {
        final long interval = 300;
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
*/

}
