package baron.task;

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

    /**
     * Mark a task as done
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Mark a task as not yet done
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of a task in the save format
     *
     * @return String representation of a task
     */
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
