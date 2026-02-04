package ketchup.parser;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import ketchup.tasks.Deadline;
import ketchup.tasks.Task;
import ketchup.tasks.ToDo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link TaskParser}.
 * Verifies correct parsing behavior for valid and invalid task inputs.
 */
public class TaskParserTest {

    /**
     * Tests that a todo task with a done flag of 1
     * is parsed correctly and marked as done.
     *
     * @throws Exception if parsing fails unexpectedly
     */
    @Test
    public void parse_todoDoneFlag_taskMarkedDone() throws Exception {
        TaskParser parser = new TaskParser();

        Task t = parser.parse("T | 1 | read book");

        assertInstanceOf(ToDo.class, t);
        assertTrue(t.isDone());
        assertEquals("read book", t.getDesc());
    }

    /**
     * Tests that a deadline task is parsed correctly,
     * including its {@link LocalDateTime} value.
     *
     * @throws Exception if parsing fails unexpectedly
     */
    @Test
    public void parse_deadline_parsesLocalDateTime() throws Exception {
        TaskParser parser = new TaskParser();

        Task t = parser.parse("D | 0 | return book | 2019-12-02 1800");

        assertInstanceOf(Deadline.class, t);
        Deadline d = (Deadline) t;

        assertFalse(d.isDone());
        assertEquals("return book", d.getDesc());
        assertEquals(
                LocalDateTime.of(2019, 12, 2, 18, 0),
                d.getDeadline()
        );
    }

    /**
     * Tests that parsing a corrupted input line
     * results in an exception being thrown.
     */
    @Test
    public void parse_corruptedLine_exceptionThrown() {
        TaskParser parser = new TaskParser();

        assertThrows(Exception.class, () -> parser.parse("D | 1 | missing datetime"));
    }
}
