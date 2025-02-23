package baron.command;

import java.time.LocalDateTime;
import java.util.ArrayList;

import baron.task.EventTask;
import baron.task.Task;
import baron.Storage;
import baron.Ui;

public class EventCommand extends Command {
    private final String taskName;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public EventCommand(String taskName, LocalDateTime startTime, LocalDateTime endTime) {
        assert taskName != null : "Task name cannot be null";
        assert startTime != null : "Start time cannot be null";
        assert endTime != null : "End time cannot be null";

        this.taskName = taskName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String execute(ArrayList<Task> taskList, Storage storage) {
        assert taskList != null : "Task list cannot be null";
        assert storage != null : "Storage cannot be null";

        Task task = new EventTask(this.taskName, this.startTime, this.endTime);
        taskList.add(task);
        storage.saveTasks(taskList);
        return Ui.showAddTask(task) + "\n" + Ui.showNumberOfTasks(taskList);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof EventCommand other) {
            return this.taskName.equals(other.taskName) && this.startTime.equals(other.startTime)
                    && this.endTime.equals(other.endTime);
        } else {
            return false;
        }
    }
}
