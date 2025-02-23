package baron;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import baron.exception.BaronException;
import baron.task.Task;

/**
 * Storage saves the tasks to a save file, as well as reads the tasks from a save file
 */
public class Storage {
    private final Path filePath;

    /**
     * Instantiates a Storage object
     *
     * @param filePath File path where tasks are saved to and loaded from
     * @throws InvalidPathException If file path is invalid
     */
    public Storage(String filePath) throws InvalidPathException {
        assert filePath != null : "File path string cannot be null";

        this.filePath = Paths.get(filePath);
    }

    /**
     * Creates a save file if it does not already exist
     */
    private void createFile() {
        try {
            Files.createDirectories(this.filePath.getParent());
            Files.createFile(this.filePath);
        } catch (IOException e) {
            System.out.println("Error occurred when creating a save file: " + e.getMessage());
        }
    }

    /**
     * Reads a file containing saved tasks and returns a list of tasks
     *
     * @return A list of tasks corresponding to the save file
     */
    public ArrayList<Task> loadSavedTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        try (Scanner s = new Scanner(filePath)) {
            while (s.hasNextLine()) {
                String task = s.nextLine();
                assert !task.trim().isEmpty() : "Read a blank line from save file";

                taskList.add(Parser.parseSavedTask(task));
            }
        } catch (IOException e) {
            this.createFile();
            return new ArrayList<>();
        } catch (BaronException e) {
            System.out.println("The saved tasks has been corrupted!");
            return new ArrayList<>();
        }
        return taskList;
    }

    /**
     * Saves a list of tasks to the save file
     *
     * @param taskList A list of tasks to be saved to a save file
     */
    public void saveTasks(ArrayList<Task> taskList) {
        assert taskList != null : "Task list cannot be null";

        try (FileWriter fw = new FileWriter(filePath.toFile())) {
            for (Task task : taskList) {
                fw.write(task.toSaveFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error occurred when saving tasks: " + e.getMessage());
        }
    }
}
