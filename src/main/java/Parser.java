public class Parser {
    private static final String DELIMITER = " | ";

    public static Task parseSavedTask(String savedTask) throws EmptyDescriptionException {
        return Task.createTask(savedTask.split(DELIMITER));
    }
}
