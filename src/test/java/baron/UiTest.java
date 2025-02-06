package baron;

import baron.command.Command;
import baron.exception.BaronException;
import baron.task.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


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
    private static final ByteArrayOutputStream stdoutCaptor = new ByteArrayOutputStream();
    private static final String lineSeparator = System.lineSeparator();

    @BeforeAll
    static void beforeAll() {
        taskList.add(new TaskStub(false, "done task"));
        taskList.add(new TaskStub(true, "not done task"));
    }


    @BeforeEach
    void beforeEach() {
        System.setOut(new PrintStream(stdoutCaptor));
        stdoutCaptor.reset();
    }

    @AfterEach
    void afterEach() {
        System.setOut(System.out);
    }

    @Test
    void showTasks_validTaskList_printTasks() {
        Ui.showTasks(taskList);
        assertEquals("Here are the tasks in your list:" + lineSeparator
                + "1. [ ] done task" + lineSeparator
                + "2. [X] not done task" + lineSeparator, stdoutCaptor.toString(), "Tasks are printed incorrectly");
    }

    @Test
    void showNumberOfTasks_validTaskList_printNumberOfTasks() {
        Ui.showNumberOfTasks(taskList);
        assertEquals("Now you have 2 tasks in the list." + lineSeparator, stdoutCaptor.toString(), "Number of tasks is printed incorrectly");
    }
}
