package baron;

import java.util.ArrayList;

import baron.command.Command;
import baron.exception.BaronException;
import baron.task.Task;

public class Baron {
    private final Storage storage;
    private final ArrayList<Task> taskList;

    public Baron(String filePath) {
        this.storage = new Storage(filePath);
        this.taskList = storage.loadSavedTasks();
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
