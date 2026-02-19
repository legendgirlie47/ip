package ketchup.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import ketchup.KetchupResult;
import ketchup.storage.Storage;
import ketchup.tasks.Deadline;
import ketchup.tasks.Event;
import ketchup.tasks.Task;
import ketchup.tasks.TaskList;
import ketchup.tasks.ToDo;
import ketchup.ui.Ui;

/**
 * Parses and processes user input commands.
 * <p>
 * This class interprets raw user input strings, delegates command-specific
 * logic to helper methods, and returns the corresponding {@link KetchupResult}.
 */
public class InputParser {

    /** Date-time formatter used for parsing user-entered date-time strings. */
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /** UI component used to generate user-facing messages. */
    private final Ui ui;

    /**
     * Constructs an InputParser with the specified UI component.
     *
     * @param ui the UI instance used to generate feedback messages
     */
    public InputParser(Ui ui) {
        assert ui != null : "Ui must not be null";
        this.ui = ui;
    }

    /**
     * Processes a single user input command.
     *
     * @param input the raw user input string
     * @param tasks the task list to operate on
     * @return a {@link KetchupResult} containing the response message
     *         and whether the application should exit
     */
    public KetchupResult handle(String input, TaskList tasks) {

        assert tasks != null : "TaskList must not be null";

        if (input == null || input.trim().isEmpty()) {
            return error("Oh nooo... idk what you are saying...");
        }

        String trimmed = input.trim();

        if (trimmed.equalsIgnoreCase("bye")) {
            return new KetchupResult(ui.showGoodbye(), true);
        }

        if (trimmed.equalsIgnoreCase("help")) {
            return new KetchupResult(ui.showAppGuidance(), false);
        }

        if (trimmed.equalsIgnoreCase("list")) {
            return new KetchupResult(ui.showList(tasks), false);
        }

        if (trimmed.startsWith("mark")) {
            return handleMark(trimmed, tasks);
        }

        if (trimmed.startsWith("unmark")) {
            return handleUnmark(trimmed, tasks);
        }

        if (trimmed.startsWith("delete")) {
            return handleDelete(trimmed, tasks);
        }

        if (trimmed.startsWith("todo")) {
            return handleTodo(trimmed, tasks);
        }

        if (trimmed.startsWith("deadline")) {
            return handleDeadline(trimmed, tasks);
        }

        if (trimmed.startsWith("event")) {
            return handleEvent(trimmed, tasks);
        }

        if (trimmed.startsWith("find")) {
            return handleFind(trimmed, tasks);
        }

        return error("Oh nooo... idk what you are saying...");
    }

    /**
     * Handles the {@code mark} command.
     *
     * @param input the full user input string
     * @param tasks the task list
     * @return the result of marking a task as done
     */
    private KetchupResult handleMark(String input, TaskList tasks) {
        int idx = parseSingleDigitIndex(input, 5);

        if (isInvalidIndex(idx, tasks.getSize())) {
            return error("Task not found!!!");
        }

        Task task = tasks.getTask(idx - 1);
        assert task != null : "Retrieved task must not be null";

        task.markDone();
        Storage.save(tasks);

        return new KetchupResult(ui.showMarked(task.getDesc()), false);
    }

    /**
     * Handles the {@code unmark} command.
     *
     * @param input the full user input string
     * @param tasks the task list
     * @return the result of marking a task as not done
     */
    private KetchupResult handleUnmark(String input, TaskList tasks) {
        int idx = parseSingleDigitIndex(input, 7);

        if (isInvalidIndex(idx, tasks.getSize())) {
            return error("Task not found!!!");
        }

        Task task = tasks.getTask(idx - 1);
        assert task != null : "Retrieved task must not be null";

        task.markUndone();
        Storage.save(tasks);

        return new KetchupResult(ui.showUnmarked(task.getDesc()), false);
    }

    /**
     * Handles the {@code delete} command.
     *
     * @param input the full user input string
     * @param tasks the task list
     * @return the result of deleting a task
     */
    private KetchupResult handleDelete(String input, TaskList tasks) {
        int idx = parseSingleDigitIndex(input, 7);

        if (isInvalidIndex(idx, tasks.getSize())) {
            return error("Task not found!!!");
        }

        Task task = tasks.getTask(idx - 1);
        assert task != null : "Retrieved task must not be null";

        tasks.deleteTask(idx - 1);
        Storage.save(tasks);

        return new KetchupResult(
                ui.showDeleted(task.getDesc(), tasks.getSize()), false);
    }

    /**
     * Handles the {@code todo} command.
     *
     * @param input the full user input string
     * @param tasks the task list
     * @return the result of adding a new ToDo task
     */
    private KetchupResult handleTodo(String input, TaskList tasks) {
        String desc = input.substring(4).trim();

        if (desc.isEmpty()) {
            return error("Toodledoo! What is your todo?");
        }

        Task newTask = new ToDo(desc);
        assert newTask != null : "New ToDo task must not be null";

        tasks.addTask(newTask);
        Storage.save(tasks);

        return new KetchupResult(
                ui.showAdded("todo", desc, tasks.getSize()), false);
    }

    /**
     * Handles the {@code deadline} command.
     *
     * @param input the full user input string
     * @param tasks the task list
     * @return the result of adding a new Deadline task
     */
    private KetchupResult handleDeadline(String input, TaskList tasks) {

        int byIndex = input.indexOf(" /by ");
        if (byIndex == -1) {
            return error("No deadline given :0");
        }

        String desc = input.substring(8, byIndex).trim();
        if (desc.isEmpty()) {
            return error("Tick tock on the clock! What is it you must do?");
        }

        String byRaw = input.substring(byIndex + 5).trim();

        try {
            LocalDateTime by =
                    LocalDateTime.parse(byRaw, DATE_TIME_FORMATTER);

            assert by != null : "Parsed deadline time must not be null";

            Task newTask = new Deadline(desc, by);
            assert newTask != null : "New Deadline must not be null";

            tasks.addTask(newTask);
            Storage.save(tasks);

            return new KetchupResult(
                    ui.showAdded("deadline", desc, tasks.getSize()), false);

        } catch (DateTimeParseException e) {
            return error("Please enter date in 'yyyy-MM-dd HHmm' format.");
        }
    }

    /**
     * Handles the {@code event} command.
     *
     * @param input the full user input string
     * @param tasks the task list
     * @return the result of adding a new Event task
     */
    private KetchupResult handleEvent(String input, TaskList tasks) {

        int fromIndex = input.indexOf(" /from ");
        int toIndex = input.indexOf(" /to ");

        if (fromIndex == -1) {
            return error("What is the start time!!!");
        }

        if (toIndex == -1) {
            return error("What is the end time!!!");
        }

        String desc = input.substring(5, fromIndex).trim();
        if (desc.isEmpty()) {
            return error("Hey!! What is it you must do?");
        }

        String fromRaw = input.substring(fromIndex + 7, toIndex).trim();
        String toRaw = input.substring(toIndex + 5).trim();

        try {
            LocalDateTime from =
                    LocalDateTime.parse(fromRaw, DATE_TIME_FORMATTER);
            LocalDateTime to =
                    LocalDateTime.parse(toRaw, DATE_TIME_FORMATTER);

            assert from != null : "Parsed event start must not be null";
            assert to != null : "Parsed event end must not be null";
            assert !to.isBefore(from)
                    : "Event end time cannot be before start time";

            Task newTask = new Event(desc, from, to);
            assert newTask != null : "New Event must not be null";

            tasks.addTask(newTask);
            Storage.save(tasks);

            return new KetchupResult(
                    ui.showAdded("event", desc, tasks.getSize()), false);

        } catch (DateTimeParseException e) {
            return error("Please enter date in 'yyyy-MM-dd HHmm' format.");
        }
    }

    /**
     * Handles the {@code find} command.
     *
     * @param input the full user input string
     * @param tasks the task list
     * @return the result of searching for tasks containing a keyword
     */
    private KetchupResult handleFind(String input, TaskList tasks) {
        String keyword = input.substring(5).trim();
        TaskList result = tasks.findTask(keyword);

        assert result != null : "findTask must not return null";

        if (result.getSize() == 0) {
            return error("Oops! No matching task found...");
        }

        return new KetchupResult(result.toString(), false);
    }

    /**
     * Creates a standardized error result.
     *
     * @param message the error message to display
     * @return a {@link KetchupResult} containing the formatted error message
     */
    private KetchupResult error(String message) {
        return new KetchupResult(ui.showError(message), false);
    }

    /**
     * Checks whether a given index is outside the valid task range.
     *
     * @param idx the user-provided index (1-based)
     * @param taskCount the total number of tasks
     * @return true if the index is invalid, false otherwise
     */
    private boolean isInvalidIndex(int idx, int taskCount) {
        return idx <= 0 || idx > taskCount;
    }

    /**
     * Parses a single-digit task index from a specific character position.
     *
     * @param input the full input string
     * @param charPos the character position where the index is expected
     * @return the parsed integer index, or -1 if invalid
     */
    private int parseSingleDigitIndex(String input, int charPos) {
        if (input.length() <= charPos) {
            return -1;
        }

        char c = input.charAt(charPos);
        if (!Character.isDigit(c)) {
            return -1;
        }

        return c - '0';
    }
}
