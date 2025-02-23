package baron.exception;

/**
 * Exception that is thrown when the user inputs an unknown command
 */
public class InvalidCommandException extends BaronException {
    public InvalidCommandException(String keyword) {
        super("OOPS!!! " + keyword + " is not a recognised command.\nI'm sorry, but I don't know what that means :-(");
    }
}
