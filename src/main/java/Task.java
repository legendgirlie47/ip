public class Task {
    private boolean isDone = false;
    private String desc;

    public Task(String desc) {
        this.desc = desc;
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

    @Override
    public String toString() {
        if (this.isDone) {
            return ("[X] " + this.desc);
        } else {
            return ("[ ] " + this.desc);
        }
    }
}
