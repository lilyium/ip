package baron.exception;

/**
 * Exception that is thrown when the user input contains reserved characters used internally as delimiters
 */
public class ReservedCharacterException extends BaronException {
    public ReservedCharacterException() {
        super("There is an error when processing the command. \"|\" is a reserved character.");
    }
}
