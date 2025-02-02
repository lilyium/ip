import java.util.ArrayList;

public class Baron {
    private final Storage storage;
    private final ArrayList<Task> taskList;

    public Baron(String filePath) {
        this.storage = new Storage(filePath);
        this.taskList = storage.loadSavedTasks();
    }

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
                System.out.println(e.getMessage());
            } finally {
                Ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Baron("./data/baron.txt").run();
    }
}
