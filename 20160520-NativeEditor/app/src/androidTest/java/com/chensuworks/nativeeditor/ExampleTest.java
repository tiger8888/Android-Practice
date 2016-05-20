package com.chensuworks.nativeeditor;

/*
 * This is an example test project created in Eclipse to test NotePad which is a sample
 * project located in AndroidSDK/samples/android-11/NotePad
 *
 *
 * You can run these test cases either on the emulator or on device. Right click
 * the test project and select Run As --> Run As Android JUnit Test
 *
 * @author Renas Reda, renas.reda@robotium.com
 *
 */

import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;


public class ExampleTest extends ActivityInstrumentationTestCase2<MatlabEditorActivity> {

    private Solo solo;

    public ExampleTest() {
        super(MatlabEditorActivity.class);

    }

    @Override
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    public void testAddNote() throws Exception {
        //Unlock the lock screen
        //solo.unlockScreen();
        final MatlabEditText textView = (MatlabEditText)solo.getView(R.id.native_editor_multiline_edittext);

        /*
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                textView.setText("ab\nc");
            }
        });
        */
        //solo.clickOnMenuItem("Add note");
        //Assert that NoteEditor activity is opened
        //solo.assertCurrentActivity("Expected NoteEditor activity", "NoteEditor");
        //In text field 0, enter Note 1

        solo.enterText(textView, "f");
        //solo.enterText(0, "ab");
        //solo.enterText(1, "a");
        solo.sleep(500);

        solo.enterText(textView, "u");
        solo.sleep(500);

        solo.enterText(textView, "n");
        solo.sleep(500);

        solo.enterText(textView, "c");
        solo.sleep(500);

        solo.enterText(textView, "t");
        solo.sleep(500);

        solo.enterText(textView, "i");
        solo.sleep(500);

        solo.enterText(textView, "o");
        solo.sleep(500);

        solo.enterText(textView, "n");
        solo.sleep(500);

        solo.enterText(textView, " ");
        solo.sleep(500);

        solo.enterText(textView, "f");
        solo.sleep(500);

        solo.enterText(textView, "o");
        solo.sleep(500);

        solo.enterText(textView, "o");
        solo.sleep(500);

        //textView.setText(textView.getText() + "\n");
        //solo.sendKey(KeyEvent.KEYCODE_ENTER);
        //solo.typeText(textView, "\n");
        //solo.enterText(textView, "\n");
        //solo.enterText(textView, textView.getText() + "\n");
        //solo.sleep(500);

        //solo.typeText(textView, "e");
        solo.enterText(textView, "e");
        solo.sleep(500);

        //solo.sendKey(Solo.ENTER);
        //solo.sleep(2000);

        //solo.enterText(textView, "\nc");
        //solo.sleep(2000);

        String actualText = ((MatlabEditText) solo.getView(R.id.native_editor_multiline_edittext)).getText().toString();

        //assertEquals(actualText, "function foo\ne");
        assertEquals(actualText, "function fooe");
        /*solo.goBack();
        //Clicks on menu item
        solo.clickOnMenuItem("Add note");
        //In text field 0, type Note 2
        solo.typeText(0, "Note 2");
        //Go back to first activity
        solo.goBack();
        //Takes a screenshot and saves it in "/sdcard/Robotium-Screenshots/".
        solo.takeScreenshot();
        boolean notesFound = solo.searchText("Note 1") && solo.searchText("Note 2");
        //Assert that Note 1 & Note 2 are found
        assertTrue("Note 1 and/or Note 2 are not found", notesFound);*/
    }

}