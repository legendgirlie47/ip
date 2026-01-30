public class Deadline extends Task {
    private String deadline;

    public Deadline(String desc, String deadline) {
        super(desc);
        this.deadline = deadline;
    }

    @Override
    public String toFileString() {
        return ("D | " + (this.isDone()? "1": "0") + " | " + this.getDesc() + " | "
                + this.deadline);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline + ")";
    }
}
