public abstract class Task {
    private final String taskName;
    private boolean isDone;

    public Task(String taskName) {
        this(false, taskName);
    }

    public Task(boolean isDone, String taskName) {
        this.taskName = taskName;
        this.isDone = isDone;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String toSaveFormat() {
        if (this.isDone) {
            return "true | " + this.taskName;
        } else {
            return "false | " + this.taskName;
        }
    }

    @Override
    public String toString() {
        if (this.isDone) {
            return "[X] " + this.taskName;
        } else {
            return "[ ] " + this.taskName;
        }
    }
}
