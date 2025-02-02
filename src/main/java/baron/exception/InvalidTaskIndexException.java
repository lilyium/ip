package baron.exception;

public class InvalidTaskIndexException extends BaronException {
    public InvalidTaskIndexException(int index) {
        super("There is no task with index " + index);
    }
}
