public class Task {
    private String taskName;
    private boolean isDone;

    public Task(String taskName) throws EmptyDescriptionException {
        if (taskName.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        this.taskName = taskName;
        this.isDone = false;
    }

    public void mark() {
        this.isDone = true;
        System.out.println("Nice! I've marked this task as done:\n" + this);
    }

    public void unmark() {
        this.isDone = false;
        System.out.println("Nice! I've marked this task as not done yet:\n" + this);
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
