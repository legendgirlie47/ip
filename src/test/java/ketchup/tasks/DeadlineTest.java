package ketchup.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Deadline}.
 * Verifies constructor behavior, formatting,
 * and done status handling.
 */
public class DeadlineTest {

    private Deadline deadline;
    private LocalDateTime testTime;

    /**
     * Initializes a Deadline instance before each test.
     */
    @BeforeEach
    public void setUp() {
        testTime = LocalDateTime.of(2026, 2, 20, 18, 0);
        deadline = new Deadline("submit report", testTime);
    }

    /**
     * Tests that the deadline date-time is stored correctly.
     */
    @Test
    public void constructor_storesDeadlineCorrectly() {
        assertEquals(testTime, deadline.getDeadline());
    }

    /**
     * Tests toFileString() when task is not done.
     */
    @Test
    public void toFileString_notDone_formatsCorrectly() {
        String expected = "D | 0 | submit report | "
                + testTime.format(Task.getDateFormat());

        assertEquals(expected, deadline.toFileString());
    }

    /**
     * Tests toFileString() when task is marked done.
     */
    @Test
    public void toFileString_done_formatsCorrectly() {
        deadline.markDone();

        String expected = "D | 1 | submit report | "
                + testTime.format(Task.getDateFormat());

        assertEquals(expected, deadline.toFileString());
    }

    /**
     * Tests toString() formatting when not done.
     */
    @Test
    public void toString_notDone_containsCorrectFormat() {
        String output = deadline.toString();

        assertTrue(output.contains("[D]"));
        assertTrue(output.contains("submit report"));
        assertTrue(output.contains("by:"));
        assertTrue(output.contains(testTime.format(Task.getDateFormat())));
    }

    /**
     * Tests toString() formatting when done.
     */
    @Test
    public void toString_done_showsDoneIndicator() {
        deadline.markDone();

        String output = deadline.toString();

        assertTrue(output.contains("[D]"));
        assertTrue(output.contains("submit report"));
    }
}
