package ketchup.parser;

import ketchup.tasks.Deadline;
import ketchup.tasks.Event;
import ketchup.tasks.Task;
import ketchup.tasks.TaskList;
import ketchup.tasks.ToDo;
import ketchup.storage.Storage;
import ketchup.ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputParser {
    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private final Ui ui;

    public InputParser(Ui ui) {
        this.ui = ui;
    }

    public boolean handle(String input, TaskList tasks) {
        if (input == null) {
            ui.showError("Oh nooo... idk what you are saying...");
            return false;
        }

        input = input.trim();

        if (input.equalsIgnoreCase("bye")) {
            return true;
        }

        int taskCount = tasks.getSize();

        if (input.equalsIgnoreCase("list")) {
            ui.showList(tasks);
            return false;
        }

        if (input.startsWith("mark")) {
            int idx = parseSingleDigitIndex(input, 5);
            if (!isValidIndex(idx, taskCount)) {
                ui.showError("ketchup.tasks.Task not found!!!");
                return false;
            }
            Task t = tasks.getTask(idx - 1);
            t.markDone();
            ui.showMarked(t.getDesc());
            Storage.save(tasks);
            return false;
        }

        if (input.startsWith("unmark")) {
            int idx = parseSingleDigitIndex(input, 7);
            if (!isValidIndex(idx, taskCount)) {
                ui.showError("ketchup.tasks.Task not found!!!");
                return false;
            }
            Task t = tasks.getTask(idx - 1);
            t.markUndone();
            ui.showUnmarked(t.getDesc());
            Storage.save(tasks);
            return false;
        }

        if (input.startsWith("delete")) {
            int idx = parseSingleDigitIndex(input, 7);
            if (!isValidIndex(idx, taskCount)) {
                ui.showError("ketchup.tasks.Task not found!!!");
                return false;
            }
            Task t = tasks.getTask(idx - 1);
            tasks.deleteTask(idx - 1);
            ui.showDeleted(t.getDesc(), tasks.getSize());
            Storage.save(tasks);
            return false;
        }

        if (input.startsWith("todo")) {
            String todo = input.substring(4).trim();
            if (todo.isEmpty()) {
                ui.showError("Toodledoo! What is your todo?");
                return false;
            }
            tasks.addTask(new ToDo(todo));
            ui.showAdded("todo", todo, tasks.getSize());
            Storage.save(tasks);
            return false;
        }

        if (input.startsWith("deadline")) {
            int byIndex = input.indexOf(" /by ");
            if (byIndex == -1) {
                ui.showError("No deadline given :0");
                return false;
            }
            String desc = input.substring(8, byIndex).trim();
            if (desc.isEmpty()) {
                ui.showError("Tick tock on the clock! What is it you must do?");
                return false;
            }
            String byRaw = input.substring(byIndex + 5).trim();

            try {
                LocalDateTime by = LocalDateTime.parse(byRaw, DT_FMT);
                tasks.addTask(new Deadline(desc, by));
                ui.showAdded("deadline", desc, tasks.getSize());
                Storage.save(tasks);
            } catch (DateTimeParseException e) {
                ui.showError("Please enter date in 'yyyy-MM-dd HHmm' format.");
            }
            return false;
        }

        if (input.startsWith("event")) {
            int fromIndex = input.indexOf(" /from ");
            int toIndex = input.indexOf(" /to ");
            if (fromIndex == -1) {
                ui.showError("What is the start time!!!");
                return false;
            }
            if (toIndex == -1) {
                ui.showError("What is the end time!!!");
                return false;
            }

            String desc = input.substring(5, fromIndex).trim();
            if (desc.isEmpty()) {
                ui.showError("Hey!! What is it you must do?");
                return false;
            }

            String fromRaw = input.substring(fromIndex + 7, toIndex).trim();
            String toRaw = input.substring(toIndex + 5).trim();

            try {
                LocalDateTime from = LocalDateTime.parse(fromRaw, DT_FMT);
                LocalDateTime to = LocalDateTime.parse(toRaw, DT_FMT);
                tasks.addTask(new Event(desc, from, to));
                ui.showAdded("event", desc, tasks.getSize());
                Storage.save(tasks);
            } catch (DateTimeParseException e) {
                ui.showError("Please enter date in 'yyyy-MM-dd HHmm' format.");
            }
            return false;
        }

        ui.showError("Oh nooo... idk what you are saying...");
        return false;
    }

    private boolean isValidIndex(int idx, int taskCount) {
        return idx > 0 && idx <= taskCount;
    }

    private int parseSingleDigitIndex(String input, int charPos) {
        if (input.length() <= charPos) return -1;
        char c = input.charAt(charPos);
        if (!Character.isDigit(c)) return -1;
        return c - '0';
    }
}
