package baron.command;

import baron.Storage;
import baron.Ui;
import baron.task.EventTask;
import baron.task.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventCommand extends Command {
    private final String taskName;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public EventCommand(String taskName, LocalDateTime startTime, LocalDateTime endTime) {
        this.taskName = taskName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public void execute(ArrayList<Task> taskList, Storage storage) {
        Task task = new EventTask(this.taskName, this.startTime, this.endTime);
        taskList.add(task);
        Ui.showAddTask(task);
        Ui.showNumberOfTasks(taskList);
        storage.saveTasks(taskList);
    }
}
