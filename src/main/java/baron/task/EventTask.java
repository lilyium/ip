package baron.task;

import baron.Parser;

import java.time.LocalDateTime;

public class EventTask extends Task {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public EventTask(String taskName, LocalDateTime startTime, LocalDateTime endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EventTask(boolean isDone, String taskName, LocalDateTime startTime, LocalDateTime endTime) {
        super(isDone, taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toSaveFormat() {
        return "D | " + super.toSaveFormat() + " | " + this.startTime.format(Parser.DATETIMEFORMAT) + " | " + this.endTime.format(Parser.DATETIMEFORMAT);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.startTime.format(Parser.DATETIMEFORMAT) + "to: " + this.endTime.format(Parser.DATETIMEFORMAT) + ")";
    }
}
