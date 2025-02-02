import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String WELCOME_MSG = "Hello! I'm Baron.\nWhat can I do for you?";
    private static final String GOODBYE_MSG = "Bye. Hope to see you again soon!";

    public static void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    public static void showWelcome() {
        System.out.println(HORIZONTAL_LINE + "\n" + WELCOME_MSG + "\n" + HORIZONTAL_LINE);
    }

    public static void showGoodbye() {
        System.out.println(GOODBYE_MSG);
    }

    public static String readCommand() {
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    public static void showMark(Task task) {
        System.out.println("Nice! I've marked this task as done:\n" + task);
    }

    public static void showUnmark(Task task) {
        System.out.println("Nice! I've marked this task as not done yet:\n" + task);
    }

    public static void showAddTask(Task task) {
        System.out.println("Got it. I've added this task:\n  " + task);
    }

    public static void showDeleteTask(Task task) {
        System.out.println("Noted. I've removed this task:\n  " + task);
    }

    public static void showNumberOfTasks(ArrayList<Task> taskList) {
        int noOfTasks = taskList.size();
        if (noOfTasks == 0) {
            System.out.println("Now you have no tasks in the list.");
        } else if (noOfTasks == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.println("Now you have " + noOfTasks + " tasks in the list.");
        }
    }

    public static void showTasks(ArrayList<Task> taskList) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(i + 1 + ". " + taskList.get(i));
        }
    }
}
