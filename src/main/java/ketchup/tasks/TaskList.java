package ketchup.tasks;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public int getSize() {
        return this.taskList.size();
    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public Task getTask(int idx) {
        return this.taskList.get(idx);
    }

    public void deleteTask(int i) {
        this.taskList.remove(i);
    }

    @Override
    public String toString() {
        String list = "";
        if (taskList.isEmpty()) {
            return "No tasks in your list.";
        }
        for (int i = 0; i < taskList.size(); i++) {
            if (i == 0) {
                list += (i + 1) + ". " + taskList.get(i).toString();
            } else {
                list += "\n" + (i + 1) + ". " + taskList.get(i).toString();
            }
        }
        return list;
    }
}
