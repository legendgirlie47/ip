import java.time.LocalDateTime;

public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    public Event(String desc, LocalDateTime start, LocalDateTime end) {
        super(desc);
        this.start = start;
        this.end = end;
    }

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
