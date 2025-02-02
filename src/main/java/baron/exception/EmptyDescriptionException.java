package baron.exception;

public class EmptyDescriptionException extends BaronException {
    public EmptyDescriptionException() {
        super("OOPS!!! Fields cannot be empty.");
    }
}
