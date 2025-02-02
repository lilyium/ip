public class EventTask extends Task {
    private String startTime;
    private String endTime;

    public EventTask(String taskDescription) throws EmptyDescriptionException {
        super(EventTask.getTaskName(taskDescription));
        this.startTime = EventTask.getStartTime(taskDescription);
        this.endTime = EventTask.getEndTime(taskDescription);
    }

    public EventTask(boolean isDone, String taskName, String startTime, String endTime) throws EmptyDescriptionException {
        super(isDone, taskName);
        this.startTime = startTime;
        this.endTime = endTime;
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
        return "D | " + super.toSaveFormat() + " | " + this.startTime + " | " + this.endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(from: " + this.startTime + "to: " + this.endTime + ")";
    }
}
