package baron.command;

import baron.Storage;
import baron.Ui;
import baron.task.Task;

import java.util.ArrayList;

public class ListCommand extends Command {
    @Override
    public void execute(ArrayList<Task> taskList, Storage storage) {
        Ui.showTasks(taskList);
    }
}
