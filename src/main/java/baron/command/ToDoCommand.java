package baron.command;

import java.util.ArrayList;

import baron.task.Task;
import baron.task.ToDoTask;
import baron.Storage;
import baron.Ui;

public class ToDoCommand extends Command {
    private final String taskName;

    public ToDoCommand(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void execute(ArrayList<Task> taskList, Storage storage) {
        Task task = new ToDoTask(taskName);
        taskList.add(task);
        Ui.showAddTask(task);
        Ui.showNumberOfTasks(taskList);
        storage.saveTasks(taskList);
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
