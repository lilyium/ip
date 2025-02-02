package baron.command;

import baron.Storage;
import baron.Ui;
import baron.exception.InvalidTaskIndexException;
import baron.task.Task;

import java.util.ArrayList;

public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(ArrayList<Task> taskList, Storage storage) throws InvalidTaskIndexException {
        try {
            Task task = taskList.get(this.index - 1);
            task.unmark();
            Ui.showUnmark(task);
            storage.saveTasks(taskList);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidTaskIndexException();
        }
    }
}
