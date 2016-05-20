package com.chensuworks.nativeeditor.plugins.undo;

import java.util.LinkedList;
import java.util.Stack;

public class UndoManager {

    public static final int STACK_SIZE_LIMIT = 5;

    private Stack<LinkedList<Task>> undoStack;
    private Stack<LinkedList<Task>> redoStack;

    public Stack<LinkedList<Task>> getUndoStack() {
        return undoStack;
    }

    public Stack<LinkedList<Task>> getRedoStack() {
        return redoStack;
    }

    public void setUndoStack(Stack<LinkedList<Task>> undoStack) {
        this.undoStack = undoStack;
    }

    public void setRedoStack(Stack<LinkedList<Task>> redoStack) {
        this.redoStack = redoStack;
    }

    public void clearRedoStack() {
        this.redoStack.clear();
    }

    public UndoManager() {
        this.undoStack = new Stack<LinkedList<Task>>();
        this.redoStack = new Stack<LinkedList<Task>>();
    }

    public boolean canUndo() {
        return !undoStack.empty();
    }

    public boolean canRedo() {
        return !redoStack.empty();
    }

    public LinkedList<Task> undo() {
        if (canUndo()) {
            LinkedList<Task> list = undoStack.pop();
            redoStack.push(list);

            return list;
        }

        return null;
    }

    public LinkedList<Task> redo() {
        if (canRedo()) {
            LinkedList<Task> list = redoStack.pop();
            undoStack.push(list);

            return list;
        }

        return null;
    }

    /**
     * If merged tasks cannot be operated in one action.
     * @return
     */
    public Task undoSingleTask() {
        if (canUndo()) {
            /**
             * Get the latest task from undoStack.
             *      If the top list of the stack has size 1, the list needs to be popped out of the stack.
             *      If the top list of the stack has more than 1 tasks, simply remove the last task from the top list.
             */
            LinkedList<Task> list = undoStack.peek();
            /*
            if (list.isEmpty()) {
                // Lists in the stack should never be empty.
                throw new Exception();
            }
            */

            Task task;
            if (list.size() == 1) {
                task = undoStack.pop().removeLast();

            } else {
                task = list.removeLast();
            }

            /**
             * Push the task to the redoStack.
             *      If the task can merge with the last task, merge to the top list.
             *      if the task cannot merge with the last task, add it to a new list and push to the redoStack.
             */
            if (canMergeTasks(getLastTaskFromRedoStack(), task, "REDO")) {
                LinkedList<Task> topList = redoStack.peek();
                topList.add(task);

            } else {
                LinkedList<Task> newList = new LinkedList<Task>();
                newList.add(task);
                redoStack.push(newList);
            }

            return task;
        }

        return null;
    }

    /**
     * If merged tasks cannot be operated in one action.
     * @return
     */
    public Task redoSingleTask() {
        if (canRedo()) {
            /**
             * Get the latest task from redoStack.
             *      If the top list of the stack has size 1, the list needs to be popped out of the stack.
             *      If the top list of the stack has more than 1 tasks, simply remove the last task from the top list.
             */
            LinkedList<Task> list = redoStack.peek();
            Task task;

            if (list.size() == 1) {
                task = redoStack.pop().removeLast();

            } else {
                task = list.removeLast();
            }


            /**
             * Push the task to the undoStack.
             *      If the task can merge with the last task, merge to the top list.
             *      if the task cannot merge with the last task, add it to a new list and push to the redoStack.
             */
            if (canMergeTasks(getLastTaskFromUndoStack(), task, "UNDO")) { // true means undoStack
                LinkedList<Task> topList = undoStack.peek();
                topList.add(task);

            } else {
                LinkedList<Task> newList = new LinkedList<Task>();
                newList.add(task);
                undoStack.push(newList);
            }

            return task;
        }

        return null;
    }

    public void addTask(Task task) {
        if (undoStack.size() == 0) {
            LinkedList<Task> newList = new LinkedList<Task>();
            newList.add(task);
            undoStack.push(newList);

        } else {
            if (canMergeTasks(getLastTaskFromUndoStack(), task, "UNDO")) {
                LinkedList<Task> lastList = undoStack.peek();
                lastList.add(task);

            } else {
                LinkedList<Task> newList = new LinkedList<Task>();
                newList.add(task);
                undoStack.push(newList);

                if (undoStack.size() > STACK_SIZE_LIMIT) {
                    undoStack.remove(0);
                }
            }
        }

    }

    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }

    public boolean canMergeTasks(Task lastTask, Task currentTask, String stackName) {
        // The currentTask could have the attribute that it cannot be merged based on its type.
        if (!currentTask.canMergeWithPrevious()) {
            return false;
        }

        // Two tasks cannot be merged if their types are different.
        if (!currentTask.getType().equals(lastTask.getType())) {
            return false;
        }

        if (currentTask.getType().equals(Task.INSERT_SINGLE_CHARACTER)) {
            // For tasks going to undoStack, current task's index should be equal to last task's index + 1 so that they can merge
            if (stackName.equals("UNDO") && lastTask.getStartIndex() + 1 != currentTask.getStartIndex()) {
                return false;
            }
            // For tasks going to redoStack, current task's index should be equal to last task's index - 1 so that they can merge
            if (stackName.equals("REDO") && lastTask.getStartIndex() - 1 != currentTask.getStartIndex()) {
                return false;
            }
        }

        if (currentTask.getType().equals(Task.DELETE_SINGLE_CHARACTER)) {
            // return false if lastTask & currentTask are not consecutive
            if (stackName.equals("UNDO") && lastTask.getStartIndex() - lastTask.getLengthOfCharsReplaced() != currentTask.getStartIndex()) {
                return false;
            }
            if (stackName.equals("REDO") && lastTask.getStartIndex() + lastTask.getLengthOfCharsReplaced() != currentTask.getStartIndex()) {
                return false;
            }
        }

        return true;
    }

    public Task getLastTaskFromUndoStack() {
        if (undoStack.size() == 0) {
            return null;
        }

        LinkedList<Task> list = undoStack.peek();
        return list.getLast();
    }

    public Task getLastTaskFromRedoStack() {
        if (redoStack.size() == 0) {
            return null;
        }

        LinkedList<Task> list = redoStack.peek();
        return list.getLast();
    }

}
