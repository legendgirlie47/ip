package ketchup.ui;

import ketchup.tasks.TaskList;

/**
 * Handles all user-facing messages for the Ketchup application.
 * <p>
 * This class is responsible for generating text responses such as greetings,
 * confirmations, error messages, and task-related feedback. It does not
 * handle input parsing or application logic.
 */
public class Ui {

    /**
     * Returns the greeting message displayed when the application starts.
     *
     * @return A greeting message for the user
     */
    public String showHello() {
        return "Hola!! I'm Ketchup! \nWhat can I do for you?\n";
    }

    /**
     * Returns a short guidance message prompting the user
     * to type the help command for available instructions.
     *
     * @return a user-facing message encouraging the use of the help command
     */
    public String showHelpMessage() {
        return "Not sure what to do? Just type 'help'!";
    }

    /**
     * Returns the farewell message displayed when the application exits.
     *
     * @return A goodbye message for the user
     */
    public String showGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns an error message to be displayed to the user.
     *
     * @param msg The error message content
     * @return The error message
     */
    public String showError(String msg) {
        return msg;
    }

    /**
     * Returns a confirmation message indicating that a task has been added.
     *
     * @param type The type of task added (e.g. todo, deadline, event)
     * @param desc The description of the task
     * @param size The updated number of tasks in the list
     * @return A message confirming the task addition
     */
    public String showAdded(String type, String desc, int size) {
        return "Sure! I have added " + type + ": " + desc
                + "\nYou now have " + size + " tasks in your list!";
    }

    /**
     * Returns a confirmation message indicating that a task has been deleted.
     *
     * @param desc The description of the deleted task
     * @param size The remaining number of tasks in the list
     * @return A message confirming the task deletion
     */
    public String showDeleted(String desc, int size) {
        return "Okay! I deleted task: " + desc
                + "\nYou have " + size + " tasks left!";
    }

    /**
     * Returns a message indicating that a task has been marked as done.
     *
     * @param desc The description of the marked task
     * @return A confirmation message for marking a task as done
     */
    public String showMarked(String desc) {
        return "Marked task: '" + desc + "' as done! :D";
    }

    /**
     * Returns a message indicating that a task has been unmarked.
     *
     * @param desc The description of the unmarked task
     * @return A confirmation message for unmarking a task
     */
    public String showUnmarked(String desc) {
        return "Unmarked task: '" + desc + "' as done :(";
    }

    /**
     * Returns a string representation of the task list.
     * If the task list is empty, an appropriate message is returned instead.
     *
     * @param tasks The task list to be displayed
     * @return A formatted list of tasks or a message indicating the list is empty
     */
    public String showList(TaskList tasks) {
        if (tasks.getSize() == 0) {
            return "No tasks in your list.";
        } else {
            return tasks.toString();
        }
    }

    /**
     * Returns a formatted help message that lists all available commands
     * supported by the Ketchup chatbot.
     *
     * @return A multi-line String containing usage instructions for
     *         creating, modifying, deleting tasks, and exiting the chatbot.
     */
    public String showAppGuidance() {
        return "👋 Welcome to Ketchup!\n\n"
                + "Here are the commands you can use:\n\n"
                + "📌 Add a To-Do\n"
                + "todo \"task description\"\n\n"
                + "⏰ Add a Deadline\n"
                + "deadline \"task description\" /by yyyy-MM-dd HHmm\n\n"
                + "📅 Add an Event\n"
                + "event \"event description\" /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm\n\n"
                + "📋 View All Tasks\n"
                + "list\n\n"
                + "✅ Mark a Task as Done\n"
                + "mark <task number>\n\n"
                + "↩ Unmark a Task\n"
                + "unmark <task number>\n\n"
                + "🗑 Delete a Task\n"
                + "delete <task number>\n\n"
                + "👋 Exit the Chatbot\n"
                + "bye\n\n"
                + "Type \"help\" anytime to see this guide again. "
                + "Let’s ketchup and get things done! 🍅";
    }

}
