package ketchup.tasks;

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
