package ketchup.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Event}.
 * Verifies constructor behavior, getters,
 * file formatting, display formatting,
 * and done state handling.
 */
public class EventTest {

    private Event event;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    /**
     * Initializes an Event instance before each test.
     */
    @BeforeEach
    public void setUp() {
        startTime = LocalDateTime.of(2026, 2, 20, 14, 0);
        endTime = LocalDateTime.of(2026, 2, 20, 16, 0);
        event = new Event("team meeting", startTime, endTime);
    }

    /**
     * Tests that start and end times are stored correctly.
     */
    @Test
    public void constructor_storesTimesCorrectly() {
        assertEquals(startTime, event.getStart());
        assertEquals(endTime, event.getEnd());
    }

    /**
     * Tests toFileString() when task is not done.
     */
    @Test
    public void toFileString_notDone_formatsCorrectly() {
        String expected = "E | 0 | team meeting | "
                + startTime.format(Task.getDateFormat())
                + " | "
                + endTime.format(Task.getDateFormat());

        assertEquals(expected, event.toFileString());
    }

    /**
     * Tests toFileString() when task is marked done.
     */
    @Test
    public void toFileString_done_formatsCorrectly() {
        event.markDone();

        String expected = "E | 1 | team meeting | "
                + startTime.format(Task.getDateFormat())
                + " | "
                + endTime.format(Task.getDateFormat());

        assertEquals(expected, event.toFileString());
    }

    /**
     * Tests toString() formatting when not done.
     */
    @Test
    public void toString_notDone_containsCorrectFormat() {
        String output = event.toString();

        assertTrue(output.contains("[E]"));
        assertTrue(output.contains("team meeting"));
        assertTrue(output.contains("from:"));
        assertTrue(output.contains("to:"));
        assertTrue(output.contains(startTime.format(Task.getDateFormat())));
        assertTrue(output.contains(endTime.format(Task.getDateFormat())));
    }

    /**
     * Tests toString() when task is marked done.
     */
    @Test
    public void toString_done_showsDoneIndicator() {
        event.markDone();

        String output = event.toString();

        assertTrue(output.contains("[E]"));
        assertTrue(output.contains("team meeting"));
    }
}
