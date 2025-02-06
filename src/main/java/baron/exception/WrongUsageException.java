package baron.exception;

import baron.command.Command.CommandType;

public class WrongUsageException extends BaronException {
    private final CommandType commandType;

    public WrongUsageException(CommandType commandType) {
        super("Wrong usage of command! Please check your input again.");
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof WrongUsageException other) {
            return this.commandType.equals(other.commandType);
        } else {
            return false;
        }
    }
}
