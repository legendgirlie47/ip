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
 * Parses user input commands and applies them to the task list.
 */
public class InputParser {

    /** Date-time format used for parsing user-entered date-time strings. */
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private final Ui ui;

    /**
     * Creates an input parser with the given UI.
     *
     * @param ui UI used to generate user-facing messages
     */
    public InputParser(Ui ui) {
        assert ui != null : "Ui must not be null";
        this.ui = ui;
    }

    /**
     * Handles a user command and returns the result to be shown to the user.
     *
     * @param input User input string
     * @param tasks Task list to operate on
     * @return Result containing feedback message and whether the app should exit
     */
    public KetchupResult handle(String input, TaskList tasks) {

        // Internal invariant
        assert tasks != null : "TaskList must not be null";
        assert ui != null : "Ui must not be null";

        if (input == null) {
            return new KetchupResult(ui.showError("Oh nooo... idk what you are saying..."), false);
        }

        String trimmedInput = input.trim();

        if (trimmedInput.isEmpty()) {
            return new KetchupResult(ui.showError("Oh nooo... idk what you are saying..."), false);
        }

        if (trimmedInput.equalsIgnoreCase("bye")) {
            KetchupResult result = new KetchupResult(ui.showGoodbye(), true);
            assert result != null : "Result must not be null";
            return result;
        }

        if (trimmedInput.equalsIgnoreCase("list")) {
            KetchupResult result = new KetchupResult(ui.showList(tasks), false);
            assert result != null : "Result must not be null";
            return result;
        }

        int taskCount = tasks.getSize();
        assert taskCount >= 0 : "TaskList size must never be negative";

        if (trimmedInput.startsWith("mark")) {
            int idx = parseSingleDigitIndex(trimmedInput, 5);

            if (isInvalidIndex(idx, taskCount)) {
                return new KetchupResult(ui.showError("Task not found!!!"), false);
            }

            Task task = tasks.getTask(idx - 1);
            assert task != null : "Retrieved task must not be null";

            task.markDone();
            Storage.save(tasks);

            KetchupResult result = new KetchupResult(ui.showMarked(task.getDesc()), false);
            assert result != null : "Result must not be null";
            return result;
        }

        if (trimmedInput.startsWith("unmark")) {
            int idx = parseSingleDigitIndex(trimmedInput, 7);

            if (isInvalidIndex(idx, taskCount)) {
                return new KetchupResult(ui.showError("Task not found!!!"), false);
            }

            Task task = tasks.getTask(idx - 1);
            assert task != null : "Retrieved task must not be null";

            task.markUndone();
            Storage.save(tasks);

            KetchupResult result = new KetchupResult(ui.showUnmarked(task.getDesc()), false);
            assert result != null : "Result must not be null";
            return result;
        }

        if (trimmedInput.startsWith("delete")) {
            int idx = parseSingleDigitIndex(trimmedInput, 7);

            if (isInvalidIndex(idx, taskCount)) {
                return new KetchupResult(ui.showError("Task not found!!!"), false);
            }

            Task task = tasks.getTask(idx - 1);
            assert task != null : "Retrieved task must not be null";

            tasks.deleteTask(idx - 1);
            Storage.save(tasks);

            KetchupResult result =
                    new KetchupResult(ui.showDeleted(task.getDesc(), tasks.getSize()), false);
            assert result != null : "Result must not be null";
            return result;
        }

        if (trimmedInput.startsWith("todo")) {
            String todo = trimmedInput.substring(4).trim();

            if (todo.isEmpty()) {
                return new KetchupResult(ui.showError("Toodledoo! What is your todo?"), false);
            }

            Task newTask = new ToDo(todo);
            assert newTask != null : "New ToDo must not be null";

            tasks.addTask(newTask);
            Storage.save(tasks);

            KetchupResult result =
                    new KetchupResult(ui.showAdded("todo", todo, tasks.getSize()), false);
            assert result != null : "Result must not be null";
            return result;
        }

        if (trimmedInput.startsWith("deadline")) {
            int byIndex = trimmedInput.indexOf(" /by ");

            if (byIndex == -1) {
                return new KetchupResult(ui.showError("No deadline given :0"), false);
            }

            String desc = trimmedInput.substring(8, byIndex).trim();

            if (desc.isEmpty()) {
                return new KetchupResult(ui.showError("Tick tock on the clock! What is it you must do?"), false);
            }

            String byRaw = trimmedInput.substring(byIndex + 5).trim();

            try {
                LocalDateTime by =
                        LocalDateTime.parse(byRaw, DATE_TIME_FORMATTER);

                assert by != null : "Parsed deadline time must not be null";

                Task newTask = new Deadline(desc, by);
                assert newTask != null : "New Deadline must not be null";

                tasks.addTask(newTask);
                Storage.save(tasks);

                KetchupResult result =
                        new KetchupResult(ui.showAdded("deadline", desc, tasks.getSize()), false);
                assert result != null : "Result must not be null";
                return result;

            } catch (DateTimeParseException e) {
                return new KetchupResult(
                        ui.showError("Please enter date in 'yyyy-MM-dd HHmm' format."), false);
            }
        }

        if (trimmedInput.startsWith("event")) {
            int fromIndex = trimmedInput.indexOf(" /from ");
            int toIndex = trimmedInput.indexOf(" /to ");

            if (fromIndex == -1) {
                return new KetchupResult(ui.showError("What is the start time!!!"), false);
            }

            if (toIndex == -1) {
                return new KetchupResult(ui.showError("What is the end time!!!"), false);
            }

            String desc = trimmedInput.substring(5, fromIndex).trim();

            if (desc.isEmpty()) {
                return new KetchupResult(ui.showError("Hey!! What is it you must do?"), false);
            }

            String fromRaw = trimmedInput.substring(fromIndex + 7, toIndex).trim();
            String toRaw = trimmedInput.substring(toIndex + 5).trim();

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

                KetchupResult result =
                        new KetchupResult(ui.showAdded("event", desc, tasks.getSize()), false);
                assert result != null : "Result must not be null";
                return result;

            } catch (DateTimeParseException e) {
                return new KetchupResult(
                        ui.showError("Please enter date in 'yyyy-MM-dd HHmm' format."), false);
            }
        }

        if (trimmedInput.startsWith("find")) {
            String keyword = trimmedInput.substring(5).trim();

            TaskList result = tasks.findTask(keyword);
            assert result != null : "findTask must not return null";

            if (result.getSize() == 0) {
                return new KetchupResult(ui.showError("Oops! No matching task found..."), false);
            }

            KetchupResult finalResult = new KetchupResult(result.toString(), false);
            assert finalResult != null : "Result must not be null";
            return finalResult;
        }

        KetchupResult fallback =
                new KetchupResult(ui.showError("Oh nooo... idk what you are saying..."), false);
        assert fallback != null : "Result must not be null";
        return fallback;
    }

    private boolean isInvalidIndex(int idx, int taskCount) {
        return idx <= 0 || idx > taskCount;
    }

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
