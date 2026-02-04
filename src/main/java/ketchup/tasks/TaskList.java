package ketchup.tasks;

import java.util.ArrayList;

/**
 * Represents a list of {@link Task} objects and provides basic operations
 * such as adding, deleting, retrieving, and searching tasks.
 */
public class TaskList {

    /** Internal list that stores tasks. */
    private ArrayList<Task> taskList;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return size of the task list
     */
    public int getSize() {
        return this.taskList.size();
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to be added
     */
    public void addTask(Task task) {
        this.taskList.add(task);
    }

    /**
     * Returns the task at the given index.
     *
     * @param idx Index of the task to retrieve
     * @return task at the specified index
     */
    public Task getTask(int idx) {
        return this.taskList.get(idx);
    }

    /**
     * Deletes the task at the given index.
     *
     * @param idx Index of the task to delete
     */
    public void deleteTask(int idx) {
        this.taskList.remove(idx);
    }

    /**
     * Finds tasks whose descriptions contain the given keyword (case-insensitive).
     *
     * @param keyword Keyword to search for
     * @return a new {@link TaskList} containing matching tasks
     */
    public TaskList findTask(String keyword) {
        TaskList results = new TaskList();
        String keywordLower = keyword.toLowerCase();

        for (int i = 0; i < this.getSize(); i++) {
            Task task = this.getTask(i);
            if (task.getDesc().toLowerCase().contains(keywordLower)) {
                results.addTask(task);
            }
        }

        return results;
    }

    /**
     * Returns a user-friendly string representation of the task list.
     *
     * @return formatted task list for display
     */
    @Override
    public String toString() {
        if (this.taskList.isEmpty()) {
            return "No tasks in your list.";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.taskList.size(); i++) {
            if (i > 0) {
                sb.append("\n");
            }
            sb.append(i + 1)
                    .append(". ")
                    .append(this.taskList.get(i).toString());
        }
        return sb.toString();
    }
}
