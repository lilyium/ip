package baron;

import java.util.ArrayList;

import baron.exception.BaronException;
import baron.exception.WrongUsageException;
import baron.task.Task;

public class Ui {
    private static final String WELCOME_MSG = "Hello! I'm Baron.\nWhat can I do for you?";
    private static final String GOODBYE_MSG = "Bye. Hope to see you again soon!";

    /**
     * Returns a welcome message
     */
    public static String showWelcome() {
        return WELCOME_MSG;
    }

    /**
     * Returns a goodbye message
     */
    public static String showGoodbye() {
        return GOODBYE_MSG;
    }

    /**
     * Returns a message that indicates that a task has been marked as done
     *
     * @param task Task that is marked as done
     */
    public static String showMark(Task task) {
        assert task != null : "Task cannot be null";

        return "Nice! I've marked this task as done:\n" + task;
    }

    /**
     * Returns a message that indicates that a task has been marked as not done yet
     *
     * @param task Task that is marked as not done yet
     */
    public static String showUnmark(Task task) {
        assert task != null : "Task cannot be null";

        return "Nice! I've marked this task as not done yet:\n" + task;
    }

    /**
     * Returns a message that indicates that a task has been added
     *
     * @param task Task that is added
     */
    public static String showAddTask(Task task) {
        assert task != null : "Task cannot be null";

        return "Got it. I've added this task:\n  " + task.toString();
    }

    /**
     * Returns a message that indicates that a task had been deleted
     *
     * @param task Task that is deleted
     */
    public static String showDeleteTask(Task task) {
        assert task != null : "Task cannot be null";

        return "Noted. I've removed this task:\n  " + task.toString();
    }

    /**
     * Returns a message that indicates the number of tasks in a list
     *
     * @param taskList List of tasks
     */
    public static String showNumberOfTasks(ArrayList<Task> taskList) {
        assert taskList != null : "Task list cannot be null";

        int noOfTasks = taskList.size();
        if (noOfTasks == 0) {
            return "Now you have no tasks in the list.";
        } else if (noOfTasks == 1) {
            return "Now you have 1 task in the list.";
        } else {
            return "Now you have " + noOfTasks + " tasks in the list.";
        }
    }

    /**
     * Iterates through a list of tasks and returns its details
     *
     * @param taskList List of tasks
     */
    public static String showTasks(ArrayList<Task> taskList) {
        assert taskList != null : "Task list cannot be null";

        StringBuilder display = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            display.append("\n").append(i + 1).append(". ").append(taskList.get(i));
        }
        return display.toString();
    }

    /**
     * Iterates through a list of tasks that match a search term and returns its details
     *
     * @param matchList List of tasks that match a search term
     */
    public static String showMatchingTasks(ArrayList<Task> matchList) {
        assert matchList != null : "List of tasks that match a search term cannot be null";

        StringBuilder display = new StringBuilder("Here are the matching tasks in your list:");
        for (int i = 0; i < matchList.size(); i++) {
            display.append("\n").append(i + 1).append(". ").append(matchList.get(i));
        }
        return display.toString();

    }

    /**
     * Returns an error message corresponding to the exception thrown
     *
     * @param e An exception thrown by the application
     */
    public static String showError(BaronException e) {
        if (e instanceof WrongUsageException wrongUsageException) {
            switch (wrongUsageException.getCommandType()) {
            case LIST:
                return "Wrong usage of command! Try: list";
            case EXIT:
                return "Wrong usage of command! Try: bye";
            case MARK:
                return "Wrong usage of command! Try: mark [index]";
            case UNMARK:
                return "Wrong usage of command! Try: unmark [index]";
            case TODO:
                return "Wrong usage of command! Try: todo [description]";
            case DEADLINE:
                return "Wrong usage of command! Try: deadline [description] /by [deadline]";
            case EVENT:
                return "Wrong usage of command! Try: event [description] /from [start time] /to [end time]";
            default:
                return e.getMessage();
            }
        } else {
            return e.getMessage();
        }
    }
}
