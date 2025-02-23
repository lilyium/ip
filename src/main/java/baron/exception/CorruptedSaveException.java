package baron.exception;

/**
 * Exception that is thrown when the save file cannot be read
 */
public class CorruptedSaveException extends BaronException {
    public CorruptedSaveException() {
        super("Unable to load tasks! Save file has been corrupted :(");
    }
}
