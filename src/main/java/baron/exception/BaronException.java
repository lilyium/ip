package baron.exception;

/**
 * Abstract base class for all self-defined exceptions to the Baron application
 */
public abstract class BaronException extends Exception {
    public BaronException(String message) {
        super(message);
    }
}
