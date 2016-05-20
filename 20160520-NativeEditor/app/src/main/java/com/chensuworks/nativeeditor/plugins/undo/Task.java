package com.chensuworks.nativeeditor.plugins.undo;

public class Task {

    private String type;
    private String replacedText;
    private String insertedText;
    private int startIndex;
    private int lengthOfCharsReplaced;
    private int lengthOfCharsInserted;
    private boolean canMergeWithPrevious;

    public static final String INSERT_SINGLE_CHARACTER = "INSERT_SINGLE_CHARACTER";
    public static final String INSERT_MULTIPLE_CHARACTERS = "INSERT_MULTIPLE_CHARACTERS";
    public static final String DELETE_SINGLE_CHARACTER = "DELETE_SINGLE_CHARACTER";
    public static final String DELETE_MULTIPLE_CHARACTERS = "DELETE_MULTIPLE_CHARACTERS";
    public static final String REPLACE_SELECTION = "REPLACE_SELECTION";
    public static final String INSERT_BREAK = "INSERT_BREAK";

    //public static final String BACKSPACE = "BACKSPACE";
    //public static final String DELETE_SELECTION = "DELETE_SELECTION";

    /**
     *      InsertText:
     *          !previousTask.insertAtSelection &&
     *          previousTask.endCursorPos.line === task.startCursorPos.line &&
     *          previousTask.endCursorPos.column === task.startCursorPos.column &&
     *          DocumentUtilities.isSingleCharOrSpaces(previousTask.text) &&
     *          DocumentUtilities.isSingleCharOrSpaces(task.text);
     *
     *      DeleteSelection:
     *          var previousEndCursorPos = previousTask.cursorSelection.anchor;
     *          var taskStartCursorPos = task.cursorSelection;
     *          if (task.reversedSelection) {
     *              taskStartCursorPos = task.cursorSelection.anchor;
     *          }
     *          //If a task text is null it implies a newline
     *          // or some other scenario that should not be merged.
     *          return previousEndCursorPos.line === taskStartCursorPos.line &&
     *              previousEndCursorPos.column === taskStartCursorPos.column &&
     *              previousTask.text &&
     *              task.text &&
     *              DocumentUtilities.isSingleCharOrSpaces(previousTask.text) &&
     *              DocumentUtilities.isSingleCharOrSpaces(task.text);
     *
     *      InsertSingleCharacter:
     *          true for consecutive
     *
     *      InsertMultipleCharacters:
     *          false
     *
     *      InsertBreak:
     *          false
     *
     *      Backspace:
     *          true for consecutive
     *
     *      DeleteSelection:
     *          false
     */

    public Task(String type, int startIndex, String replacedText, String insertedText, boolean mergeWithPrevious) {
        this.type = type;
        this.replacedText = replacedText;
        this.insertedText = insertedText;
        this.startIndex = startIndex;
        this.lengthOfCharsReplaced = replacedText.length();
        this.lengthOfCharsInserted = insertedText.length();
        this.canMergeWithPrevious = mergeWithPrevious;

        if (type.equals(INSERT_SINGLE_CHARACTER) || type.equals(DELETE_SINGLE_CHARACTER)) {
            this.canMergeWithPrevious = true;
        } else {
            this.canMergeWithPrevious = false;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReplacedText() {
        return replacedText;
    }

    public void setReplacedText(String text) {
        this.replacedText = text;
    }

    public String getInsertedText() {
        return insertedText;
    }

    public void setInsertedText(String insertedText) {
        this.insertedText = insertedText;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getLengthOfCharsReplaced() {
        return lengthOfCharsReplaced;
    }

    public void setLengthOfCharsReplaced(int lengthOfCharsReplaced) {
        this.lengthOfCharsReplaced = lengthOfCharsReplaced;
    }

    public int getLengthOfCharsInserted() {
        return lengthOfCharsInserted;
    }

    public void setLengthOfCharsInserted(int lengthOfCharsInserted) {
        this.lengthOfCharsInserted = lengthOfCharsInserted;
    }

    public boolean canMergeWithPrevious() {
        return canMergeWithPrevious;
    }

    public void setCanMergeWithPrevious(boolean canMergeWithPrevious) {
        this.canMergeWithPrevious = canMergeWithPrevious;
    }
}
