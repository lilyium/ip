package baron.command;

import java.util.ArrayList;

import baron.exception.BaronException;
import baron.task.Task;
import baron.Storage;
import baron.Ui;

public abstract class Command {
    public enum CommandType {
        LIST,
        EXIT,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE,
        FIND
    }

    public static final Command EMPTY_COMMAND = new EmptyCommand();
    public static final Command LIST_COMMAND = new ListCommand();
    public static final Command EXIT_COMMAND = new ExitCommand();

    public abstract String execute(ArrayList<Task> taskList, Storage storage) throws BaronException;

    public boolean isExit() {
        return false;
    }

    @Override
    public abstract boolean equals(Object o);

    private static class EmptyCommand extends Command {
        @Override
        public String execute(ArrayList<Task> taskList, Storage storage) {
            return "Meow, say something!";
        }

        @Override
        public boolean equals(Object o) {
            return this == o;
        }
    }

    private static class ListCommand extends Command {
        @Override
        public String execute(ArrayList<Task> taskList, Storage storage) {
            return Ui.showTasks(taskList);
        }

        @Override
        public boolean equals(Object o) {
            return this == o;
        }
    }

    private static class ExitCommand extends Command {
        @Override
        public String execute(ArrayList<Task> taskList, Storage storage) {
            return Ui.showGoodbye();
        }


        @Override
        public boolean isExit() {
            return true;
        }

        @Override
        public boolean equals(Object o) {
            return this == o;
        }
    }
}
