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
        assert filePath != null : "File path string cannot be null";

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
        assert input != null : "Input cannot be null";

        try {
            Command command = Parser.parseCommand(input);
            return command.execute(taskList, storage);
        } catch (BaronException e) {
            return Ui.showError(e);
        }
    }
}
