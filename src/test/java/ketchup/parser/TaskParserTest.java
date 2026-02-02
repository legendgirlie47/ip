package ketchup.parser;

import ketchup.tasks.Deadline;
import ketchup.tasks.Task;
import ketchup.tasks.ToDo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskParserTest {

    @Test
    public void parse_todoDoneFlag_taskMarkedDone() throws Exception {
        TaskParser parser = new TaskParser();

        Task t = parser.parse("T | 1 | read book");

        assertInstanceOf(ToDo.class, t);
        assertTrue(t.isDone());
        assertEquals("read book", t.getDesc());
    }

    @Test
    public void parse_deadline_parsesLocalDateTime() throws Exception {
        TaskParser parser = new TaskParser();

        Task t = parser.parse("D | 0 | return book | 2019-12-02 1800");

        assertInstanceOf(Deadline.class, t);
        Deadline d = (Deadline) t;

        assertFalse(d.isDone());
        assertEquals("return book", d.getDesc());
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0), d.getDeadline());
    }

    @Test
    public void parse_corruptedLine_exceptionThrown() {
        TaskParser parser = new TaskParser();

        assertThrows(Exception.class, () -> parser.parse("D | 1 | missing datetime"));
    }
}
