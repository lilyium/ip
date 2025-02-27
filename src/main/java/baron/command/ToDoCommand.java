package baron.command;

import java.util.ArrayList;

import baron.task.Task;
import baron.task.ToDoTask;
import baron.Storage;
import baron.Ui;

/**
 * Class for command that adds a todo task
 */
public class ToDoCommand extends Command {
    private final String taskName;

    public ToDoCommand(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String execute(ArrayList<Task> taskList, Storage storage) {
        assert taskList != null : "Task list cannot be null";
        assert storage != null : "Storage cannot be null";

        Task task = new ToDoTask(taskName);
        taskList.add(task);
        storage.saveTasks(taskList);
        return Ui.showAddTask(task) + "\n" + Ui.showNumberOfTasks(taskList);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof ToDoCommand other) {
            return this.taskName.equals(other.taskName);
        } else {
            return false;
        }
    }
}
