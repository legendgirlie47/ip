package ketchup.tasks;

import java.time.LocalDateTime;

/**
 * Represents an event task with a start and end date-time.
 * An event is a type of {@link Task} that spans a fixed time period.
 */
public class Event extends Task {

    /** Start date-time of the event. */
    private LocalDateTime start;

    /** End date-time of the event. */
    private LocalDateTime end;

    /**
     * Creates an event task with the given description, start time, and end time.
     *
     * @param desc  Description of the event
     * @param start Start date-time of the event
     * @param end   End date-time of the event
     */
    public Event(String desc, LocalDateTime start, LocalDateTime end) {
        super(desc);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the start date-time of the event.
     *
     * @return the event start date-time
     */
    public LocalDateTime getStart() {
        return this.start;
    }

    /**
     * Returns the end date-time of the event.
     *
     * @return the event end date-time
     */
    public LocalDateTime getEnd() {
        return this.end;
    }

    /**
     * Returns a string representation of this event task suitable for file storage.
     * <p>
     * Format:
     * E | isDone | description | startDateTime | endDateTime
     * </p>
     * <p>
     * Example:
     * E | 0 | project meeting | 2019-12-02 1800 | 2019-12-02 2000
     * </p>
     *
     * @return a formatted string that can be written to the save file
     */
    @Override
    public String toFileString() {
        return "E | " + (this.isDone() ? "1" : "0") + " | " + this.getDesc() + " | "
                + this.start.format(Task.getDateFormat()) + " | "
                + this.end.format(Task.getDateFormat());
    }

    /**
     * Returns a user-friendly string representation of this event.
     *
     * @return formatted event description for display
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + this.start.format(Task.getDateFormat())
                + " to: " + this.end.format(Task.getDateFormat()) + ")";
    }
}
