package ketchup.ui;

import ketchup.tasks.TaskList;

/**
 * Handles all user-facing output for the Ketchup application.
 * This class is responsible for displaying messages, errors,
 * and task-related feedback to the user.
 */
public class Ui {

    /**
     * Displays the greeting message when the program starts.
     */
    public void showHello() {
        System.out.println("Hello! I'm ketchup.\nWhat can I do for you?\n");
    }

    /**
     * Displays the goodbye message when the program exits.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays an error message to the user.
     *
     * @param msg The error message to be shown
     */
    public void showError(String msg) {
        System.out.println(msg);
    }

    /**
     * Displays a message indicating that a task has been added.
     *
     * @param type The type of task added
     * @param desc The description of the task
     * @param size The updated number of tasks in the list
     */
    public void showAdded(String type, String desc, int size) {
        System.out.println("Sure! I have added " + type + ": " + desc);
        System.out.println("You now have " + size + " tasks in your list!");
    }

    /**
     * Displays a message indicating that a task has been deleted.
     *
     * @param desc The description of the deleted task
     * @param size The remaining number of tasks in the list
     */
    public void showDeleted(String desc, int size) {
        System.out.println("Okay! I deleted task: " + desc
                + "\nYou have " + size + " tasks left!");
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param desc The description of the marked task
     */
    public void showMarked(String desc) {
        System.out.println("Marked task: '" + desc + "' as done! :D");
    }

    /**
     * Displays a message indicating that a task has been unmarked.
     *
     * @param desc The description of the unmarked task
     */
    public void showUnmarked(String desc) {
        System.out.println("Unmarked task: '" + desc + "' as done :(");
    }

    /**
     * Displays the list of tasks to the user.
     * If the task list is empty, an appropriate message is shown instead.
     *
     * @param tasks The task list to be displayed
     */
    public void showList(TaskList tasks) {
        if (tasks.getSize() == 0) {
            System.out.println("No tasks in your list.");
        } else {
            System.out.println(tasks.toString());
        }
    }
}
