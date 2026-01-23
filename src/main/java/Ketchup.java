import java.util.Scanner;

public class Ketchup {
    public static void main(String[] args) {
        String hello = "Hello! I'm Ketchup\nWhat can I do for you?\n";
        String goodbye = "Bye. Hope to see you again soon!";
        System.out.println(hello);

        Scanner sc = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                System.out.println(goodbye);
                break;
            }
            if (input.startsWith("mark")) {
                int idx = input.charAt(5) - '0';
                System.out.println(idx);
                Task temp = tasks[idx-1];
                temp.markDone();
                System.out.println("Marked task: " + "'" + temp.getDesc() + "'" + " as done! :D");
                continue;
            }

            if (input.startsWith("unmark")) {
                int idx = input.charAt(7) - '0';
                System.out.println(idx);
                Task temp = tasks[idx-1];
                temp.markUndone();
                System.out.println("Unmarked task: " + "'" + temp.getDesc() + "'" + " as done :(");
                continue;
            }

            if (input.equalsIgnoreCase("list")) {
                if (taskCount == 0) {
                    System.out.println("No tasks in your list.");
                } else {
                    for (int i =0; i < taskCount; i++) {
                        System.out.println((i+1)+". "+ tasks[i].toString());
                    }

                }
            } else {
                if (input.startsWith("todo")) {
                    String todo = input.substring(5).trim();
                    tasks[taskCount] = new ToDo(todo);
                    System.out.println("Sure! I have added todo: " + todo);
                    taskCount++;
                    System.out.println("You now have " + taskCount + " tasks in your list!");
                }

                if (input.startsWith("deadline")) {
                    int byIndex = input.indexOf(" /by ");
                    String desc = input.substring(8, byIndex).trim();
                    String by = input.substring(byIndex + 5);
                    tasks[taskCount] = new Deadline(desc, by);
                    System.out.println("Sure! I have added deadline: " + desc);
                    taskCount++;
                    System.out.println("You now have " + taskCount + " tasks in your list!");
                }

                if (input.startsWith("event")) {
                    int fromIndex = input.indexOf(" /from ");
                    int toIndex = input.indexOf(" /to ");
                    String desc = input.substring(5, fromIndex).trim();
                    String from = input.substring(fromIndex + 7, toIndex);
                    String to = input.substring(toIndex + 5);
                    tasks[taskCount] = new Event(desc, from, to);
                    System.out.println("Sure! I have added event: " + desc);
                    taskCount++;
                    System.out.println("You now have " + taskCount + " tasks in your list!");
                }
            }
        }
    }
}
