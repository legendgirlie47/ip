package ketchup;

import ketchup.parser.InputParser;
import ketchup.storage.Storage;
import ketchup.tasks.TaskList;
import ketchup.ui.Ui;

/**
 * The main entry point of the Ketchup application.
 * This class initializes core components and delegates
 * user input processing to the parser.
 */
public class Ketchup {

    /** Parser that interprets user input commands. */
    private final InputParser parser;

    /** List that stores all tasks in the application. */
    private final TaskList tasks;

    /**
     * Constructs a Ketchup instance by initializing the UI,
     * input parser, and loading stored tasks.
     */
    public Ketchup() {
        Ui ui = new Ui();
        this.parser = new InputParser(ui);
        this.tasks = Storage.load();

        // Internal invariants
        assert ui != null : "UI must be initialized";
        assert this.parser != null : "Parser must be initialized";
        assert this.tasks != null : "TaskList must be loaded";
    }

    /**
     * Processes a single user input string and returns
     * the result produced by the input parser.
     *
     * @param input the raw user input
     * @return the result of processing the input
     */
    public KetchupResult getResult(String input) {

        // tasks should always exist
        assert this.tasks != null : "TaskList must not be null";

        KetchupResult result = parser.handle(input, tasks);

        // parser must always return a result
        assert result != null : "Parser must return a non-null KetchupResult";

        return result;
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
