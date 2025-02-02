package baron.exception;

public class ReservedCharacterException extends BaronException {
    public ReservedCharacterException() {
        super("There is an error when processing the command. \"|\" is a reserved character.");
    }
}
