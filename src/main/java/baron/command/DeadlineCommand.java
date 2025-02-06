package baron.command;

import baron.Storage;
import baron.Ui;
import baron.exception.WrongUsageException;
import baron.task.DeadlineTask;
import baron.task.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DeadlineCommand extends Command {
    private final String taskName;
    private final LocalDateTime deadline;

    public DeadlineCommand(String taskName, LocalDateTime deadline) {
        this.taskName = taskName;
        this.deadline = deadline;
    }

    @Override
    public void execute(ArrayList<Task> taskList, Storage storage) {
        Task task = new DeadlineTask(this.taskName, this.deadline);
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

        if (o instanceof DeadlineCommand other) {
            return this.taskName.equals(other.taskName) && this.deadline.equals(other.deadline);
        } else {
            return false;
        }
    }
}
