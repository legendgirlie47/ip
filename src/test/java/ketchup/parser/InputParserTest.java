package ketchup.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ketchup.KetchupResult;
import ketchup.tasks.TaskList;
import ketchup.ui.Ui;

/**
 * Unit tests for {@link InputParser}.
 * Verifies correct command handling, task mutations,
 * error handling, and exit behavior.
 */
public class InputParserTest {

    private InputParser parser;
    private TaskList tasks;

    /**
     * Initializes a fresh InputParser and TaskList before each test.
     */
    @BeforeEach
    public void setUp() {
        Ui ui = new Ui();
        parser = new InputParser(ui);
        tasks = new TaskList();
    }

    /* ================= BASIC COMMANDS ================= */

    @Test
    public void handle_byeCommand_returnsExitTrue() {
        KetchupResult result = parser.handle("bye", tasks);

        assertTrue(result.isShouldExit());
        assertTrue(result.getResponse().length() > 0);
    }

    @Test
    public void handle_helpCommand_returnsExitFalse() {
        KetchupResult result = parser.handle("help", tasks);

        assertFalse(result.isShouldExit());
        assertTrue(result.getResponse().length() > 0);
    }

    @Test
    public void handle_listCommand_emptyList() {
        KetchupResult result = parser.handle("list", tasks);

        assertFalse(result.isShouldExit());
    }

    /* ================= TODO ================= */

    @Test
    public void handle_validTodo_addsTask() {
        parser.handle("todo read book", tasks);

        assertEquals(1, tasks.getSize());
    }

    @Test
    public void handle_emptyTodo_returnsError() {
        KetchupResult result = parser.handle("todo   ", tasks);

        assertEquals(0, tasks.getSize());
        assertFalse(result.isShouldExit());
        assertTrue(result.getResponse().length() > 0);
    }

    /* ================= DEADLINE ================= */

    @Test
    public void handle_validDeadline_addsTask() {
        String date = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));

        parser.handle("deadline submit /by " + date, tasks);

        assertEquals(1, tasks.getSize());
    }

    @Test
    public void handle_invalidDeadline_returnsError() {
        KetchupResult result =
                parser.handle("deadline submit /by invalid-date", tasks);

        assertEquals(0, tasks.getSize());
        assertFalse(result.isShouldExit());
        assertTrue(result.getResponse().contains("yyyy-MM-dd"));
    }

    /* ================= EVENT ================= */

    @Test
    public void handle_validEvent_addsTask() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        String from = LocalDateTime.now().format(formatter);
        String to = LocalDateTime.now().plusHours(1).format(formatter);

        parser.handle("event meeting /from " + from + " /to " + to, tasks);

        assertEquals(1, tasks.getSize());
    }

    /* ================= MARK / UNMARK ================= */

    @Test
    public void handleMark_validIndex_marksTask() {
        parser.handle("todo read", tasks);
        parser.handle("mark 1", tasks);

        assertTrue(tasks.getTask(0).isDone());
    }

    @Test
    public void handleUnmark_validIndex_unmarksTask() {
        parser.handle("todo read", tasks);
        parser.handle("mark 1", tasks);
        parser.handle("unmark 1", tasks);

        assertFalse(tasks.getTask(0).isDone());
    }

    /* ================= DELETE ================= */

    @Test
    public void handleDelete_validIndex_removesTask() {
        parser.handle("todo read", tasks);
        parser.handle("delete 1", tasks);

        assertEquals(0, tasks.getSize());
    }

    /* ================= FIND ================= */

    @Test
    public void handle_find_existingTask() {
        parser.handle("todo read book", tasks);
        KetchupResult result = parser.handle("find read", tasks);

        assertFalse(result.isShouldExit());
        assertTrue(result.getResponse().contains("read"));
    }

    /* ================= INVALID COMMAND ================= */

    @Test
    public void handle_invalidCommand_returnsError() {
        KetchupResult result = parser.handle("asdfgh", tasks);

        assertFalse(result.isShouldExit());
        assertTrue(result.getResponse().length() > 0);
    }
}
