package ketchup.tasks;

import java.time.format.DateTimeFormatter;

/**
 * Represents a generic task with a description and completion status.
 * This class serves as the base class for all task types.
 */
public class Task {

    /** Date-time formatter used for task storage and display. */
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /** Indicates whether the task has been completed. */
    private boolean isDone;

    /** Description of the task. */
    private String desc;

    /**
     * Creates a task with the given description.
     *
     * @param desc Description of the task
     */
    public Task(String desc) {
        this.desc = desc;
        this.isDone = false;
    }

    /**
     * Returns the date-time formatter used by tasks.
     *
     * @return task date-time formatter
     */
    public static DateTimeFormatter getDateFormat() {
        return FORMAT;
    }

    /**
     * Returns whether the task is marked as done.
     *
     * @return true if the task is completed, false otherwise
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Marks the task as done.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return task description
     */
    public String getDesc() {
        return this.desc;
    }

    /**
     * Returns a string representation of this task suitable for file storage.
     * <p>
     * Format:
     * T | isDone | description
     * </p>
     * <p>
     * Example:
     * T | 0 | buy bread
     * </p>
     *
     * @return a formatted string that can be written to the save file
     */
    public String toFileString() {
        return "T | " + (this.isDone ? "1" : "0") + " | " + this.desc;
    }

    /**
     * Returns a user-friendly string representation of this task.
     *
     * @return formatted task description for display
     */
    @Override
    public String toString() {
        return (this.isDone ? "[X] " : "[ ] ") + this.desc;
    }
}
