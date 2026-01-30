import java.time.format.DateTimeFormatter;

public class Task {
    private boolean isDone = false;
    private String desc;
    private static DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public Task(String desc) {
        this.desc = desc;
    }

    public static DateTimeFormatter getDateFormat() {
        return FORMAT;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    public String getDesc() {
        return this.desc;
    }

    public String toFileString() {
        return ("T | " + (this.isDone? "1": "0") + " | " + this.desc);
    };

    @Override
    public String toString() {
        if (this.isDone) {
            return ("[X] " + this.desc);
        } else {
            return ("[ ] " + this.desc);
        }
    }
}
