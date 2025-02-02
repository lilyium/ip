package baron.task;

public class ToDoTask extends Task {
    public ToDoTask(String taskName) {
        this(false, taskName);
    }

    public ToDoTask(boolean isDone, String taskName) {
        super(isDone, taskName);
    }

    @Override
    public String toSaveFormat() {
        return "T | " + super.toSaveFormat();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
