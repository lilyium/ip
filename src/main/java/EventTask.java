import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventTask extends Task {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public EventTask(String taskDescription) throws EmptyDescriptionException {
        super(EventTask.getTaskName(taskDescription));
        this.startTime = Parser.parseDateTime(EventTask.getStartTime(taskDescription));
        this.endTime = Parser.parseDateTime(EventTask.getEndTime(taskDescription));
    }

    public EventTask(boolean isDone, String taskName, String startTime, String endTime) throws EmptyDescriptionException {
        super(isDone, taskName);
        this.startTime = Parser.parseDateTime(startTime);
        this.endTime = Parser.parseDateTime(endTime);
    }


    private static String getTaskName(String taskDescription) {
        int idx = taskDescription.indexOf("/from");
        return taskDescription.substring(0, idx);
    }

    private static String getStartTime(String taskDescription) {
        int idxStart = taskDescription.indexOf("/from");
        int idxEnd = taskDescription.indexOf("/to");
        return taskDescription.substring(idxStart + 6, idxEnd);
    }

    private static String getEndTime(String taskDescription) {
        int idx = taskDescription.indexOf("/to");
        return taskDescription.substring(idx + 4);
    }

    @Override
    public String toSaveFormat() {
        return "D | " + super.toSaveFormat() + " | " + this.startTime.format(DefaultFormat.dateTimeFormat()) + " | " + this.endTime.format(DefaultFormat.dateTimeFormat());
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(from: " + this.startTime.format(DefaultFormat.dateTimeFormat()) + "to: " + this.endTime.format(DefaultFormat.dateTimeFormat()) + ")";
    }
}
