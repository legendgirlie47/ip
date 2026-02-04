package ketchup.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link TaskList}.
 * Verifies basic task list operations such as add, get, and delete.
 */
public class TaskListTest {

    /**
     * Tests that tasks added to the list are stored in order
     * and can be retrieved correctly by index.
     */
    @Test
    public void addAndGetTask_tasksStoredInOrder() {
        TaskList list = new TaskList();

        list.addTask(new ToDo("read book"));
        list.addTask(new ToDo("write report"));

        assertEquals(2, list.getSize());
        assertEquals("read book", list.getTask(0).getDesc());
        assertEquals("write report", list.getTask(1).getDesc());
    }

    /**
     * Tests that deleting a task removes it and shifts subsequent tasks left.
     */
    @Test
    public void deleteTask_removedAndShiftsLeft() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("A"));
        list.addTask(new ToDo("B"));
        list.addTask(new ToDo("C"));

        list.deleteTask(1); // delete "B"

        assertEquals(2, list.getSize());
        assertEquals("A", list.getTask(0).getDesc());
        assertEquals("C", list.getTask(1).getDesc()); // shifted left
    }

    /**
     * Tests that deleting a task at an invalid index throws an exception.
     */
    @Test
    public void deleteTask_indexOutOfBounds_exceptionThrown() {
        TaskList list = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> list.deleteTask(0));
    }
}
