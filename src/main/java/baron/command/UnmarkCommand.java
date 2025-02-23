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
    public String execute(ArrayList<Task> taskList, Storage storage) throws InvalidTaskIndexException {
        assert taskList != null : "Task list cannot be null";
        assert storage != null : "Storage cannot be null";

        try {
            Task task = taskList.get(this.index - 1);
            task.unmark();
            storage.saveTasks(taskList);
            return Ui.showUnmark(task);
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
