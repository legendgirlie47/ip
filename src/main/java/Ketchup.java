import java.util.Scanner;

public class Ketchup {
    public static void main(String[] args) {
        String hello = "Hello! I'm Ketchup\nWhat can I do for you?\n";
        String goodbye = "Bye. Hope to see you again soon!";
        System.out.println(hello);

        Scanner sc = new Scanner(System.in);
        String[] tasks = new String[100];
        int taskCount = 0;
        boolean[] isDone = new boolean[100];

        while (true) {
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                System.out.println(goodbye);
                break;
            }
            if (input.startsWith("mark")) {
                int idx = input.charAt(5) - '0';
                System.out.println(idx);
                isDone[idx-1] = true;
                System.out.println("Marked task: " + "'" + tasks[idx-1] + "'" + " as done! :D");
                continue;
            }

            if (input.startsWith("unmark")) {
                int idx = input.charAt(7) - '0';
                System.out.println(idx);
                isDone[idx-1] = true;
                System.out.println("Unmarked task: " + "'" + tasks[idx-1] + "'" + " as done :(");
                continue;
            }

            if (input.equalsIgnoreCase("list")) {
                if (taskCount == 0) {
                    System.out.println("No tasks in your list.");
                } else {
                    for (int i =0; i < taskCount; i++) {
                        String done = "[ ]";
                        if(isDone[i]) {
                            done = "[X]";
                        }
                        System.out.println(done + " " + (i+1)+". "+ tasks[i]);
                    }

                }
            } else {
                tasks[taskCount] = input;
                isDone[taskCount] = false;
                taskCount++;
                System.out.println("added: " + input);
            }
        }
    }
}
