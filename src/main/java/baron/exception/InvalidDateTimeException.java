package baron.exception;

public class InvalidDateTimeException extends BaronException {
    public InvalidDateTimeException(String dateTimeString) {
        super(dateTimeString + " is not a recognised Date/Time format\n Try one of the following:"
                + "dd-MM, dd-MM-yyyy, yyyy-MM-dd [HH:mm]");
    }
}
