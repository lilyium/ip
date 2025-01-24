public class EmptyDescriptionException extends BaronException {
    @Override
    public String toString() {
        return "OOPS!!! The description of a todo cannot be empty.";
    }
}
