package baron.exception;

public class CorruptedSaveException extends BaronException {
    public CorruptedSaveException() {
        super("Unable to load tasks! Save file has been corrupted :(");
    }
}
