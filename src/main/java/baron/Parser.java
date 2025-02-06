package baron;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

import baron.command.Command;
import baron.command.Command.CommandType;
import baron.command.DeadlineCommand;
import baron.command.DeleteCommand;
import baron.command.EventCommand;
import baron.command.MarkCommand;
import baron.command.ToDoCommand;
import baron.command.UnmarkCommand;
import baron.task.DeadlineTask;
import baron.task.EventTask;
import baron.task.Task;
import baron.task.ToDoTask;
import baron.exception.CorruptedSaveException;
import baron.exception.EmptyDescriptionException;
import baron.exception.InvalidCommandException;
import baron.exception.InvalidDateTimeException;
import baron.exception.ReservedCharacterException;
import baron.exception.WrongUsageException;

public class Parser {
    private static final String TODO_TASK = "T";
    private static final String DEADLINE_TASK = "D";
    private static final String EVENT_TASK = "E";

    private static final String DELIMITER = " \\| ";
    public static final DateTimeFormatter DATETIMEFORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public static Command parseCommand(String input) throws EmptyDescriptionException, InvalidCommandException, WrongUsageException, ReservedCharacterException, InvalidDateTimeException {
        String trimmed_input = input.trim();
        String[] split_input = trimmed_input.split(" ", 2);
        if (trimmed_input.isEmpty()) {
            return Command.EMPTY_COMMAND;
        } else if (split_input.length == 1) {
            String keyword = split_input[0];
            switch (keyword) {
            case "list":
                return Command.LIST_COMMAND;
            case "bye":
                return Command.EXIT_COMMAND;
            case "mark":
                throw new WrongUsageException(CommandType.MARK);
            case "unmark":
                throw new WrongUsageException(CommandType.UNMARK);
            case "todo":
                throw new WrongUsageException(CommandType.TODO);
            case "deadline":
                throw new WrongUsageException(CommandType.DEADLINE);
            case "event":
                throw new WrongUsageException(CommandType.EVENT);
            case "delete":
                throw new WrongUsageException(CommandType.DELETE);
            default:
                throw new InvalidCommandException(keyword);
            }
        } else {
            String keyword = split_input[0];
            String details = split_input[1].trim();
            switch (keyword) {
            case "list":
                throw new WrongUsageException(CommandType.LIST);
            case "bye":
                throw new WrongUsageException(CommandType.EXIT);
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
                throw new InvalidCommandException(keyword);
            }
        }
    }

    private static MarkCommand parseMarkCommand(String details) throws WrongUsageException {
        try {
            return new MarkCommand(Integer.parseUnsignedInt(details));
        } catch (NumberFormatException e) {
            throw new WrongUsageException(CommandType.MARK);
        }
    }

    private static UnmarkCommand parseUnmarkCommand(String details) throws WrongUsageException {
        try {
            return new UnmarkCommand(Integer.parseUnsignedInt(details));
        } catch (NumberFormatException e) {
            throw new WrongUsageException(CommandType.UNMARK);
        }
    }

    private static ToDoCommand parseToDoCommand(String details) throws ReservedCharacterException {
        checkReservedCharacters(details);
        return new ToDoCommand(details);
    }

    private static DeadlineCommand parseDeadlineCommand(String details) throws WrongUsageException, ReservedCharacterException, EmptyDescriptionException, InvalidDateTimeException {
        int idx = details.indexOf("/by");
        if (idx == -1) {
            throw new WrongUsageException(CommandType.DEADLINE);
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

    private static EventCommand parseEventCommand(String details) throws WrongUsageException, ReservedCharacterException, EmptyDescriptionException, InvalidDateTimeException {
        int idx1 = details.indexOf("/from");
        int idx2 = details.indexOf("/to");
        if (idx1 == -1 || idx2 == -1) {
            throw new WrongUsageException(CommandType.EVENT);
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

    private static DeleteCommand parseDeleteCommand(String details) throws WrongUsageException {
        try {
            return new DeleteCommand(Integer.parseUnsignedInt(details));
        } catch (NumberFormatException e) {
            throw new WrongUsageException(CommandType.DELETE);
        }
    }

    private static void checkReservedCharacters(String details) throws ReservedCharacterException {
        if (details.contains("|")) {
            throw new ReservedCharacterException();
        }
    }

    public static Task parseSavedTask(String savedTask) throws EmptyDescriptionException, InvalidDateTimeException, CorruptedSaveException {
        String[] splitSavedTask = savedTask.split(DELIMITER);
        try {
            switch (splitSavedTask[0]) {
            case TODO_TASK:
                return new ToDoTask(Boolean.parseBoolean(splitSavedTask[1]), splitSavedTask[2]);
            case DEADLINE_TASK:
                return new DeadlineTask(Boolean.parseBoolean(splitSavedTask[1]), splitSavedTask[2], parseDateTime(splitSavedTask[3]));
            case EVENT_TASK:
                return new EventTask(Boolean.parseBoolean(splitSavedTask[1]), splitSavedTask[2], parseDateTime(splitSavedTask[3]), parseDateTime(splitSavedTask[3]));
            default:
                throw new CorruptedSaveException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CorruptedSaveException();
        }
    }

    public static LocalDateTime parseDateTime(String dateTimeString) throws InvalidDateTimeException {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
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
            return LocalDateTime.parse(dateTimeString, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeException(dateTimeString);
        }
    }
}
