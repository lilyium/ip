package baron.task;

import java.time.LocalDateTime;

import baron.Parser;

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
        return "D | " + super.toSaveFormat() + " | " + this.deadline.format(Parser.DATETIMEFORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline.format(Parser.DATETIMEFORMAT) + ")";
    }
}
