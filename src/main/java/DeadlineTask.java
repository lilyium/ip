import java.time.LocalDateTime;

public class DeadlineTask extends Task {
    private LocalDateTime deadline;

    public DeadlineTask(String taskDescription) throws EmptyDescriptionException {
        super(DeadlineTask.getTaskName(taskDescription));
        this.deadline = Parser.parseDateTime(DeadlineTask.getDeadline(taskDescription));
    }

    public DeadlineTask(boolean isDone, String taskName, String deadline) throws EmptyDescriptionException {
        super(isDone, taskName);
        this.deadline = Parser.parseDateTime(deadline);
    }

    private static String getTaskName(String taskDescription) {
        int idx = taskDescription.indexOf("/by");
        return taskDescription.substring(0, idx);
    }

    private static String getDeadline(String taskDescription) {
        int idx = taskDescription.indexOf("/by");
        return taskDescription.substring(idx + 4);
    }

    @Override
    public String toSaveFormat() {
        return "D | " + super.toSaveFormat() + " | " + this.deadline.format(DefaultFormat.dateTimeFormat());
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + this.deadline.format(DefaultFormat.dateTimeFormat()) + ")";
    }
}
