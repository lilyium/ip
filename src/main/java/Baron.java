import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        Save save = new Save("./data/duke.txt");
        ArrayList<Task> taskList = save.loadSavedTasks();
        String input = s.nextLine();

        while (!input.equals("bye")) {
            System.out.println(horizontalLine);
            try {
                if (input.equals("list")) {
                    for (int i = 0; i < taskList.size(); i++) {
                        System.out.println(i + 1 + ". " + taskList.get(i));
                    }
                } else if (input.startsWith("mark ")) {
                    taskList.get(Integer.parseInt(input.substring(5)) - 1).mark();
                } else if (input.startsWith("unmark ")) {
                    taskList.get(Integer.parseInt(input.substring(7)) - 1).unmark();
                } else if (input.startsWith("todo ")) {
                    taskList.add(new ToDoTask(input.substring(5)));
                    Task task = taskList.get(taskList.size() - 1);
                    System.out.println("Got it. I've added this task:\n  " + task
                            + "\nNow you have " + taskList.size() + " tasks in the list.");
                } else if (input.startsWith("deadline ")) {
                    taskList.add(new DeadlineTask(input.substring(9)));
                    Task task = taskList.get(taskList.size() - 1);
                    System.out.println("Got it. I've added this task:\n  " + task
                            + "\nNow you have " + taskList.size() + " tasks in the list.");
                } else if (input.startsWith("event ")) {
                    taskList.add(new EventTask(input.substring(6)));
                    Task task = taskList.get(taskList.size() - 1);
                    System.out.println("Got it. I've added this task:\n  " + task
                            + "\nNow you have " + taskList.size() + " tasks in the list.");
                } else if (input.startsWith("delete ")) {
                    Task task = taskList.remove(Integer.parseInt(input.substring(7)) - 1);
                    System.out.println("Noted. I've removed this task:\n  " + task
                            + "\nNow you have " + taskList.size() + " tasks in the list.");
                } else {
                    throw new InvalidCommandException();
                }
            } catch (BaronException e) {
                System.out.println(e);
            }
            System.out.println(horizontalLine);
            save.saveTasks(taskList);
            input = s.nextLine();
        }

        System.out.println(bye);
    }
}
