package com.chensuworks.nativeeditor.utils;

import android.view.KeyEvent;

import java.util.HashMap;

public class CharacterKeyMap {

    private final static HashMap<Character, Integer> characterKeyMap;
    private final static HashMap<Character, Integer> characterUppercaseMap;

    static {
        characterKeyMap = new HashMap<Character, Integer>();

        characterKeyMap.put('a', KeyEvent.KEYCODE_A);
        characterKeyMap.put('b', KeyEvent.KEYCODE_B);
        characterKeyMap.put('c', KeyEvent.KEYCODE_C);
        characterKeyMap.put('d', KeyEvent.KEYCODE_D);
        characterKeyMap.put('e', KeyEvent.KEYCODE_E);
        characterKeyMap.put('f', KeyEvent.KEYCODE_F);
        characterKeyMap.put('g', KeyEvent.KEYCODE_G);
        characterKeyMap.put('h', KeyEvent.KEYCODE_H);
        characterKeyMap.put('i', KeyEvent.KEYCODE_I);
        characterKeyMap.put('j', KeyEvent.KEYCODE_J);
        characterKeyMap.put('k', KeyEvent.KEYCODE_K);
        characterKeyMap.put('l', KeyEvent.KEYCODE_L);
        characterKeyMap.put('m', KeyEvent.KEYCODE_M);
        characterKeyMap.put('n', KeyEvent.KEYCODE_N);
        characterKeyMap.put('o', KeyEvent.KEYCODE_O);
        characterKeyMap.put('p', KeyEvent.KEYCODE_P);
        characterKeyMap.put('q', KeyEvent.KEYCODE_Q);
        characterKeyMap.put('r', KeyEvent.KEYCODE_R);
        characterKeyMap.put('s', KeyEvent.KEYCODE_S);
        characterKeyMap.put('t', KeyEvent.KEYCODE_T);
        characterKeyMap.put('u', KeyEvent.KEYCODE_U);
        characterKeyMap.put('v', KeyEvent.KEYCODE_V);
        characterKeyMap.put('w', KeyEvent.KEYCODE_W);
        characterKeyMap.put('x', KeyEvent.KEYCODE_X);
        characterKeyMap.put('y', KeyEvent.KEYCODE_Y);
        characterKeyMap.put('z', KeyEvent.KEYCODE_Z);

        characterKeyMap.put('A', KeyEvent.KEYCODE_A);
        characterKeyMap.put('B', KeyEvent.KEYCODE_B);
        characterKeyMap.put('C', KeyEvent.KEYCODE_C);
        characterKeyMap.put('D', KeyEvent.KEYCODE_D);
        characterKeyMap.put('E', KeyEvent.KEYCODE_E);
        characterKeyMap.put('F', KeyEvent.KEYCODE_F);
        characterKeyMap.put('G', KeyEvent.KEYCODE_G);
        characterKeyMap.put('H', KeyEvent.KEYCODE_H);
        characterKeyMap.put('I', KeyEvent.KEYCODE_I);
        characterKeyMap.put('J', KeyEvent.KEYCODE_J);
        characterKeyMap.put('K', KeyEvent.KEYCODE_K);
        characterKeyMap.put('L', KeyEvent.KEYCODE_L);
        characterKeyMap.put('M', KeyEvent.KEYCODE_M);
        characterKeyMap.put('N', KeyEvent.KEYCODE_N);
        characterKeyMap.put('O', KeyEvent.KEYCODE_O);
        characterKeyMap.put('P', KeyEvent.KEYCODE_P);
        characterKeyMap.put('Q', KeyEvent.KEYCODE_Q);
        characterKeyMap.put('R', KeyEvent.KEYCODE_R);
        characterKeyMap.put('S', KeyEvent.KEYCODE_S);
        characterKeyMap.put('T', KeyEvent.KEYCODE_T);
        characterKeyMap.put('U', KeyEvent.KEYCODE_U);
        characterKeyMap.put('V', KeyEvent.KEYCODE_V);
        characterKeyMap.put('W', KeyEvent.KEYCODE_W);
        characterKeyMap.put('X', KeyEvent.KEYCODE_X);
        characterKeyMap.put('Y', KeyEvent.KEYCODE_Y);
        characterKeyMap.put('Z', KeyEvent.KEYCODE_Z);

        characterKeyMap.put('0', KeyEvent.KEYCODE_0);
        characterKeyMap.put('1', KeyEvent.KEYCODE_1);
        characterKeyMap.put('2', KeyEvent.KEYCODE_2);
        characterKeyMap.put('3', KeyEvent.KEYCODE_3);
        characterKeyMap.put('4', KeyEvent.KEYCODE_4);
        characterKeyMap.put('5', KeyEvent.KEYCODE_5);
        characterKeyMap.put('6', KeyEvent.KEYCODE_6);
        characterKeyMap.put('7', KeyEvent.KEYCODE_7);
        characterKeyMap.put('8', KeyEvent.KEYCODE_8);
        characterKeyMap.put('9', KeyEvent.KEYCODE_9);

        characterKeyMap.put('!', KeyEvent.KEYCODE_1);
        characterKeyMap.put('@', KeyEvent.KEYCODE_2);
        characterKeyMap.put('#', KeyEvent.KEYCODE_3);
        characterKeyMap.put('$', KeyEvent.KEYCODE_4);
        characterKeyMap.put('%', KeyEvent.KEYCODE_5);
        characterKeyMap.put('^', KeyEvent.KEYCODE_6);
        characterKeyMap.put('&', KeyEvent.KEYCODE_7);
        characterKeyMap.put('*', KeyEvent.KEYCODE_8);
        characterKeyMap.put('(', KeyEvent.KEYCODE_9);
        characterKeyMap.put(')', KeyEvent.KEYCODE_0);

        characterKeyMap.put(' ', KeyEvent.KEYCODE_SPACE);
        characterKeyMap.put('\n', KeyEvent.KEYCODE_ENTER);
        characterKeyMap.put('=', KeyEvent.KEYCODE_EQUALS);
        characterKeyMap.put(';', KeyEvent.KEYCODE_SEMICOLON);
        characterKeyMap.put(':', KeyEvent.KEYCODE_SEMICOLON);
        characterKeyMap.put('+', KeyEvent.KEYCODE_PLUS);
        characterKeyMap.put('-', KeyEvent.KEYCODE_MINUS);
        characterKeyMap.put('/', KeyEvent.KEYCODE_SLASH);

        characterKeyMap.put(',', KeyEvent.KEYCODE_COMMA);
        characterKeyMap.put('!', KeyEvent.KEYCODE_COMMA);
        characterKeyMap.put('.', KeyEvent.KEYCODE_PERIOD);
        characterKeyMap.put('?', KeyEvent.KEYCODE_PERIOD);

        characterKeyMap.put('[', KeyEvent.KEYCODE_LEFT_BRACKET);
        characterKeyMap.put(']', KeyEvent.KEYCODE_RIGHT_BRACKET);
        characterKeyMap.put('{', KeyEvent.KEYCODE_LEFT_BRACKET);
        characterKeyMap.put('}', KeyEvent.KEYCODE_RIGHT_BRACKET);


        characterUppercaseMap = new HashMap<Character, Integer>();

        characterUppercaseMap.put('a', 0);
        characterUppercaseMap.put('b', 0);
        characterUppercaseMap.put('c', 0);
        characterUppercaseMap.put('d', 0);
        characterUppercaseMap.put('e', 0);
        characterUppercaseMap.put('f', 0);
        characterUppercaseMap.put('g', 0);
        characterUppercaseMap.put('h', 0);
        characterUppercaseMap.put('i', 0);
        characterUppercaseMap.put('j', 0);
        characterUppercaseMap.put('k', 0);
        characterUppercaseMap.put('l', 0);
        characterUppercaseMap.put('m', 0);
        characterUppercaseMap.put('n', 0);
        characterUppercaseMap.put('o', 0);
        characterUppercaseMap.put('p', 0);
        characterUppercaseMap.put('q', 0);
        characterUppercaseMap.put('r', 0);
        characterUppercaseMap.put('s', 0);
        characterUppercaseMap.put('t', 0);
        characterUppercaseMap.put('u', 0);
        characterUppercaseMap.put('v', 0);
        characterUppercaseMap.put('w', 0);
        characterUppercaseMap.put('x', 0);
        characterUppercaseMap.put('y', 0);
        characterUppercaseMap.put('z', 0);

        characterUppercaseMap.put('0', 0);
        characterUppercaseMap.put('1', 0);
        characterUppercaseMap.put('2', 0);
        characterUppercaseMap.put('3', 0);
        characterUppercaseMap.put('4', 0);
        characterUppercaseMap.put('5', 0);
        characterUppercaseMap.put('6', 0);
        characterUppercaseMap.put('7', 0);
        characterUppercaseMap.put('8', 0);
        characterUppercaseMap.put('9', 0);

        characterUppercaseMap.put('!', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('@', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('#', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('$', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('%', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('^', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('&', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('*', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('(', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put(')', KeyEvent.META_SHIFT_ON);

        characterUppercaseMap.put('A', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('B', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('C', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('D', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('E', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('F', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('G', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('H', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('I', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('J', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('K', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('L', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('M', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('N', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('O', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('P', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('Q', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('R', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('S', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('T', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('U', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('V', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('W', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('X', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('Y', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('Z', KeyEvent.META_SHIFT_ON);

        characterUppercaseMap.put(' ', 0);
        characterUppercaseMap.put('\n', 0);
        characterUppercaseMap.put('=', 0);
        characterUppercaseMap.put(';', 0);
        characterUppercaseMap.put(':', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('+', 0);
        characterUppercaseMap.put('-', 0);
        characterUppercaseMap.put('/', 0);

        characterUppercaseMap.put('[', 0);
        characterUppercaseMap.put(']', 0);
        characterUppercaseMap.put('{', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('}', KeyEvent.META_SHIFT_ON);

        characterUppercaseMap.put(',', 0);
        characterUppercaseMap.put('!', KeyEvent.META_SHIFT_ON);
        characterUppercaseMap.put('.', 0);
        characterUppercaseMap.put('?', KeyEvent.META_SHIFT_ON);
    }

    public static HashMap<Character, Integer> getCharacterKeyMap() {
        return characterKeyMap;
    }

    public static HashMap<Character, Integer> getCharacterUppercaseMap() {
        return characterUppercaseMap;
    }
}
