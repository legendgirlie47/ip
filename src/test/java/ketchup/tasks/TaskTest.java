package ketchup.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Task}.
 * Verifies state transitions, formatting,
 * and description storage.
 */
public class TaskTest {

    private Task task;

    /**
     * Initializes a Task before each test.
     */
    @BeforeEach
    public void setUp() {
        task = new Task("buy milk");
    }

    /**
     * Tests that a new task is not done by default.
     */
    @Test
    public void constructor_defaultNotDone() {
        assertFalse(task.isDone());
    }

    /**
     * Tests that description is stored correctly.
     */
    @Test
    public void constructor_storesDescription() {
        assertEquals("buy milk", task.getDesc());
    }

    /**
     * Tests markDone() updates state correctly.
     */
    @Test
    public void markDone_setsDoneTrue() {
        task.markDone();
        assertTrue(task.isDone());
    }

    /**
     * Tests markUndone() updates state correctly.
     */
    @Test
    public void markUndone_setsDoneFalse() {
        task.markDone();
        task.markUndone();
        assertFalse(task.isDone());
    }

    /**
     * Tests toFileString() when task is not done.
     */
    @Test
    public void toFileString_notDone_formatsCorrectly() {
        String expected = "T | 0 | buy milk";
        assertEquals(expected, task.toFileString());
    }

    /**
     * Tests toFileString() when task is marked done.
     */
    @Test
    public void toFileString_done_formatsCorrectly() {
        task.markDone();

        String expected = "T | 1 | buy milk";
        assertEquals(expected, task.toFileString());
    }

    /**
     * Tests toString() when task is not done.
     */
    @Test
    public void toString_notDone_formatsCorrectly() {
        assertEquals("[ ] buy milk", task.toString());
    }

    /**
     * Tests toString() when task is marked done.
     */
    @Test
    public void toString_done_formatsCorrectly() {
        task.markDone();
        assertEquals("[X] buy milk", task.toString());
    }

    /**
     * Tests that getDateFormat() returns the correct formatter pattern.
     */
    @Test
    public void getDateFormat_formatsCorrectly() {
        DateTimeFormatter formatter = Task.getDateFormat();

        String formatted = java.time.LocalDateTime
                .of(2026, 2, 20, 18, 0)
                .format(formatter);

        assertEquals("2026-02-20 1800", formatted);
    }
}
