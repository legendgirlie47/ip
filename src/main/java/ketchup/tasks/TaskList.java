package ketchup.tasks;

import java.util.ArrayList;

import ketchup.ui.Ui;

public class TaskList {
    private Ui ui = new Ui();
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

    public TaskList findTask(String keyword) {
        TaskList results = new TaskList();

        for (int i = 0; i < this.getSize(); i++) {
            Task task = this.getTask(i);
            if (task.getDesc().toLowerCase().contains(keyword.toLowerCase())) {
                results.addTask(task);
            }
        }

        return results;
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
