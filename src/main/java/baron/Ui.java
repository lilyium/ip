package baron;

import java.util.ArrayList;
import java.util.Scanner;

import baron.exception.BaronException;
import baron.exception.WrongUsageException;
import baron.task.Task;

public class Ui {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String WELCOME_MSG = "Hello! I'm Baron.\nWhat can I do for you?";
    private static final String GOODBYE_MSG = "Bye. Hope to see you again soon!";

    /**
     * Prints a horizontal line
     */
    public static void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Prints a welcome message
     */
    public static void showWelcome() {
        System.out.println(HORIZONTAL_LINE + "\n" + WELCOME_MSG + "\n" + HORIZONTAL_LINE);
    }

    /**
     * Prints a goodbye message
     */
    public static void showGoodbye() {
        System.out.println(GOODBYE_MSG);
    }

    /**
     * Reads the user input
     *
     * @return The user input
     */
    public static String readCommand() {
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    /**
     * Prints a message that indicates that a task has been marked as done
     *
     * @param task Task that is marked as done
     */
    public static void showMark(Task task) {
        System.out.println("Nice! I've marked this task as done:\n" + task);
    }

    /**
     * Prints a message that indicates that a task has been marked as not done yet
     *
     * @param task Task that is marked as not done yet
     */
    public static void showUnmark(Task task) {
        System.out.println("Nice! I've marked this task as not done yet:\n" + task);
    }

    /**
     * Prints a message that indicates that a task has been added
     *
     * @param task Task that is added
     */
    public static void showAddTask(Task task) {
        System.out.println("Got it. I've added this task:\n  " + task);
    }

    /**
     * Prints a message that indicates that a task had been deleted
     *
     * @param task Task that is deleted
     */
    public static void showDeleteTask(Task task) {
        System.out.println("Noted. I've removed this task:\n  " + task);
    }

    /**
     * Prints a message that indicates the number of tasks in a list
     *
     * @param taskList List of tasks
     */
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

    /**
     * Iterates through a list of tasks and prints out its details
     *
     * @param taskList List of tasks
     */
    public static void showTasks(ArrayList<Task> taskList) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(i + 1 + ". " + taskList.get(i));
        }
    }

    /**
     * Iterates through a list of tasks that match a search term and prints out its details
     *
     * @param matchList List of tasks that match a search term
     */
    public static void showMatchingTasks(ArrayList<Task> matchList) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matchList.size(); i++) {
            System.out.println(i + 1 + ". " + matchList.get(i));
        }
    }

    /**
     * Prints out an error message corresponding to the exception thrown
     *
     * @param e An exception thrown by the application
     */
    public static void showError(BaronException e) {
        if (e instanceof WrongUsageException wrongUsageException) {
            switch (wrongUsageException.getCommandType()) {
            case LIST:
                System.out.println("Wrong usage of command! Try: list");
                break;
            case EXIT:
                System.out.println("Wrong usage of command! Try: bye");
                break;
            case MARK:
                System.out.println("Wrong usage of command! Try: mark [index]");
                break;
            case UNMARK:
                System.out.println("Wrong usage of command! Try: unmark [index]");
                break;
            case TODO:
                System.out.println("Wrong usage of command! Try: todo [description]");
                break;
            case DEADLINE:
                System.out.println("Wrong usage of command! Try: deadline [description] /by [deadline]");
                break;
            case EVENT:
                System.out.println("Wrong usage of command! Try: event [description] /from [start time] /to [end time]");
                break;
            }
        } else {
            System.out.println(e.getMessage());
        }
    }
}
