package com.chensuworks.nativeeditor.plugins.undo;

import android.test.ActivityTestCase;

public class UndoManagerUnitTest extends ActivityTestCase {

    public void testConstructor() {
        UndoManager undoManager = new UndoManager();

        assertEquals(undoManager.getUndoStack().size(), 0);
        assertEquals(undoManager.getRedoStack().size(), 0);
        assertFalse(undoManager.canUndo());
        assertFalse(undoManager.canRedo());
    }

    public void testCanMergeTasks() {
        UndoManager undoManager = new UndoManager();
        Task task1 = new Task(Task.INSERT_SINGLE_CHARACTER, 0, "", "a", true);
        Task task2 = new Task(Task.INSERT_SINGLE_CHARACTER, 1, "", "b", true);
        Task task3 = new Task(Task.INSERT_BREAK, 2, "", "\n", false);

        assertFalse(undoManager.canMergeTasks(task2, task3, "UNDO"));
        assertFalse(undoManager.canMergeTasks(task3, task2, "UNDO"));
    }
}
