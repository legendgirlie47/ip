package ketchup.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ketchup.tasks.Deadline;
import ketchup.tasks.Event;
import ketchup.tasks.Task;
import ketchup.tasks.ToDo;

/**
 * Unit tests for {@link TaskParser}.
 * <p>
 * Verifies that valid storage lines are correctly reconstructed into
 * concrete {@link Task} objects, and that malformed input results in exceptions.
 */
public class TaskParserTest {

    private TaskParser parser;

    /**
     * Initializes a fresh {@link TaskParser} before each test case.
     */
    @BeforeEach
    public void setUp() {
        parser = new TaskParser();
    }

    /* ================= VALID CASES ================= */

    /**
     * Tests that a valid ToDo line is parsed successfully.
     */
    @Test
    public void parse_validTodo_success() throws Exception {
        String line = "T | 0 | read book";

        Task task = parser.parse(line);

        assertTrue(task instanceof ToDo);
        assertFalse(task.isDone());
        assertEquals("read book", task.getDesc());
    }

    /**
     * Tests that a valid Deadline line is parsed successfully.
     */
    @Test
    public void parse_validDeadline_success() throws Exception {
        String date = LocalDateTime.now()
                .format(Task.getDateFormat());

        String line = "D | 1 | submit report | " + date;

        Task task = parser.parse(line);

        assertTrue(task instanceof Deadline);
        assertTrue(task.isDone());
        assertEquals("submit report", task.getDesc());
    }

    /**
     * Tests that a valid Event line is parsed successfully.
     */
    @Test
    public void parse_validEvent_success() throws Exception {
        String from = LocalDateTime.now()
                .format(Task.getDateFormat());
        String to = LocalDateTime.now().plusHours(2)
                .format(Task.getDateFormat());

        String line = "E | 0 | meeting | " + from + " | " + to;

        Task task = parser.parse(line);

        assertTrue(task instanceof Event);
        assertFalse(task.isDone());
        assertEquals("meeting", task.getDesc());
    }

    /* ================= INVALID CASES ================= */

    /**
     * Tests that parsing a null line throws an exception.
     */
    @Test
    public void parse_nullLine_throwsException() {
        assertThrows(Exception.class, () -> parser.parse(null));
    }

    /**
     * Tests that parsing an empty line throws an exception.
     */
    @Test
    public void parse_emptyLine_throwsException() {
        assertThrows(Exception.class, () -> parser.parse("   "));
    }

    /**
     * Tests that an invalid done flag throws an exception.
     */
    @Test
    public void parse_invalidDoneFlag_throwsException() {
        String line = "T | X | read book";

        assertThrows(Exception.class, () -> parser.parse(line));
    }

    /**
     * Tests that an unknown task type throws an exception.
     */
    @Test
    public void parse_unknownTaskType_throwsException() {
        String line = "Z | 0 | something";

        assertThrows(Exception.class, () -> parser.parse(line));
    }

    /**
     * Tests that a deadline missing its date throws an exception.
     */
    @Test
    public void parse_deadlineMissingDate_throwsException() {
        String line = "D | 0 | homework";

        assertThrows(Exception.class, () -> parser.parse(line));
    }

    /**
     * Tests that an event missing required fields throws an exception.
     */
    @Test
    public void parse_eventMissingFields_throwsException() {
        String line = "E | 0 | meeting | 2026-02-20T1400";

        assertThrows(Exception.class, () -> parser.parse(line));
    }

    /**
     * Tests that an invalid date format throws an exception.
     */
    @Test
    public void parse_invalidDateFormat_throwsException() {
        String line = "D | 0 | homework | invalid-date";

        assertThrows(Exception.class, () -> parser.parse(line));
    }
}
