package ketchup.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link TaskList}.
 * Verifies task addition, deletion, retrieval,
 * searching, and string formatting behavior.
 */
public class TaskListTest {

    private TaskList taskList;

    /**
     * Initializes a fresh TaskList before each test.
     */
    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    /* ================= BASIC OPERATIONS ================= */

    @Test
    public void constructor_initialSizeZero() {
        assertEquals(0, taskList.getSize());
    }

    @Test
    public void addTask_increasesSize() {
        taskList.addTask(new ToDo("read book"));
        assertEquals(1, taskList.getSize());
    }

    @Test
    public void getTask_returnsCorrectTask() {
        Task task = new ToDo("study");
        taskList.addTask(task);

        assertEquals(task, taskList.getTask(0));
    }

    @Test
    public void deleteTask_removesTask() {
        taskList.addTask(new ToDo("task1"));
        taskList.addTask(new ToDo("task2"));

        taskList.deleteTask(0);

        assertEquals(1, taskList.getSize());
        assertEquals("task2", taskList.getTask(0).getDesc());
    }

    /* ================= FIND ================= */

    @Test
    public void findTask_existingKeyword_returnsMatchingTasks() {
        taskList.addTask(new ToDo("read book"));
        taskList.addTask(new ToDo("write report"));
        taskList.addTask(new ToDo("buy milk"));

        TaskList results = taskList.findTask("read");

        assertEquals(1, results.getSize());
        assertTrue(results.getTask(0).getDesc().contains("read"));
    }

    @Test
    public void findTask_caseInsensitive_matchesCorrectly() {
        taskList.addTask(new ToDo("Read Book"));

        TaskList results = taskList.findTask("read");

        assertEquals(1, results.getSize());
    }

    @Test
    public void findTask_noMatch_returnsEmptyList() {
        taskList.addTask(new ToDo("read book"));

        TaskList results = taskList.findTask("math");

        assertEquals(0, results.getSize());
    }

    /* ================= toString ================= */

    @Test
    public void toString_emptyList_returnsMessage() {
        assertEquals("No tasks in your list.", taskList.toString());
    }

    @Test
    public void toString_nonEmptyList_formatsCorrectly() {
        taskList.addTask(new ToDo("read"));
        taskList.addTask(new ToDo("write"));

        String output = taskList.toString();

        assertTrue(output.contains("1."));
        assertTrue(output.contains("2."));
        assertTrue(output.contains("read"));
        assertTrue(output.contains("write"));
    }
}
