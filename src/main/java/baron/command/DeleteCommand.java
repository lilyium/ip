package baron.command;

import baron.Storage;
import baron.Ui;
import baron.exception.InvalidTaskIndexException;
import baron.task.Task;

import java.util.ArrayList;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(ArrayList<Task> taskList, Storage storage) throws InvalidTaskIndexException {
        try {
            Task task = taskList.remove(this.index - 1);
            Ui.showDeleteTask(task);
            Ui.showNumberOfTasks(taskList);
            storage.saveTasks(taskList);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidTaskIndexException();
        }
    }

}
