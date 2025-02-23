package baron.exception;

/**
 * Exception that is thrown when a user inputs a task number that is out of range
 */
public class InvalidTaskIndexException extends BaronException {
    public InvalidTaskIndexException(int index) {
        super("There is no task with index " + index);
    }
}
