package ketchup.tasks;

import java.time.LocalDateTime;

public class Event extends Task {
    private LocalDateTime start; //Start time of event
    private LocalDateTime end; //End time of event

    public Event(String desc, LocalDateTime start, LocalDateTime end) {
        super(desc);
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    /**
     * Returns a string representation of this event task suitable for file storage.
     * <p>
     * Format:
     * E | isDone | description | startDateTime | endDateTime
     * <p>
     * Example:
     * E | 0 | project meeting | 2019-12-02 1800 | 2019-12-02 2000
     *
     * @return a formatted string that can be written to the save file
     */
    @Override
    public String toFileString() {
        return ("E | " + (this.isDone()? "1": "0") + " | " + this.getDesc() + " | "
                + this.start.format(Task.getDateFormat()) + " | "
                + this.end.format(Task.getDateFormat()));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.start.format(Task.getDateFormat())
                + " to: " + this.end.format(Task.getDateFormat()) + ")";
    }
}
