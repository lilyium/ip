public abstract class Task {
    private static final String TODO_TASK = "T";
    private static final String DEADLINE_TASK = "D";
    private static final String EVENT_TASK = "E";

    private String taskName;
    private boolean isDone;

    public Task(String taskName) throws EmptyDescriptionException {
        this(false, taskName);
    }

    public Task(boolean isDone, String taskName) throws EmptyDescriptionException {
        if (taskName.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        this.taskName = taskName;
        this.isDone = isDone;
    }

    public static Task createTask(String[] parsedTask) throws EmptyDescriptionException {
        switch (parsedTask[0]) {
        case TODO_TASK:
            return new ToDoTask(Boolean.parseBoolean(parsedTask[1]), parsedTask[2]);
        case DEADLINE_TASK:
            return new DeadlineTask(Boolean.parseBoolean(parsedTask[1]), parsedTask[2], parsedTask[3]);
        case EVENT_TASK:
            return new EventTask(Boolean.parseBoolean(parsedTask[1]), parsedTask[2], parsedTask[3], parsedTask[4]);
        default:
            throw new EmptyDescriptionException();
        }
    }


    public void mark() {
        this.isDone = true;
        System.out.println("Nice! I've marked this task as done:\n" + this);
    }

    public void unmark() {
        this.isDone = false;
        System.out.println("Nice! I've marked this task as not done yet:\n" + this);
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
