package baron;

import java.util.ArrayList;

import baron.command.Command;
import baron.exception.BaronException;
import baron.task.Task;

public class Baron {
    private static final String DEFAULTFILEPATH = "./data/baron.txt";

    private final Storage storage;
    private final ArrayList<Task> taskList;

    /**
     * Creates an instance of Baron with the specified storage file path
     *
     * @param filePath The storage file path
     */
    public Baron(String filePath) {
        this.storage = new Storage(filePath);
        this.taskList = storage.loadSavedTasks();
    }

    /**
     * Creates an instance of Baron with the default storage file path
     */
    public Baron() {
        this(DEFAULTFILEPATH);
    }

    /**
     * Returns the message to be displayed by the chatbot in response to the input
     *
     * @param input The user input
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parseCommand(input);
            return command.execute(taskList, storage);
        } catch (BaronException e) {
            return Ui.showError(e);
        }
    }

    /**
     * Runs the application, printing a welcome message and handling user input
     */
    public void run() {
        Ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = Ui.readCommand();
                Ui.showLine();
                Command c = Parser.parseCommand(fullCommand);
                c.execute(taskList, storage);
                isExit = c.isExit();
            } catch (BaronException e) {
                Ui.showError(e);
            } finally {
                Ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Baron("./data/baron.txt").run();
    }
}
