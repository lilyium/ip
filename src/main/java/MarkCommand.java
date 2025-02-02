import java.util.ArrayList;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(ArrayList<Task> taskList, Storage storage) throws InvalidTaskIndexException {
        try {
            Task task = taskList.get(this.index - 1);
            task.mark();
            Ui.showMark(task);
            storage.saveTasks(taskList);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidTaskIndexException();
        }
    }
}
