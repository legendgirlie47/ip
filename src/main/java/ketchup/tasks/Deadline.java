package ketchup.tasks;

import ketchup.parser.InputParser;
import ketchup.storage.Storage;
import ketchup.ui.Ui;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Deadline extends Task {
    private LocalDateTime deadline;

    public Deadline(String desc, LocalDateTime deadline) {
        super(desc);
        this.deadline = deadline;
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    @Override
    public String toFileString() {
        return ("D | " + (this.isDone()? "1": "0") + " | " + this.getDesc() + " | "
                + this.deadline.format(Task.getDateFormat()));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline.format(Task.getDateFormat()) + ")";
    }

    public static class Ketchup {
        private final Ui ui;
        private final InputParser parser;
        private final TaskList tasks;

        public Ketchup() {
            this.ui = new Ui();
            this.parser = new InputParser(ui);
            this.tasks = Storage.load();
        }

        public void run() {
            ui.showHello();

            Scanner sc = new Scanner(System.in);
            while (true) {
                String input = sc.nextLine();
                boolean shouldExit = parser.handle(input, tasks);
                if (shouldExit) {
                    ui.showGoodbye();
                    break;
                }
            }
        }

        public static void main(String[] args) {
            new Ketchup().run();
        }

    }
}
