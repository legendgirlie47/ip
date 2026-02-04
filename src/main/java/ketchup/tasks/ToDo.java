package ketchup.tasks;

/**
 * Represents a to-do task without any associated date or time.
 */
public class ToDo extends Task {

    /**
     * Creates a to-do task with the given description.
     *
     * @param desc Description of the task
     */
    public ToDo(String desc) {
        super(desc);
    }

    /**
     * Returns a user-friendly string representation of this to-do task.
     *
     * @return formatted to-do description for display
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
