public class ToDoTask extends Task {
    public ToDoTask(String taskName) throws EmptyDescriptionException {
        super(taskName);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
