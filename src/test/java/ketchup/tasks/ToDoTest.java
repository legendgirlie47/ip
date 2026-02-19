package ketchup.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ToDo}.
 * Verifies description storage, display formatting,
 * and done state behavior.
 */
public class ToDoTest {

    private ToDo todo;

    /**
     * Initializes a ToDo instance before each test.
     */
    @BeforeEach
    public void setUp() {
        todo = new ToDo("read book");
    }

    /**
     * Tests that description is stored correctly.
     */
    @Test
    public void constructor_storesDescriptionCorrectly() {
        assertEquals("read book", todo.getDesc());
    }

    /**
     * Tests toString() formatting when task is not done.
     */
    @Test
    public void toString_notDone_formatsCorrectly() {
        String output = todo.toString();

        assertTrue(output.contains("[T]"));
        assertTrue(output.contains("read book"));
    }

    /**
     * Tests toString() formatting when task is marked done.
     */
    @Test
    public void toString_done_showsDoneIndicator() {
        todo.markDone();

        String output = todo.toString();

        assertTrue(output.contains("[T]"));
        assertTrue(output.contains("read book"));
    }

    /**
     * Tests marking task as done.
     */
    @Test
    public void markDone_setsDoneTrue() {
        todo.markDone();
        assertTrue(todo.isDone());
    }
}
