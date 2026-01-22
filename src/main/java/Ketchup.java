import java.util.Scanner;

public class Ketchup {
    public static void main(String[] args) {
        String hello = "Hello! I'm Ketchup\nWhat can I do for you?\n";
        String goodbye = "Bye. Hope to see you again soon!";
        System.out.println(hello);

        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                System.out.println(goodbye);
                break;
            } else {
                System.out.println(input);
            }
        }

    }
}
