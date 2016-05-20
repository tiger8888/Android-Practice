package com.chensuworks.nativeeditor.plugins.undo;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Stack;

import static org.junit.Assert.*;

public class UndoManagerTest {

    @Test
    public void testConstructor() {
        UndoManager undoManager = new UndoManager();

        assertEquals(undoManager.getUndoStack().size(), 0);
        assertEquals(undoManager.getRedoStack().size(), 0);
        assertFalse(undoManager.canUndo());
        assertFalse(undoManager.canRedo());
    }

    @Test
    public void canMergeTasksTest1() {
        UndoManager undoManager = new UndoManager();

        Task task1 = new Task(Task.INSERT_SINGLE_CHARACTER, 0, "", "a", true);
        Task task2 = new Task(Task.INSERT_SINGLE_CHARACTER, 1, "", "b", true);
        Task task3 = new Task(Task.INSERT_BREAK, 2, "", "\n", false);

        assertTrue(undoManager.canMergeTasks(task1, task2, "UNDO"));
        assertFalse(undoManager.canMergeTasks(task2, task3, "UNDO"));
        assertFalse(undoManager.canMergeTasks(task3, task2, "UNDO"));
    }

    @Test
    public void canMergeTasksTest2() {
        UndoManager undoManager = new UndoManager();

        Task task00 = new Task(Task.INSERT_SINGLE_CHARACTER, 0, "", "f", true);
        Task task01 = new Task(Task.INSERT_SINGLE_CHARACTER, 1, "", "u", true);
        Task task02 = new Task(Task.INSERT_SINGLE_CHARACTER, 2, "", "n", true);
        Task task03 = new Task(Task.INSERT_SINGLE_CHARACTER, 3, "", "c", true);
        Task task04 = new Task(Task.INSERT_SINGLE_CHARACTER, 4, "", "t", true);
        Task task05 = new Task(Task.INSERT_SINGLE_CHARACTER, 5, "", "i", true);
        Task task06 = new Task(Task.INSERT_SINGLE_CHARACTER, 6, "", "o", true);
        Task task07 = new Task(Task.INSERT_SINGLE_CHARACTER, 7, "", "n", true);
        Task task08 = new Task(Task.INSERT_SINGLE_CHARACTER, 8, "", " ", true);
        Task task09 = new Task(Task.INSERT_SINGLE_CHARACTER, 9, "", "f", true);
        Task task10 = new Task(Task.INSERT_SINGLE_CHARACTER, 10, "", "o", true);
        Task task11 = new Task(Task.INSERT_SINGLE_CHARACTER, 11, "", "o", true);
        Task task12 = new Task(Task.INSERT_BREAK, 12, "", "\n", false);
        Task task13 = new Task(Task.INSERT_MULTIPLE_CHARACTERS, 13, "", "    ", false);
        Task task14 = new Task(Task.INSERT_SINGLE_CHARACTER, 17, "", "e", true);
        Task task15 = new Task(Task.INSERT_SINGLE_CHARACTER, 18, "", "n", true);
        Task task16 = new Task(Task.INSERT_SINGLE_CHARACTER, 19, "", "d", true);
        Task task17 = new Task(Task.DELETE_MULTIPLE_CHARACTERS, 13, "    ", "", false);
        Task task18 = new Task(Task.DELETE_SINGLE_CHARACTER, 15, "d", "", true);
        Task task19 = new Task(Task.DELETE_SINGLE_CHARACTER, 14, "n", "", true);
        Task task20 = new Task(Task.DELETE_SINGLE_CHARACTER, 13, "e", "", true);

        assertTrue(undoManager.canMergeTasks(task00, task01, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task01, task02, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task02, task03, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task03, task04, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task04, task05, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task05, task06, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task06, task07, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task07, task08, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task08, task09, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task09, task10, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task10, task11, "UNDO"));
        assertFalse(undoManager.canMergeTasks(task11, task12, "UNDO"));
        assertFalse(undoManager.canMergeTasks(task12, task13, "UNDO"));
        assertFalse(undoManager.canMergeTasks(task13, task14, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task14, task15, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task15, task16, "UNDO"));
        assertFalse(undoManager.canMergeTasks(task16, task17, "UNDO"));
        assertFalse(undoManager.canMergeTasks(task17, task18, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task18, task19, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task19, task20, "UNDO"));

        assertTrue(undoManager.canMergeTasks(task20, task19, "REDO"));
        assertTrue(undoManager.canMergeTasks(task19, task18, "REDO"));
        assertFalse(undoManager.canMergeTasks(task18, task17, "REDO"));
        assertFalse(undoManager.canMergeTasks(task17, task16, "REDO"));
        assertTrue(undoManager.canMergeTasks(task16, task15, "REDO"));
        assertTrue(undoManager.canMergeTasks(task15, task14, "REDO"));
        assertFalse(undoManager.canMergeTasks(task14, task13, "REDO"));
        assertFalse(undoManager.canMergeTasks(task13, task12, "REDO"));
        assertFalse(undoManager.canMergeTasks(task12, task11, "REDO"));
        assertTrue(undoManager.canMergeTasks(task11, task10, "REDO"));
        assertTrue(undoManager.canMergeTasks(task10, task09, "REDO"));
        assertTrue(undoManager.canMergeTasks(task09, task08, "REDO"));
        assertTrue(undoManager.canMergeTasks(task08, task07, "REDO"));
        assertTrue(undoManager.canMergeTasks(task07, task06, "REDO"));
        assertTrue(undoManager.canMergeTasks(task06, task05, "REDO"));
        assertTrue(undoManager.canMergeTasks(task05, task04, "REDO"));
        assertTrue(undoManager.canMergeTasks(task04, task03, "REDO"));
        assertTrue(undoManager.canMergeTasks(task03, task02, "REDO"));
        assertTrue(undoManager.canMergeTasks(task02, task01, "REDO"));
        assertTrue(undoManager.canMergeTasks(task01, task00, "REDO"));
    }

    @Test
    public void canMergeTasksTest3() {
        UndoManager undoManager = new UndoManager();

        Task task0 = new Task(Task.INSERT_MULTIPLE_CHARACTERS, 0, "", "function", false);
        Task task1 = new Task(Task.INSERT_SINGLE_CHARACTER, 8, "", " ", true);
        Task task2 = new Task(Task.INSERT_MULTIPLE_CHARACTERS, 9, "", "foo", false);
        Task task3 = new Task(Task.INSERT_BREAK, 12, "", "\n", false);
        Task task4 = new Task(Task.DELETE_SINGLE_CHARACTER, 13, "\n", "", true);
        Task task5 = new Task(Task.DELETE_SINGLE_CHARACTER, 12, "o", "", true);
        Task task6 = new Task(Task.DELETE_SINGLE_CHARACTER, 11, "o", "", true);
        Task task7 = new Task(Task.DELETE_SINGLE_CHARACTER, 10, "f", "", true);
        Task task8 = new Task(Task.DELETE_MULTIPLE_CHARACTERS, 9, "function ", "", false);

        assertFalse(undoManager.canMergeTasks(task0, task1, "UNDO"));
        assertFalse(undoManager.canMergeTasks(task1, task2, "UNDO"));
        assertFalse(undoManager.canMergeTasks(task2, task3, "UNDO"));
        assertFalse(undoManager.canMergeTasks(task3, task4, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task4, task5, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task5, task6, "UNDO"));
        assertTrue(undoManager.canMergeTasks(task6, task7, "UNDO"));
        assertFalse(undoManager.canMergeTasks(task7, task8, "UNDO"));

        assertFalse(undoManager.canMergeTasks(task8, task7, "REDO"));
        assertTrue(undoManager.canMergeTasks(task7, task6, "REDO"));
        assertTrue(undoManager.canMergeTasks(task6, task5, "REDO"));
        assertTrue(undoManager.canMergeTasks(task5, task4, "REDO"));
        assertFalse(undoManager.canMergeTasks(task4, task3, "REDO"));
        assertFalse(undoManager.canMergeTasks(task3, task2, "REDO"));
        assertFalse(undoManager.canMergeTasks(task2, task1, "REDO"));
        assertFalse(undoManager.canMergeTasks(task1, task0, "REDO"));
    }

    @Test
    public void testGetLastTaskCaseNull() {
        UndoManager undoManager = new UndoManager();

        assertEquals(undoManager.getLastTaskFromUndoStack(), null);
    }

    @Test
    public void testGetLastTaskCase1() {
        UndoManager undoManager = new UndoManager();

        Stack<LinkedList<Task>> stack = new Stack<LinkedList<Task>>();
        LinkedList<Task> list0 = new LinkedList<Task>();
        Task task00 = new Task(Task.INSERT_SINGLE_CHARACTER, 0, "", "f", true);

        list0.add(task00);
        stack.add(list0);
        undoManager.setUndoStack(stack);

        assertEquals(undoManager.getLastTaskFromUndoStack(), task00);
    }

    @Test
    public void testGetLastTaskCase2() {
        UndoManager undoManager = new UndoManager();

        Stack<LinkedList<Task>> stack = new Stack<LinkedList<Task>>();
        LinkedList<Task> list0 = new LinkedList<Task>();
        LinkedList<Task> list1 = new LinkedList<Task>();

        Task task00 = new Task(Task.INSERT_SINGLE_CHARACTER, 0, "", "f", true);
        Task task01 = new Task(Task.INSERT_BREAK, 1, "", "\n", false);

        list0.add(task00);
        list1.add(task01);

        stack.add(list0);
        stack.add(list1);

        undoManager.setUndoStack(stack);

        assertEquals(undoManager.getLastTaskFromUndoStack(), task01);
    }

    @Test
    public void testGetLastTaskCase3() {
        UndoManager undoManager = new UndoManager();

        Stack<LinkedList<Task>> stack = new Stack<LinkedList<Task>>();

        LinkedList<Task> list0 = new LinkedList<Task>();
        LinkedList<Task> list1 = new LinkedList<Task>();
        LinkedList<Task> list2 = new LinkedList<Task>();
        LinkedList<Task> list3 = new LinkedList<Task>();
        LinkedList<Task> list4 = new LinkedList<Task>();

        Task task00 = new Task(Task.INSERT_SINGLE_CHARACTER, 0, "", "f", true);
        Task task01 = new Task(Task.INSERT_SINGLE_CHARACTER, 1, "", "u", true);
        Task task02 = new Task(Task.INSERT_SINGLE_CHARACTER, 2, "", "n", true);
        Task task03 = new Task(Task.INSERT_SINGLE_CHARACTER, 3, "", "c", true);
        Task task04 = new Task(Task.INSERT_SINGLE_CHARACTER, 4, "", "t", true);
        Task task05 = new Task(Task.INSERT_SINGLE_CHARACTER, 5, "", "i", true);
        Task task06 = new Task(Task.INSERT_SINGLE_CHARACTER, 6, "", "o", true);
        Task task07 = new Task(Task.INSERT_SINGLE_CHARACTER, 7, "", "n", true);
        Task task08 = new Task(Task.INSERT_SINGLE_CHARACTER, 8, "", " ", true);
        Task task09 = new Task(Task.INSERT_SINGLE_CHARACTER, 9, "", "f", true);
        Task task10 = new Task(Task.INSERT_SINGLE_CHARACTER, 10, "", "o", true);
        Task task11 = new Task(Task.INSERT_SINGLE_CHARACTER, 11, "", "o", true);
        Task task12 = new Task(Task.INSERT_BREAK, 12, "", "\n", false);
        Task task13 = new Task(Task.INSERT_MULTIPLE_CHARACTERS, 13, "", "    ", false);
        Task task14 = new Task(Task.INSERT_SINGLE_CHARACTER, 17, "", "e", true);
        Task task15 = new Task(Task.INSERT_SINGLE_CHARACTER, 18, "", "n", true);
        Task task16 = new Task(Task.INSERT_SINGLE_CHARACTER, 19, "", "d", true);
        Task task17 = new Task(Task.DELETE_MULTIPLE_CHARACTERS, 13, "    ", "", false);

        list0.add(task00);
        list0.add(task01);
        list0.add(task02);
        list0.add(task03);
        list0.add(task04);
        list0.add(task05);
        list0.add(task06);
        list0.add(task07);
        list0.add(task08);
        list0.add(task09);
        list0.add(task10);
        list0.add(task11);

        list1.add(task12);

        list2.add(task13);

        list3.add(task14);
        list3.add(task15);
        list3.add(task16);

        list4.add(task17);

        stack.push(list0);
        stack.push(list1);
        stack.push(list2);
        stack.push(list3);
        stack.push(list4);

        undoManager.setUndoStack(stack);

        assertEquals(undoManager.getLastTaskFromUndoStack(), task17);
    }

    @Test
    public void testAddTask() {
        UndoManager undoManager = new UndoManager();

        Task task00 = new Task(Task.INSERT_SINGLE_CHARACTER, 0, "", "f", true);
        Task task01 = new Task(Task.INSERT_SINGLE_CHARACTER, 1, "", "u", true);
        Task task02 = new Task(Task.INSERT_SINGLE_CHARACTER, 2, "", "n", true);
        Task task03 = new Task(Task.INSERT_SINGLE_CHARACTER, 3, "", "c", true);
        Task task04 = new Task(Task.INSERT_SINGLE_CHARACTER, 4, "", "t", true);
        Task task05 = new Task(Task.INSERT_SINGLE_CHARACTER, 5, "", "i", true);
        Task task06 = new Task(Task.INSERT_SINGLE_CHARACTER, 6, "", "o", true);
        Task task07 = new Task(Task.INSERT_SINGLE_CHARACTER, 7, "", "n", true);
        Task task08 = new Task(Task.INSERT_SINGLE_CHARACTER, 8, "", " ", true);
        Task task09 = new Task(Task.INSERT_SINGLE_CHARACTER, 9, "", "f", true);
        Task task10 = new Task(Task.INSERT_SINGLE_CHARACTER, 10, "", "o", true);
        Task task11 = new Task(Task.INSERT_SINGLE_CHARACTER, 11, "", "o", true);
        Task task12 = new Task(Task.INSERT_BREAK, 12, "", "\n", false);
        Task task13 = new Task(Task.INSERT_MULTIPLE_CHARACTERS, 13, "", "    ", false);
        Task task14 = new Task(Task.INSERT_SINGLE_CHARACTER, 17, "", "e", true);
        Task task15 = new Task(Task.INSERT_SINGLE_CHARACTER, 18, "", "n", true);
        Task task16 = new Task(Task.INSERT_SINGLE_CHARACTER, 19, "", "d", true);
        Task task17 = new Task(Task.DELETE_MULTIPLE_CHARACTERS, 13, "    ", "", false);

        undoManager.addTask(task00);
        undoManager.addTask(task01);
        undoManager.addTask(task02);
        undoManager.addTask(task03);
        undoManager.addTask(task04);
        undoManager.addTask(task05);
        undoManager.addTask(task06);
        undoManager.addTask(task07);
        undoManager.addTask(task08);
        undoManager.addTask(task09);
        undoManager.addTask(task10);
        undoManager.addTask(task11);
        undoManager.addTask(task12);
        undoManager.addTask(task13);
        undoManager.addTask(task14);
        undoManager.addTask(task15);
        undoManager.addTask(task16);
        undoManager.addTask(task17);

        assertEquals(undoManager.getUndoStack().size(), 5);

        Stack<LinkedList<Task>> undoStack = undoManager.getUndoStack();
        assertEquals(undoStack.pop().size(), 1);
        assertEquals(undoStack.pop().size(), 3);
        assertEquals(undoStack.pop().size(), 1);
        assertEquals(undoStack.pop().size(), 1);
        assertEquals(undoStack.pop().size(), 12);
    }

    @Test
    public void testUndoRedo() {
        UndoManager undoManager = new UndoManager();

        Task task00 = new Task(Task.INSERT_SINGLE_CHARACTER, 0, "", "f", true);
        Task task01 = new Task(Task.INSERT_SINGLE_CHARACTER, 1, "", "u", true);
        Task task02 = new Task(Task.INSERT_SINGLE_CHARACTER, 2, "", "n", true);
        Task task03 = new Task(Task.INSERT_SINGLE_CHARACTER, 3, "", "c", true);
        Task task04 = new Task(Task.INSERT_SINGLE_CHARACTER, 4, "", "t", true);
        Task task05 = new Task(Task.INSERT_SINGLE_CHARACTER, 5, "", "i", true);
        Task task06 = new Task(Task.INSERT_SINGLE_CHARACTER, 6, "", "o", true);
        Task task07 = new Task(Task.INSERT_SINGLE_CHARACTER, 7, "", "n", true);
        Task task08 = new Task(Task.INSERT_SINGLE_CHARACTER, 8, "", " ", true);
        Task task09 = new Task(Task.INSERT_SINGLE_CHARACTER, 9, "", "f", true);
        Task task10 = new Task(Task.INSERT_SINGLE_CHARACTER, 10, "", "o", true);
        Task task11 = new Task(Task.INSERT_SINGLE_CHARACTER, 11, "", "o", true);
        Task task12 = new Task(Task.INSERT_BREAK, 12, "", "\n", false);
        Task task13 = new Task(Task.INSERT_MULTIPLE_CHARACTERS, 13, "", "    ", false);
        Task task14 = new Task(Task.INSERT_SINGLE_CHARACTER, 17, "", "e", true);
        Task task15 = new Task(Task.INSERT_SINGLE_CHARACTER, 18, "", "n", true);
        Task task16 = new Task(Task.INSERT_SINGLE_CHARACTER, 19, "", "d", true);
        Task task17 = new Task(Task.DELETE_MULTIPLE_CHARACTERS, 13, "    ", "", false);

        undoManager.addTask(task00);
        undoManager.addTask(task01);
        undoManager.addTask(task02);
        undoManager.addTask(task03);
        undoManager.addTask(task04);
        undoManager.addTask(task05);
        undoManager.addTask(task06);
        undoManager.addTask(task07);
        undoManager.addTask(task08);
        undoManager.addTask(task09);
        undoManager.addTask(task10);
        undoManager.addTask(task11);
        undoManager.addTask(task12);
        undoManager.addTask(task13);
        undoManager.addTask(task14);
        undoManager.addTask(task15);
        undoManager.addTask(task16);
        undoManager.addTask(task17);

        assertEquals(undoManager.getUndoStack().size(), 5);

        // Undo task17
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 4);
        assertEquals(undoManager.getRedoStack().size(), 1);

        // Undo task16
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 4);
        assertEquals(undoManager.getRedoStack().size(), 2);

        // Undo task15
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 4);
        assertEquals(undoManager.getRedoStack().size(), 2);

        // Undo task14
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 3);
        assertEquals(undoManager.getRedoStack().size(), 2);

        // Undo task13
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 2);
        assertEquals(undoManager.getRedoStack().size(), 3);

        // Undo task12
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 1);
        assertEquals(undoManager.getRedoStack().size(), 4);

        // Undo task11
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 1);
        assertEquals(undoManager.getRedoStack().size(), 5);

        // Undo task10
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 1);
        assertEquals(undoManager.getRedoStack().size(), 5);

        // Undo task09
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 1);
        assertEquals(undoManager.getRedoStack().size(), 5);

        // Undo task08
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 1);
        assertEquals(undoManager.getRedoStack().size(), 5);

        // Undo task07
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 1);
        assertEquals(undoManager.getRedoStack().size(), 5);

        // Undo task06
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 1);
        assertEquals(undoManager.getRedoStack().size(), 5);

        // Undo task05
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 1);
        assertEquals(undoManager.getRedoStack().size(), 5);

        // Undo task04
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 1);
        assertEquals(undoManager.getRedoStack().size(), 5);

        // Undo task03
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 1);
        assertEquals(undoManager.getRedoStack().size(), 5);

        // Undo task02
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 1);
        assertEquals(undoManager.getRedoStack().size(), 5);

        // Undo task01
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 1);
        assertEquals(undoManager.getRedoStack().size(), 5);

        // Undo task00
        assertNotNull(undoManager.undoSingleTask());
        assertEquals(undoManager.getUndoStack().size(), 0);
        assertEquals(undoManager.getRedoStack().size(), 5);

        // Following Undo should return null
        assertNull(undoManager.undoSingleTask());
        assertNull(undoManager.undoSingleTask());
    }

    @Test
    public void testStackLimit() {
        UndoManager undoManager = new UndoManager();

        LinkedList<Task> tasks = new LinkedList<Task>();
        // Keep entering breaks for STACK_SIZE_LIMIT times.
        for (int i = 0; i < UndoManager.STACK_SIZE_LIMIT; i++) {
            Task task = new Task(Task.INSERT_BREAK, i, "", "\n", false);

            tasks.add(task);
            undoManager.addTask(task);
        }
        // Undo stack should be full now.
        assertEquals(undoManager.getUndoStack().size(), UndoManager.STACK_SIZE_LIMIT);

        // Add an extra task and undo stack should remove the bottom task, while keeping the size unchanged.
        Task extraTask = new Task(Task.INSERT_BREAK, UndoManager.STACK_SIZE_LIMIT, "", "\n", false);
        undoManager.addTask(extraTask);
        Stack<LinkedList<Task>> undoStack = undoManager.getUndoStack();
        // Undo stack should still have size of STACK_SIZE_LIMIT.
        assertEquals(undoStack.size(), UndoManager.STACK_SIZE_LIMIT);
        // The top of undo stack should be the newly added extraTask.
        assertEquals(undoStack.get(undoStack.size() - 1).size(), 1);
        assertEquals(undoStack.get(undoStack.size() - 1).get(0), extraTask);


        // Add another extra task and undo stack should remove the bottom task, while keeping the size unchanged.
        extraTask = new Task(Task.INSERT_BREAK, UndoManager.STACK_SIZE_LIMIT + 1, "", "\n", false);
        undoManager.addTask(extraTask);
        undoStack = undoManager.getUndoStack();
        // Undo stack should still have size of STACK_SIZE_LIMIT.
        assertEquals(undoStack.size(), UndoManager.STACK_SIZE_LIMIT);
        // The top of undo stack should be the newly added extraTask.
        assertEquals(undoStack.get(undoStack.size() - 1).size(), 1);
        assertEquals(undoStack.get(undoStack.size() - 1).get(0), extraTask);


        // Add another extra task and undo stack should remove the bottom task, while keeping the size unchanged.
        extraTask = new Task(Task.INSERT_BREAK, UndoManager.STACK_SIZE_LIMIT + 1, "", "\n", false);
        undoManager.addTask(extraTask);
        undoStack = undoManager.getUndoStack();
        // Undo stack should still have size of STACK_SIZE_LIMIT.
        assertEquals(undoStack.size(), UndoManager.STACK_SIZE_LIMIT);
        // The top of undo stack should be the newly added extraTask.
        assertEquals(undoStack.get(undoStack.size() - 1).size(), 1);
        assertEquals(undoStack.get(undoStack.size() - 1).get(0), extraTask);
    }
}
