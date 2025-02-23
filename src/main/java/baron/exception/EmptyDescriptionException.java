package baron.exception;

/**
 * Exception that is thrown when the user input contains empty fields
 */
public class EmptyDescriptionException extends BaronException {
    public EmptyDescriptionException() {
        super("OOPS!!! Fields cannot be empty.");
    }
}
