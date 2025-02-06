package baron.command;

import java.util.ArrayList;

import baron.Storage;
import baron.Ui;
import baron.task.Task;

public class FindCommand extends Command {
    private final String searchTerm;

    public FindCommand(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public void execute(ArrayList<Task> taskList, Storage storage) {
        ArrayList<Task> matchList = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getTaskName().contains(searchTerm)) {
                matchList.add(task);
            }
        }
        Ui.showMatchingTasks(matchList);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof FindCommand other) {
            return this.searchTerm.equals(other.searchTerm);
        } else {
            return false;
        }
    }

}