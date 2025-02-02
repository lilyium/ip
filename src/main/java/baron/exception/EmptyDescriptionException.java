package baron.exception;

public class EmptyDescriptionException extends BaronException {
    @Override
    public String toString() {
        return "OOPS!!! The description of a task cannot be empty.";
    }
}
