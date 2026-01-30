import java.util.Scanner;

public class Ketchup {
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
