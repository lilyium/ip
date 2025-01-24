import java.util.ArrayList;
import java.util.Scanner;

public class Baron {
    private static String horizontalLine = "____________________________________________________________";
    private static String hello = horizontalLine + "\n" +
            "Hello! I'm Baron.\n" +
            "What can I do for you?\n" +
            horizontalLine;

    private static String bye = "Bye. Hope to see you again soon!\n" +
            horizontalLine;


    public static void main(String[] args) {
        System.out.println(hello);
        Scanner s = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<Task>();
        String input = s.nextLine();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                System.out.println(horizontalLine);
                for (int i = 0; i < taskList.size(); i++) {
                    System.out.println(i + 1 + ". " + taskList.get(i));
                }
                System.out.println(horizontalLine);
            } else {
                taskList.add(new Task(input));
                System.out.println(horizontalLine + "\n added: " + input + "\n" + horizontalLine);
            }
            input = s.nextLine();
        }

        System.out.println(bye);
    }
}
