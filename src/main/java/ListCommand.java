import java.util.ArrayList;

public class ListCommand extends Command {
    @Override
    public void execute(ArrayList<Task> taskList, Storage storage) {
        Ui.showTasks(taskList);
    }
}
