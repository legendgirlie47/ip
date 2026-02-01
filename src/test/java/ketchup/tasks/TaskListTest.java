package ketchup.tasks;

import ketchup.TaskList;
import ketchup.ToDo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {

    @Test
    public void addAndGetTask_tasksStoredInOrder() {
        TaskList list = new TaskList();

        list.addTask(new ToDo("read book"));
        list.addTask(new ToDo("write report"));

        assertEquals(2, list.getSize());
        assertEquals("read book", list.getTask(0).getDesc());
        assertEquals("write report", list.getTask(1).getDesc());
    }

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

    @Test
    public void deleteTask_indexOutOfBounds_exceptionThrown() {
        TaskList list = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> list.deleteTask(0));
    }
}
