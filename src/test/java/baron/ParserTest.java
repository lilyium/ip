package baron;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import baron.command.Command;
import baron.command.MarkCommand;
import baron.command.ToDoCommand;
import baron.command.UnmarkCommand;
import baron.exception.BaronException;
import baron.exception.InvalidDateTimeException;
import baron.exception.WrongUsageException;

import org.junit.jupiter.api.Test;


public class ParserTest {
    @Test
    void parseCommand_emptyInput_returnEmptyCommand() {
        try {
            assertEquals(Command.EMPTY_COMMAND, Parser.parseCommand(""));
        } catch (BaronException e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    void parseCommand_normalListInput_returnListCommand() {
        try {
            assertEquals(Command.LIST_COMMAND, Parser.parseCommand("list"), "\"list\" does not return a ListCommand");
        } catch (BaronException e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    void parseCommand_incorrectListInput_exceptionThrown() {
        WrongUsageException e = assertThrows(WrongUsageException.class,
                () -> Parser.parseCommand("list 2"), "\"list 2\" does not return a WrongUsageException");
        assertEquals(new WrongUsageException(Command.CommandType.LIST), e, "\"list 2\" does not return the corresponding WrongUsageException");
    }

    @Test
    void parseCommand_normalExitInput_returnExitCommand() {
        try {
            assertEquals(Command.EXIT_COMMAND, Parser.parseCommand("bye"), "\"bye\" does not return a ExitCommand");
        } catch (BaronException e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    void parseCommand_incorrectExitInput_exceptionThrown() {
        WrongUsageException e = assertThrows(WrongUsageException.class,
                () -> Parser.parseCommand("bye now"), "\"bye now\" does not return a WrongUsageException");
        assertEquals(new WrongUsageException(Command.CommandType.EXIT), e, "\"bye now\" does not return the corresponding WrongUsageException");
    }

    @Test
    void parseCommand_normalMarkInput_returnMarkCommand() {
        try {
            assertEquals(new MarkCommand(2), Parser.parseCommand("mark 2"), "\"mark 2\" does not return a correct MarkCommand");
        } catch (BaronException e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    void parseCommand_incorrectMarkInput_exceptionThrown() {
        WrongUsageException e = assertThrows(WrongUsageException.class,
                () -> Parser.parseCommand("mark"), "\"mark\" does not return a WrongUsageException");
        assertEquals(new WrongUsageException(Command.CommandType.MARK), e, "\"mark\" does not return the corresponding WrongUsageException");
    }

    @Test
    void parseCommand_normalUnmarkInput_returnUnmarkCommand() {
        try {
            assertEquals(new UnmarkCommand(2), Parser.parseCommand("unmark 2"), "\"unmark 2\" does not return a correct UnmarkCommand");
        } catch (BaronException e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    void parseCommand_incorrectUnmarkInput_exceptionThrown() {
        WrongUsageException e = assertThrows(WrongUsageException.class,
                () -> Parser.parseCommand("unmark"), "\"unmark\" does not return a WrongUsageException");
        assertEquals(new WrongUsageException(Command.CommandType.UNMARK), e, "\"unmark\" does not return the corresponding WrongUsageException");
    }

    @Test
    void parseCommand_normalTodoInput_returnTodoCommand() {
        try {
            assertEquals(new ToDoCommand("test"), Parser.parseCommand("todo test"), "\"todo test\" does not return a correct TodoCommand");
        } catch (BaronException e) {
            fail("Unexpected exception thrown: " + e.getMessage());
        }
    }

    @Test
    void parseCommand_incorrectTodoInput_exceptionThrown() {
        WrongUsageException e = assertThrows(WrongUsageException.class,
                () -> Parser.parseCommand("todo"), "\"todo\" does not return a WrongUsageException");
        assertEquals(new WrongUsageException(Command.CommandType.TODO), e, "\"todo\" does not return the corresponding WrongUsageException");
    }

    @Test
    void parseDateTime_validInputDdMYyyy_returnLocalDateTime() {
        String testInput = "31/01/2025";
        try {
            LocalDateTime localDateTime = Parser.parseDateTime(testInput);
            assertEquals(2025, localDateTime.getYear(), "Parser parses the input " + testInput + " wrongly");
            assertEquals(1, localDateTime.getMonthValue(), "Parser parses the input " + testInput + " wrongly");
            assertEquals(31, localDateTime.getDayOfMonth(), "Parser parses the input " + testInput + " wrongly");
            assertEquals(23, localDateTime.getHour(), "Parser parses the input " + testInput + " wrongly");
            assertEquals(59, localDateTime.getMinute(), "Parser parses the input " + testInput + " wrongly");
        } catch (InvalidDateTimeException e) {
            fail("Parser was unable to parse " + testInput);
        }
    }

    @Test
    void parseDateTime_validInputMmmDd_returnLocalDateTime() {
        String testInput = "Jan 31";
        try {
            LocalDateTime localDateTime = Parser.parseDateTime(testInput);
            assertEquals(2025, localDateTime.getYear(), "Parser parses the input " + testInput + " wrongly");
            assertEquals(1, localDateTime.getMonthValue(), "Parser parses the input " + testInput + " wrongly");
            assertEquals(31, localDateTime.getDayOfMonth(), "Parser parses the input " + testInput + " wrongly");
            assertEquals(23, localDateTime.getHour(), "Parser parses the input " + testInput + " wrongly");
            assertEquals(59, localDateTime.getMinute(), "Parser parses the input " + testInput + " wrongly");
        } catch (InvalidDateTimeException e) {
            fail("Parser was unable to parse " + testInput);
        }
    }

    @Test
    void parseDateTime_invalidInput_exceptionThrown() {
        String testInput = "Later";
        InvalidDateTimeException e = assertThrows(InvalidDateTimeException.class,
                () -> Parser.parseDateTime(testInput), "\"" + testInput + "\" does not return a WrongUsageException");
    }
}
