package baron.command;

import java.util.ArrayList;

import baron.exception.InvalidTaskIndexException;
import baron.task.Task;
import baron.Storage;
import baron.Ui;

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
            throw new InvalidTaskIndexException(index);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof UnmarkCommand other) {
            return this.index == other.index;
        } else {
            return false;
        }
    }
}
