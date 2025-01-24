public class DeadlineTask extends Task {
    private String deadline;

    public DeadlineTask(String taskDescription) {
        super(DeadlineTask.getTaskName(taskDescription));
        this.deadline = DeadlineTask.getDeadline(taskDescription);
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
    public String toString() {
        return "[T]" + super.toString() + "(by: " + this.deadline + ")";
    }
}
