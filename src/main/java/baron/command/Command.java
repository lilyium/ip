package baron.command;

import baron.Storage;
import baron.Ui;
import baron.exception.BaronException;
import baron.task.Task;

import java.util.ArrayList;

public abstract class Command {
    public static final Command EMPTY_COMMAND = new EmptyCommand();
    public static final Command EXIT_COMMAND = new ExitCommand();

    public abstract void execute(ArrayList<Task> taskList, Storage storage) throws BaronException;

    public boolean isExit() {
        return false;
    }

    public static Command getEmptyCommand() {
        return Command.EMPTY_COMMAND;
    }

    public static Command getExitCommand() {
        return Command.EXIT_COMMAND;
    }


    private static class EmptyCommand extends Command {
        @Override
        public void execute(ArrayList<Task> taskList, Storage storage) {

        }
    }

    private static class ExitCommand extends Command {
        @Override
        public void execute(ArrayList<Task> taskList, Storage storage) {
            Ui.showGoodbye();
        }

        @Override
        public boolean isExit() {
            return true;
        }
    }
}
