package ketchup.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
     * @param ui UI used to display messages to the user
     */
    public InputParser(Ui ui) {
        this.ui = ui;
    }

    /**
     * Handles a user command.
     *
     * @param input User input string
     * @param tasks Task list to operate on
     * @return true if the program should exit, false otherwise
     */
    public boolean handle(String input, TaskList tasks) {
        if (input == null) {
            ui.showError("Oh nooo... idk what you are saying...");
            return false;
        }

        String trimmedInput = input.trim();

        if (trimmedInput.equalsIgnoreCase("bye")) {
            return true;
        }

        if (trimmedInput.equalsIgnoreCase("list")) {
            ui.showList(tasks);
            return false;
        }

        int taskCount = tasks.getSize();

        if (trimmedInput.startsWith("mark")) {
            int idx = parseSingleDigitIndex(trimmedInput, 5);
            if (isInvalidIndex(idx, taskCount)) {
                ui.showError("Task not found!!!");
                return false;
            }
            Task task = tasks.getTask(idx - 1);
            task.markDone();
            ui.showMarked(task.getDesc());
            Storage.save(tasks);
            return false;
        }

        if (trimmedInput.startsWith("unmark")) {
            int idx = parseSingleDigitIndex(trimmedInput, 7);
            if (isInvalidIndex(idx, taskCount)) {
                ui.showError("Task not found!!!");
                return false;
            }
            Task task = tasks.getTask(idx - 1);
            task.markUndone();
            ui.showUnmarked(task.getDesc());
            Storage.save(tasks);
            return false;
        }

        if (trimmedInput.startsWith("delete")) {
            int idx = parseSingleDigitIndex(trimmedInput, 7);
            if (isInvalidIndex(idx, taskCount)) {
                ui.showError("Task not found!!!");
                return false;
            }
            Task task = tasks.getTask(idx - 1);
            tasks.deleteTask(idx - 1);
            ui.showDeleted(task.getDesc(), tasks.getSize());
            Storage.save(tasks);
            return false;
        }

        if (trimmedInput.startsWith("todo")) {
            String todo = trimmedInput.substring(4).trim();
            if (todo.isEmpty()) {
                ui.showError("Toodledoo! What is your todo?");
                return false;
            }
            tasks.addTask(new ToDo(todo));
            ui.showAdded("todo", todo, tasks.getSize());
            Storage.save(tasks);
            return false;
        }

        if (trimmedInput.startsWith("deadline")) {
            int byIndex = trimmedInput.indexOf(" /by ");
            if (byIndex == -1) {
                ui.showError("No deadline given :0");
                return false;
            }

            String desc = trimmedInput.substring(8, byIndex).trim();
            if (desc.isEmpty()) {
                ui.showError("Tick tock on the clock! What is it you must do?");
                return false;
            }

            String byRaw = trimmedInput.substring(byIndex + 5).trim();
            try {
                LocalDateTime by = LocalDateTime.parse(byRaw, DATE_TIME_FORMATTER);
                tasks.addTask(new Deadline(desc, by));
                ui.showAdded("deadline", desc, tasks.getSize());
                Storage.save(tasks);
            } catch (DateTimeParseException e) {
                ui.showError("Please enter date in 'yyyy-MM-dd HHmm' format.");
            }
            return false;
        }

        if (trimmedInput.startsWith("event")) {
            int fromIndex = trimmedInput.indexOf(" /from ");
            int toIndex = trimmedInput.indexOf(" /to ");
            if (fromIndex == -1) {
                ui.showError("What is the start time!!!");
                return false;
            }
            if (toIndex == -1) {
                ui.showError("What is the end time!!!");
                return false;
            }

            String desc = trimmedInput.substring(5, fromIndex).trim();
            if (desc.isEmpty()) {
                ui.showError("Hey!! What is it you must do?");
                return false;
            }

            String fromRaw = trimmedInput.substring(fromIndex + 7, toIndex).trim();
            String toRaw = trimmedInput.substring(toIndex + 5).trim();

            try {
                LocalDateTime from = LocalDateTime.parse(fromRaw, DATE_TIME_FORMATTER);
                LocalDateTime to = LocalDateTime.parse(toRaw, DATE_TIME_FORMATTER);
                tasks.addTask(new Event(desc, from, to));
                ui.showAdded("event", desc, tasks.getSize());
                Storage.save(tasks);
            } catch (DateTimeParseException e) {
                ui.showError("Please enter date in 'yyyy-MM-dd HHmm' format.");
            }
            return false;
        }

        if (trimmedInput.startsWith("find")) {
            String keyword = trimmedInput.substring(5).trim();
            TaskList result = tasks.findTask(keyword);

            if (result.getSize() == 0) {
                ui.showError("Oops! No matching task found...");
                return false;
            }

            System.out.println(result);
            return false;
        }

        ui.showError("Oh nooo... idk what you are saying...");
        return false;
    }

    /**
     * Checks if a 1-based index is invalid for the current task list size.
     *
     * @param idx       1-based index provided by the user
     * @param taskCount number of tasks currently in the list
     * @return true if invalid, false otherwise
     */
    private boolean isInvalidIndex(int idx, int taskCount) {
        return idx <= 0 || idx > taskCount;
    }

    /**
     * Parses a single digit at a specific character position as a 1-based index.
     *
     * @param input   full user input
     * @param charPos character position of the digit
     * @return parsed digit as an int, or -1 if invalid
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
