package ketchup;

import java.time.LocalDateTime;

public class Deadline extends Task {
    private LocalDateTime deadline;

    public Deadline(String desc, LocalDateTime deadline) {
        super(desc);
        this.deadline = deadline;
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    /**
     * Returns a string representation of this deadline task suitable for file storage.
     * <p>
     * Format:
     * D | isDone | description | deadline
     * <p>
     * Example:
     * D | 0 | project slides | 2019-12-02 1800
     *
     * @return a formatted string that can be written to the save file
     */
    @Override
    public String toFileString() {
        return ("D | " + (this.isDone()? "1": "0") + " | " + this.getDesc() + " | "
                + this.deadline.format(Task.getDateFormat()));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline.format(Task.getDateFormat()) + ")";
    }
}
