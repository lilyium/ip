import java.time.LocalDateTime;

public class DeadlineTask extends Task {
    private LocalDateTime deadline;

    public DeadlineTask(String taskName, LocalDateTime deadline) {
        super(taskName);
        this.deadline = deadline;
    }

    public DeadlineTask(boolean isDone, String taskName, LocalDateTime deadline) {
        super(isDone, taskName);
        this.deadline = deadline;
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
