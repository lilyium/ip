package baron;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import baron.task.Task;

public class UiTest {
    private static class TaskStub extends Task {
        private final String taskName;
        private boolean isDone;

        public TaskStub(boolean isDone, String taskName) {
            super(isDone, taskName);
            this.taskName = taskName;
            this.isDone = isDone;
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

    private static final ArrayList<Task> taskList = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        taskList.add(new TaskStub(false, "done task"));
        taskList.add(new TaskStub(true, "not done task"));
    }

    @Test
    void showTasks_validTaskList_printTasks() {
        assertEquals("Here are the tasks in your list:\n1. [ ] done task\n2. [X] not done task", Ui.showTasks(taskList),
                "Tasks are printed incorrectly");
    }

    @Test
    void showNumberOfTasks_validTaskList_printNumberOfTasks() {
        assertEquals("Now you have 2 tasks in the list.", Ui.showNumberOfTasks(taskList),
                "Number of tasks is printed incorrectly");
    }
}
