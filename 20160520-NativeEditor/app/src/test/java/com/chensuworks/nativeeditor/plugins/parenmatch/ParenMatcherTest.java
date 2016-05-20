package com.chensuworks.nativeeditor.plugins.parenmatch;

import android.text.SpannableStringBuilder;
import android.util.Log;

//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.CoreMatchers.*;
//import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.mockito.Matchers;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
//import static org.mockito.Matchers.*;

//@RunWith(MockitoJUnitRunner.class)
public class ParenMatcherTest {

    private static final String STRING_1 = "xlabel('Time (sec)');";
    private static final String STRING_2 = "axis([0 0.2 -1.2 1.2]);";
    private static final String STRING_3 = "(([{[({[]})]}]))";

/*
    @Test
    public void testHandleCursorChanged() {

        //SpannableStringBuilder mSpannableStringBuilder = mock(SpannableStringBuilder.class);
        //when(mSpannableStringBuilder.toString()).thenReturn(STRING_1);

        //TODO: How to use Mockito to mock android.util.Log.d() method?
        //TODO: Should I continue using Mockito?
        //Log mLog = mock(Log.class);
        //when(mLog.d(Matchers.<String>any(), Matchers.<String>any())).thenReturn(null);

        ParenMatcher parenMatcher = new ParenMatcher(mSpannableStringBuilder);

        // Set cursor to 0: "|xlabel('Time (sec)');"
        parenMatcher.handleCursorChanged(0, 0);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set selection to 0 & 1: "|x|label('Time (sec)');"
        parenMatcher.handleCursorChanged(0, 1);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 1: "x|label('Time (sec)');"
        parenMatcher.handleCursorChanged(1, 1);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 2: "xl|abel('Time (sec)');"
        parenMatcher.handleCursorChanged(2, 2);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 3: "xla|bel('Time (sec)');"
        parenMatcher.handleCursorChanged(3, 3);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 4: "xlab|el('Time (sec)');"
        parenMatcher.handleCursorChanged(4, 4);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 5: "xlabe|l('Time (sec)');"
        parenMatcher.handleCursorChanged(5, 5);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Outer () pair, index of 6 & 19
        // Set cursor to 6: "xlabel|('Time (sec)');"
        parenMatcher.handleCursorChanged(6, 6);
        assertEquals(parenMatcher.getLeftParen(), 6);
        assertEquals(parenMatcher.getRightParen(), 19);

        // Outer () pair, index of 6 & 19
        // Set selection to 6 & 7: "xlabel|(|'Time (sec)');"
        parenMatcher.handleCursorChanged(6, 7);
        assertEquals(parenMatcher.getLeftParen(), 6);
        assertEquals(parenMatcher.getRightParen(), 19);

        // Set cursor to 7: "xlabel(|'Time (sec)');"
        parenMatcher.handleCursorChanged(7, 7);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 8: "xlabel('|Time (sec)');"
        parenMatcher.handleCursorChanged(8, 8);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 9: "xlabel('T|ime (sec)');"
        parenMatcher.handleCursorChanged(9, 9);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 10: "xlabel('Ti|me (sec)');"
        parenMatcher.handleCursorChanged(10, 10);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 11: "xlabel('Tim|e (sec)');"
        parenMatcher.handleCursorChanged(11, 11);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 12: "xlabel('Time| (sec)');"
        parenMatcher.handleCursorChanged(12, 12);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Inner () pair, index of 13 & 17
        // Set cursor to 13: "xlabel('Time |(sec)');"
        parenMatcher.handleCursorChanged(13, 13);
        assertEquals(parenMatcher.getLeftParen(), 13);
        assertEquals(parenMatcher.getRightParen(), 17);

        // Inner () pair, index of 13 & 17
        // Set selection to 13 & 14: "xlabel('Time |(|sec)');"
        parenMatcher.handleCursorChanged(13, 14);
        assertEquals(parenMatcher.getLeftParen(), 13);
        assertEquals(parenMatcher.getRightParen(), 17);

        // Set cursor to 14: "xlabel('Time (|sec)');"
        parenMatcher.handleCursorChanged(14, 14);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 15: "xlabel('Time (s|ec)');"
        parenMatcher.handleCursorChanged(15, 15);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 16: "xlabel('Time (se|c)');"
        parenMatcher.handleCursorChanged(16, 16);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 17: "xlabel('Time (sec|)');"
        parenMatcher.handleCursorChanged(17, 17);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Inner () pair, index of 13 & 17
        // Set cursor to 18: "xlabel('Time (sec)|');"
        parenMatcher.handleCursorChanged(18, 18);
        assertEquals(parenMatcher.getLeftParen(), 13);
        assertEquals(parenMatcher.getRightParen(), 17);

        // Inner () pair, index of 13 & 17
        // Set selection to 17 & 18: "xlabel('Time (sec|)|');"
        parenMatcher.handleCursorChanged(17, 18);
        assertEquals(parenMatcher.getLeftParen(), 13);
        assertEquals(parenMatcher.getRightParen(), 17);

        // Set cursor to 19: "xlabel('Time (sec)'|);"
        parenMatcher.handleCursorChanged(19, 19);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Outer () pair, index of 6 & 19
        // Set cursor to 20: "xlabel('Time (sec)')|;"
        parenMatcher.handleCursorChanged(20, 20);
        assertEquals(parenMatcher.getLeftParen(), 6);
        assertEquals(parenMatcher.getRightParen(), 19);

        // Outer () pair, index of 6 & 19
        // Set selection to 19 & 20: "xlabel('Time (sec)'|)|;"
        parenMatcher.handleCursorChanged(19, 20);
        assertEquals(parenMatcher.getLeftParen(), 6);
        assertEquals(parenMatcher.getRightParen(), 19);

        // Set cursor to 21: "xlabel('Time (sec)');|"
        parenMatcher.handleCursorChanged(21, 21);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);


        parenMatcher = new ParenMatcher(new SpannableStringBuilder(STRING_2));

        // Set cursor to 0: "|axis([0 0.2 -1.2 1.2]);"
        parenMatcher.handleCursorChanged(0, 0);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 1: "a|xis([0 0.2 -1.2 1.2]);"
        parenMatcher.handleCursorChanged(1, 1);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 2: "ax|is([0 0.2 -1.2 1.2]);"
        parenMatcher.handleCursorChanged(2, 2);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 3: "axi|s([0 0.2 -1.2 1.2]);"
        parenMatcher.handleCursorChanged(3, 3);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // () pair, index of 4 & 21
        // Set cursor to 4: "axis|([0 0.2 -1.2 1.2]);"
        parenMatcher.handleCursorChanged(4, 4);
        assertEquals(parenMatcher.getLeftParen(), 4);
        assertEquals(parenMatcher.getRightParen(), 21);

        // [] pair, index of 5 & 20
        // Set cursor to 5: "axis(|[0 0.2 -1.2 1.2]);"
        parenMatcher.handleCursorChanged(5, 5);
        assertEquals(parenMatcher.getLeftParen(), 5);
        assertEquals(parenMatcher.getRightParen(), 20);

        // Set cursor to 6: "axis([|0 0.2 -1.2 1.2]);"
        parenMatcher.handleCursorChanged(6, 6);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 7: "axis([0| 0.2 -1.2 1.2]);"
        parenMatcher.handleCursorChanged(7, 7);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 8: "axis([0 |0.2 -1.2 1.2]);"
        parenMatcher.handleCursorChanged(8, 8);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 9: "axis([0 0|.2 -1.2 1.2]);"
        parenMatcher.handleCursorChanged(9, 9);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 10: "axis([0 0.|2 -1.2 1.2]);"
        parenMatcher.handleCursorChanged(10, 10);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 11: "axis([0 0.2| -1.2 1.2]);"
        parenMatcher.handleCursorChanged(11, 11);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 12: "axis([0 0.2 |-1.2 1.2]);"
        parenMatcher.handleCursorChanged(12, 12);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 13: "axis([0 0.2 -|1.2 1.2]);"
        parenMatcher.handleCursorChanged(13, 13);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 14: "axis([0 0.2 -1|.2 1.2]);"
        parenMatcher.handleCursorChanged(14, 14);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 15: "axis([0 0.2 -1.|2 1.2]);"
        parenMatcher.handleCursorChanged(15, 15);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 16: "axis([0 0.2 -1.2| 1.2]);"
        parenMatcher.handleCursorChanged(16, 16);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 17: "axis([0 0.2 -1.2 |1.2]);"
        parenMatcher.handleCursorChanged(17, 17);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 18: "axis([0 0.2 -1.2 1|.2]);"
        parenMatcher.handleCursorChanged(18, 18);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 19: "axis([0 0.2 -1.2 1.|2]);"
        parenMatcher.handleCursorChanged(19, 19);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // Set cursor to 20: "axis([0 0.2 -1.2 1.2|]);"
        parenMatcher.handleCursorChanged(20, 20);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // [] pair, index of 5 & 20
        // Set cursor to 21: "axis([0 0.2 -1.2 1.2]|);"
        parenMatcher.handleCursorChanged(21, 21);
        assertEquals(parenMatcher.getLeftParen(), 5);
        assertEquals(parenMatcher.getRightParen(), 20);

        // () pair, index of 4 & 21
        // Set cursor to 22: "axis([0 0.2 -1.2 1.2])|;"
        parenMatcher.handleCursorChanged(22, 22);
        assertEquals(parenMatcher.getLeftParen(), 4);
        assertEquals(parenMatcher.getRightParen(), 21);

        // Set cursor to 23: "axis([0 0.2 -1.2 1.2]);|"
        parenMatcher.handleCursorChanged(23, 23);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);


        parenMatcher = new ParenMatcher(new SpannableStringBuilder(STRING_3));

        // () pair, index of 0 & 15
        // Set cursor to 0: "|(([{[({[]})]}]))"
        parenMatcher.handleCursorChanged(0, 0);
        assertEquals(parenMatcher.getLeftParen(), 0);
        assertEquals(parenMatcher.getRightParen(), 15);

        // () pair, index of 1 & 14
        // Set cursor to 1: "(|([{[({[]})]}]))"
        parenMatcher.handleCursorChanged(1, 1);
        assertEquals(parenMatcher.getLeftParen(), 1);
        assertEquals(parenMatcher.getRightParen(), 14);

        // [] pair, index of 2 & 13
        // Set cursor to 2: "((|[{[({[]})]}]))"
        parenMatcher.handleCursorChanged(2, 2);
        assertEquals(parenMatcher.getLeftParen(), 2);
        assertEquals(parenMatcher.getRightParen(), 13);

        // {} pair, index of 3 & 12
        // Set cursor to 3: "(([|{[({[]})]}]))"
        parenMatcher.handleCursorChanged(3, 3);
        assertEquals(parenMatcher.getLeftParen(), 3);
        assertEquals(parenMatcher.getRightParen(), 12);

        // [] pair, index of 4 & 11
        // Set cursor to 4: "(([{|[({[]})]}]))"
        parenMatcher.handleCursorChanged(4, 4);
        assertEquals(parenMatcher.getLeftParen(), 4);
        assertEquals(parenMatcher.getRightParen(), 11);

        // () pair, index of 5 & 10
        // Set cursor to 5: "(([{[|({[]})]}]))"
        parenMatcher.handleCursorChanged(5, 5);
        assertEquals(parenMatcher.getLeftParen(), 5);
        assertEquals(parenMatcher.getRightParen(), 10);

        // {} pair, index of 6 & 9
        // Set cursor to 6: "(([{[(|{[]})]}]))"
        parenMatcher.handleCursorChanged(6, 6);
        assertEquals(parenMatcher.getLeftParen(), 6);
        assertEquals(parenMatcher.getRightParen(), 9);

        // [] pair, index of 7 & 8
        // Set cursor to 7: "(([{[({|[]})]}]))"
        parenMatcher.handleCursorChanged(7, 7);
        assertEquals(parenMatcher.getLeftParen(), 7);
        assertEquals(parenMatcher.getRightParen(), 8);

        // Set cursor to 8: "(([{[({[|]})]}]))"
        parenMatcher.handleCursorChanged(8, 8);
        assertEquals(parenMatcher.getLeftParen(), -1);
        assertEquals(parenMatcher.getRightParen(), -1);

        // [] pair, index of 7 & 8
        // Set cursor to 9: "(([{[({[]|})]}]))"
        parenMatcher.handleCursorChanged(9, 9);
        assertEquals(parenMatcher.getLeftParen(), 7);
        assertEquals(parenMatcher.getRightParen(), 8);

        // {} pair, index of 6 & 9
        // Set cursor to 10: "(([{[({[]}|)]}]))"
        parenMatcher.handleCursorChanged(10, 10);
        assertEquals(parenMatcher.getLeftParen(), 6);
        assertEquals(parenMatcher.getRightParen(), 9);

        // () pair, index of 5 & 10
        // Set cursor to 11: "(([{[({[]})|]}]))"
        parenMatcher.handleCursorChanged(11, 11);
        assertEquals(parenMatcher.getLeftParen(), 5);
        assertEquals(parenMatcher.getRightParen(), 10);

        // [] pair, index of 4 & 11
        // Set cursor to 12: "(([{[({[]})]|}]))"
        parenMatcher.handleCursorChanged(12, 12);
        assertEquals(parenMatcher.getLeftParen(), 4);
        assertEquals(parenMatcher.getRightParen(), 11);

        // {} pair, index of 3 & 12
        // Set cursor to 13: "(([{[({[]})]}|]))"
        parenMatcher.handleCursorChanged(13, 13);
        assertEquals(parenMatcher.getLeftParen(), 3);
        assertEquals(parenMatcher.getRightParen(), 12);

        // [] pair, index of 2 & 13
        // Set cursor to 14: "(([{[({[]})]}]|))"
        parenMatcher.handleCursorChanged(14, 14);
        assertEquals(parenMatcher.getLeftParen(), 2);
        assertEquals(parenMatcher.getRightParen(), 13);

        // () pair, index of 1 & 14
        // Set cursor to 15: "(([{[({[]})]}])|)"
        parenMatcher.handleCursorChanged(15, 15);
        assertEquals(parenMatcher.getLeftParen(), 1);
        assertEquals(parenMatcher.getRightParen(), 14);

        // () pair, index of 0 & 15
        // Set cursor to 16: "(([{[({[]})]}]))|"
        parenMatcher.handleCursorChanged(16, 16);
        assertEquals(parenMatcher.getLeftParen(), 0);
        assertEquals(parenMatcher.getRightParen(), 15);
    }
*/
}
