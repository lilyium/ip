package baron.command;

import java.time.LocalDateTime;
import java.util.ArrayList;

import baron.task.DeadlineTask;
import baron.task.Task;
import baron.Storage;
import baron.Ui;

public class DeadlineCommand extends Command {
    private final String taskName;
    private final LocalDateTime deadline;

    public DeadlineCommand(String taskName, LocalDateTime deadline) {
        this.taskName = taskName;
        this.deadline = deadline;
    }

    @Override
    public String execute(ArrayList<Task> taskList, Storage storage) {
        Task task = new DeadlineTask(this.taskName, this.deadline);
        taskList.add(task);
        storage.saveTasks(taskList);
        return Ui.showAddTask(task) + "\n" + Ui.showNumberOfTasks(taskList);
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
