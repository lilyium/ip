import java.time.format.DateTimeFormatter;

public class DefaultFormat {
    private static final String DELIMITER = " \\| ";
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public static String delimiter() {
        return DefaultFormat.DELIMITER;
    }

    public static DateTimeFormatter dateTimeFormat() {
        return DefaultFormat.dateTimeFormat;
    }
}
