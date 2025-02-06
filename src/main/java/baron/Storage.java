package baron;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import baron.task.Task;
import baron.exception.BaronException;

public class Storage {
    private final Path filePath;

    public Storage(String filePath) throws InvalidPathException {
        this.filePath = Paths.get(filePath);
    }

    private void createFile() {
        try {
            Files.createDirectories(this.filePath.getParent());
            Files.createFile(this.filePath);
        } catch (IOException e) {
            System.out.println("Error occurred when creating a save file: " + e.getMessage());
        }
    }

    public ArrayList<Task> loadSavedTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        try (Scanner s = new Scanner(filePath)) {
            while (s.hasNextLine()) {
                String task = s.nextLine();
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

    public void saveTasks(ArrayList<Task> taskList) {
        try (FileWriter fw = new FileWriter(filePath.toFile())) {
            for (Task task : taskList) {
                fw.write(task.toSaveFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error occurred when saving tasks: " + e.getMessage());
        }
    }
}
