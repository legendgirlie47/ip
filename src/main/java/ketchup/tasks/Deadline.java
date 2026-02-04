package ketchup.tasks;

import java.time.LocalDateTime;

/**
 * Represents a deadline task that must be completed by a specific date-time.
 */
public class Deadline extends Task {

    /** Date-time by which the task must be completed. */
    private LocalDateTime deadline;

    /**
     * Creates a deadline task with the given description and deadline.
     *
     * @param desc     Description of the task
     * @param deadline Date-time by which the task must be completed
     */
    public Deadline(String desc, LocalDateTime deadline) {
        super(desc);
        this.deadline = deadline;
    }

    /**
     * Returns the deadline date-time of the task.
     *
     * @return the deadline date-time
     */
    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    /**
     * Returns a string representation of this deadline task suitable for file storage.
     * <p>
     * Format:
     * D | isDone | description | deadline
     * </p>
     * <p>
     * Example:
     * D | 0 | project slides | 2019-12-02 1800
     * </p>
     *
     * @return a formatted string that can be written to the save file
     */
    @Override
    public String toFileString() {
        return "D | " + (this.isDone() ? "1" : "0") + " | " + this.getDesc() + " | "
                + this.deadline.format(Task.getDateFormat());
    }

    /**
     * Returns a user-friendly string representation of this deadline task.
     *
     * @return formatted deadline description for display
     */
    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + this.deadline.format(Task.getDateFormat()) + ")";
    }
}
