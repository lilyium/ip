package baron.command;

import java.util.ArrayList;

import baron.exception.InvalidTaskIndexException;
import baron.task.Task;
import baron.Storage;
import baron.Ui;

/**
 * Class for command that deletes a task
 */
public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(ArrayList<Task> taskList, Storage storage) throws InvalidTaskIndexException {
        assert taskList != null : "Task list cannot be null";
        assert storage != null : "Storage cannot be null";

        try {
            Task task = taskList.remove(this.index - 1);
            storage.saveTasks(taskList);
            return Ui.showDeleteTask(task) + "\n" + Ui.showNumberOfTasks(taskList);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidTaskIndexException(index);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof DeleteCommand other) {
            return this.index == other.index;
        } else {
            return false;
        }
    }

}
