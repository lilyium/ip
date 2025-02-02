import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Parser {
    public static Task parseSavedTask(String savedTask) throws EmptyDescriptionException {
        return Task.createTask(savedTask.split(DefaultFormat.delimiter()));
    }

    public static LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofPattern(
                        "[yyyy-M-d]" + "[d-M-yyyy]" + "[d-M]" + "[yyyy/M/d]" + "[d/M/yyyy]" + "[d/M]"
                        + "[d MMM yyyy]" + "[d MMM]" + "[MMM d yyyy]" + "[MMM d]"
                ))
                .appendOptional(DateTimeFormatter.ofPattern(" " +
                        "[HHmm]" + "[HH:mm]"
                ))
                .parseCaseInsensitive()
                .parseDefaulting(ChronoField.YEAR_OF_ERA, LocalDateTime.now().getYear())
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 23)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 59)
                .toFormatter();
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
