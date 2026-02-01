package ketchup.ui;

import ketchup.TaskList;

public class Ui {

    public void showHello() {
        System.out.println("Hello! I'm ketchup.\nWhat can I do for you?\n");
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showError(String msg) {
        System.out.println(msg);
    }

    public void showAdded(String type, String desc, int size) {
        System.out.println("Sure! I have added " + type + ": " + desc);
        System.out.println("You now have " + size + " tasks in your list!");
    }

    public void showDeleted(String desc, int size) {
        System.out.println("Okay! I deleted task: " + desc + "\nYou have " + size + " tasks left!");
    }

    public void showMarked(String desc) {
        System.out.println("Marked task: '" + desc + "' as done! :D");
    }

    public void showUnmarked(String desc) {
        System.out.println("Unmarked task: '" + desc + "' as done :(");
    }

    public void showList(TaskList tasks) {
        if (tasks.getSize() == 0) {
            System.out.println("No tasks in your list.");
        } else {
            System.out.println(tasks.toString());
        }
    }
}
