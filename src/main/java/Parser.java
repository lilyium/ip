import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

public class Parser {
    private static final String TODO_TASK = "T";
    private static final String DEADLINE_TASK = "D";
    private static final String EVENT_TASK = "E";

    public static Command parseCommand(String input) throws EmptyDescriptionException, InvalidCommandException, WrongUsageException, ReservedCharacterException, InvalidDateTimeException {
        String trimmed_input = input.trim();
        if (trimmed_input.equals("list")) {
            return new ListCommand();
        } else if (trimmed_input.equals("bye")) {
            return Command.getExitCommand();
        }

        String[] split_input = trimmed_input.split(" ", 2);
        if (split_input.length == 0) {
            return Command.getEmptyCommand();
        } else if (split_input.length == 1){
            throw new EmptyDescriptionException();
        } else {
            String keyword = split_input[0];
            String details = split_input[1].trim();
            switch (keyword) {
            case "mark":
                return parseMarkCommand(details);
            case "unmark":
                return parseUnmarkCommand(details);
            case "todo":
                return parseToDoCommand(details);
            case "deadline":
                return parseDeadlineCommand(details);
            case "event":
                return parseEventCommand(details);
            case "delete":
                return parseDeleteCommand(details);
            default:
                throw new InvalidCommandException();
            }
        }
    }

    private static MarkCommand parseMarkCommand(String details) throws WrongUsageException {
        try {
            return new MarkCommand(Integer.parseUnsignedInt(details));
        } catch (NumberFormatException e) {
            throw new WrongUsageException();
        }
    }

    private static UnmarkCommand parseUnmarkCommand(String details) throws WrongUsageException {
        try {
            return new UnmarkCommand(Integer.parseUnsignedInt(details));
        } catch (NumberFormatException e) {
            throw new WrongUsageException();
        }
    }

    public static ToDoCommand parseToDoCommand(String details) throws ReservedCharacterException {
        checkReservedCharacters(details);
        return new ToDoCommand(details);
    }

    public static DeadlineCommand parseDeadlineCommand(String details) throws WrongUsageException, ReservedCharacterException, EmptyDescriptionException, InvalidDateTimeException {
        int idx = details.indexOf("/by");
        if (idx == -1) {
            throw new WrongUsageException();
        }
        String taskName = details.substring(0, idx).trim();
        String deadline = details.substring(idx + 4).trim();
        checkReservedCharacters(taskName);
        checkReservedCharacters(deadline);
        if (taskName.isEmpty() || deadline.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        return new DeadlineCommand(taskName, parseDateTime(deadline));
    }

    public static EventCommand parseEventCommand(String details) throws WrongUsageException, ReservedCharacterException, EmptyDescriptionException, InvalidDateTimeException {
        int idx1 = details.indexOf("/from");
        int idx2 = details.indexOf("/to");
        if (idx1 == -1 || idx2 == -1) {
            throw new WrongUsageException();
        }
        String taskName = details.substring(0, idx1).trim();
        String startTime = details.substring(idx1 + 6, idx2).trim();
        String endTime = details.substring(idx2 + 4).trim();
        checkReservedCharacters(taskName);
        checkReservedCharacters(startTime);
        checkReservedCharacters(endTime);
        if (taskName.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        return new EventCommand(taskName, parseDateTime(startTime), parseDateTime(endTime));
    }

    public static DeleteCommand parseDeleteCommand(String details) throws WrongUsageException {
        try {
            return new DeleteCommand(Integer.parseUnsignedInt(details));
        } catch (NumberFormatException e) {
            throw new WrongUsageException();
        }
    }
    public static void checkReservedCharacters(String details) throws ReservedCharacterException {
        if (details.contains("|") || details.contains("/")) {
            throw new ReservedCharacterException();
        }
    }

    public static Task parseSavedTask(String savedTask) throws EmptyDescriptionException, InvalidDateTimeException {
        String[] splitSavedTask = savedTask.split(DefaultFormat.delimiter());
        switch (splitSavedTask[0]) {
        case TODO_TASK:
            return new ToDoTask(Boolean.parseBoolean(splitSavedTask[1]), splitSavedTask[2]);
        case DEADLINE_TASK:
            return new DeadlineTask(Boolean.parseBoolean(splitSavedTask[1]), splitSavedTask[2], parseDateTime(splitSavedTask[3]));
        case EVENT_TASK:
            return new EventTask(Boolean.parseBoolean(splitSavedTask[1]), splitSavedTask[2], parseDateTime(splitSavedTask[3]), parseDateTime(splitSavedTask[3]));
        default:
            throw new EmptyDescriptionException();
        }
    }

    public static LocalDateTime parseDateTime(String dateTimeString) throws InvalidDateTimeException {
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
        try {
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException();
        }
    }
}
