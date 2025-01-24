import java.util.ArrayList;
import java.util.Scanner;

public class Baron {
    private static String horizontalLine = "____________________________________________________________";
    private static String hello = horizontalLine +
            "\nHello! I'm Baron.\n" +
            "What can I do for you?\n" +
            horizontalLine;
    private static String bye = horizontalLine +
            "\nBye. Hope to see you again soon!\n" +
            horizontalLine;

    public static void main(String[] args) {
        System.out.println(hello);
        Scanner s = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<Task>();
        String input = s.nextLine();

        while (!input.equals("bye")) {
            System.out.println(horizontalLine);
            if (input.equals("list")) {
                for (int i = 0; i < taskList.size(); i++) {
                    System.out.println(i + 1 + ". " + taskList.get(i));
                }
            } else if (input.startsWith("mark ")) {
                taskList.get(Integer.parseInt(input.substring(5)) - 1).mark();
            } else if (input.startsWith("unmark ")) {
                taskList.get(Integer.parseInt(input.substring(7)) - 1).unmark();
            } else {
                taskList.add(new Task(input));
                System.out.println("added: " + input);
            }
            System.out.println(horizontalLine);
            input = s.nextLine();
        }
        System.out.println(bye);
    }
}
