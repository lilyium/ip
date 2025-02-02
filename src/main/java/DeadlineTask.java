public class DeadlineTask extends Task {
    private String deadline;

    public DeadlineTask(String taskDescription) throws EmptyDescriptionException {
        super(DeadlineTask.getTaskName(taskDescription));
        this.deadline = DeadlineTask.getDeadline(taskDescription);
    }

    public DeadlineTask(boolean isDone, String taskName, String deadline) throws EmptyDescriptionException {
        super(isDone, taskName);
        this.deadline = deadline;
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
        return "D | " + super.toSaveFormat() + " | " + this.deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + this.deadline + ")";
    }
}
