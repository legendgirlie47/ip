package ketchup;

import java.util.Scanner;

import ketchup.parser.InputParser;
import ketchup.storage.Storage;
import ketchup.tasks.TaskList;
import ketchup.ui.Ui;

/**
 * The main entry point of the Ketchup application.
 * This class initializes core components and runs the main input loop.
 */
public class Ketchup {

    /** UI component responsible for user interaction. */
    private final Ui ui;

    /** Parser that interprets user input commands. */
    private final InputParser parser;

    /** List that stores all tasks in the application. */
    private final TaskList tasks;

    /**
     * Constructs a Ketchup instance by initializing the UI,
     * input parser, and loading stored tasks.
     */
    public Ketchup() {
        this.ui = new Ui();
        this.parser = new InputParser(ui);
        this.tasks = Storage.load();
    }

    /**
     * Runs the main program loop.
     * Continuously reads user input and processes commands
     * until the user chooses to exit.
     */
    public boolean run(String input) {
        boolean shouldExit = parser.handle(input, tasks);
        if (shouldExit) {
            ui.showGoodbye();
        }
        return shouldExit;
    }

    /**
     * The main method that starts the Ketchup application.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Hello!");
    }
}
