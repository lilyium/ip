import java.util.ArrayList;

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
}
