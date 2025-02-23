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
import baron.command.FindCommand;
import baron.command.MarkCommand;
import baron.command.ToDoCommand;
import baron.command.UnmarkCommand;
import baron.exception.BaronException;
import baron.exception.CorruptedSaveException;
import baron.exception.EmptyDescriptionException;
import baron.exception.InvalidCommandException;
import baron.exception.InvalidDateTimeException;
import baron.exception.ReservedCharacterException;
import baron.exception.WrongUsageException;
import baron.task.DeadlineTask;
import baron.task.EventTask;
import baron.task.Task;
import baron.task.ToDoTask;

/**
 * Parser interprets the user input and save file contents
 */
public class Parser {
    public static final DateTimeFormatter DATETIMEFORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
    private static final String DELIMITER = " \\| ";

    private static final String TODO_TASK = "T";
    private static final String DEADLINE_TASK = "D";
    private static final String EVENT_TASK = "E";


    /**
     * Parses the user input and returns a corresponding Command object
     *
     * @param input The user input
     * @return Command object corresponding to the user input
     * @throws EmptyDescriptionException  If user input contains empty fields
     * @throws InvalidCommandException    If the command given is not recognised
     * @throws WrongUsageException        If a command has been used wrongly
     * @throws ReservedCharacterException If reserved characters such as | are used
     * @throws InvalidDateTimeException   If date given cannot be parsed
     */
    public static Command parseCommand(String input) throws EmptyDescriptionException, InvalidCommandException,
            WrongUsageException, ReservedCharacterException, InvalidDateTimeException {
        assert input != null : "Input cannot be null";

        String trimmedInput = input.trim();
        String[] splitInput = trimmedInput.split(" ", 2);
        if (trimmedInput.isEmpty()) {
            return Command.EMPTY_COMMAND;
        } else if (splitInput.length == 1) {
            String keyword = splitInput[0];
            switch (keyword) {
            case "l":
            case "list":
                return Command.LIST_COMMAND;
            case "b":
            case "bye":
                return Command.EXIT_COMMAND;
            case "m":
            case "mark":
                throw new WrongUsageException(CommandType.MARK);
            case "um":
            case "unmark":
                throw new WrongUsageException(CommandType.UNMARK);
            case "t":
            case "todo":
                throw new WrongUsageException(CommandType.TODO);
            case "d":
            case "deadline":
                throw new WrongUsageException(CommandType.DEADLINE);
            case "e":
            case "event":
                throw new WrongUsageException(CommandType.EVENT);
            case "del":
            case "delete":
                throw new WrongUsageException(CommandType.DELETE);
            case "f":
            case "find":
                throw new WrongUsageException(CommandType.FIND);
            default:
                throw new InvalidCommandException(keyword);
            }
        } else {
            String keyword = splitInput[0];
            String details = splitInput[1].trim();
            switch (keyword) {
            case "l":
            case "list":
                throw new WrongUsageException(CommandType.LIST);
            case "b":
            case "bye":
                throw new WrongUsageException(CommandType.EXIT);
            case "m":
            case "mark":
                return parseMarkCommand(details);
            case "um":
            case "unmark":
                return parseUnmarkCommand(details);
            case "t":
            case "todo":
                return parseToDoCommand(details);
            case "d":
            case "deadline":
                return parseDeadlineCommand(details);
            case "e":
            case "event":
                return parseEventCommand(details);
            case "del":
            case "delete":
                return parseDeleteCommand(details);
            case "f":
            case "find":
                return parseFindCommand(details);
            default:
                throw new InvalidCommandException(keyword);
            }
        }
    }

    private static MarkCommand parseMarkCommand(String details) throws WrongUsageException {
        assert details != null : "Arguments to a mark command cannot be null";

        try {
            return new MarkCommand(Integer.parseUnsignedInt(details));
        } catch (NumberFormatException e) {
            throw new WrongUsageException(CommandType.MARK);
        }
    }

    private static UnmarkCommand parseUnmarkCommand(String details) throws WrongUsageException {
        assert details != null : "Arguments to an unmark command cannot be null";

        try {
            return new UnmarkCommand(Integer.parseUnsignedInt(details));
        } catch (NumberFormatException e) {
            throw new WrongUsageException(CommandType.UNMARK);
        }
    }

    private static ToDoCommand parseToDoCommand(String details) throws ReservedCharacterException {
        assert details != null : "Arguments to a todo command cannot be null";

        checkReservedCharacters(details);
        return new ToDoCommand(details);
    }

    private static DeadlineCommand parseDeadlineCommand(String details) throws WrongUsageException,
            ReservedCharacterException, EmptyDescriptionException, InvalidDateTimeException {
        assert details != null : "Arguments to a deadline command cannot be null";

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

    private static EventCommand parseEventCommand(String details) throws WrongUsageException,
            ReservedCharacterException, EmptyDescriptionException, InvalidDateTimeException {
        assert details != null : "Arguments to an event command cannot be null";

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
        assert details != null : "Arguments to a delete command cannot be null";

        try {
            return new DeleteCommand(Integer.parseUnsignedInt(details));
        } catch (NumberFormatException e) {
            throw new WrongUsageException(CommandType.DELETE);
        }
    }

    private static FindCommand parseFindCommand(String details) throws ReservedCharacterException {
        assert details != null : "Arguments to a find command cannot be null";

        checkReservedCharacters(details);
        return new FindCommand(details);
    }

    private static void checkReservedCharacters(String details) throws ReservedCharacterException {
        assert details != null : "Attempting to check if null contains reserved characters";

        if (details.contains("|")) {
            throw new ReservedCharacterException();
        }
    }

    /**
     * Parses a string representation of a saved task and returns a corresponding Task object
     *
     * @param savedTask String representation of a saved task
     * @return Task object corresponding to savedTask
     * @throws CorruptedSaveException If savedTask is not of the correct format
     */
    public static Task parseSavedTask(String savedTask) throws CorruptedSaveException {
        assert savedTask != null : "String representation of saved task cannot be null";

        String[] splitSavedTask = savedTask.split(DELIMITER);
        try {
            switch (splitSavedTask[0]) {
            case TODO_TASK:
                return new ToDoTask(Boolean.parseBoolean(splitSavedTask[1]), splitSavedTask[2]);
            case DEADLINE_TASK:
                return new DeadlineTask(Boolean.parseBoolean(splitSavedTask[1]), splitSavedTask[2],
                        parseDateTime(splitSavedTask[3]));
            case EVENT_TASK:
                return new EventTask(Boolean.parseBoolean(splitSavedTask[1]), splitSavedTask[2],
                        parseDateTime(splitSavedTask[3]), parseDateTime(splitSavedTask[3]));
            default:
                throw new CorruptedSaveException();
            }
        } catch (BaronException | ArrayIndexOutOfBoundsException e) {
            throw new CorruptedSaveException();
        }
    }

    /**
     * Parses a string representation of a date and time and returns a corresponding LocalDateTime object
     *
     * @param dateTimeString String representation of a date and time
     * @return LocalDateTime object corresponding to dateTimeString
     * @throws InvalidDateTimeException If dateTimeString is not of the correct format
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws InvalidDateTimeException {
        assert dateTimeString != null : "Datetime string cannot be null";

        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofPattern(
                        "[yyyy-M-d]" + "[d-M-yyyy]" + "[d-M]" + "[yyyy/M/d]" + "[d/M/yyyy]" + "[d/M]"
                                + "[d MMM yyyy]" + "[d MMM]" + "[MMM d yyyy]" + "[MMM d]"
                ))
                .appendOptional(DateTimeFormatter.ofPattern(" "
                        + "[HHmm]" + "[HH:mm]"
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
