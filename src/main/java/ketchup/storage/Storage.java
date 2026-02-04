package ketchup.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import ketchup.parser.TaskParser;
import ketchup.tasks.Task;
import ketchup.tasks.TaskList;

/**
 * Handles the storage of tasks by saving to and loading from a local data file.
 */
public class Storage {

    /**
     * Relative file path used to store task data.
     */
    private static final String FILE_PATH = "data/ketchup.txt";

    /**
     * Clears all stored task data by overwriting the data file with an empty file.
     * <p>
     * If the file does not exist, it will be created.
     */
    public static void clear() {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);
            fw.write("");
            fw.close();
        } catch (IOException e) {
            System.out.println("Could not clear data file.");
        }
    }

    /**
     * Saves the current task list to the data file.
     * <p>
     * The data directory is created if it does not already exist.
     * Each task is written on a new line using its
     * {@link Task#toFileString()} representation.
     *
     * @param tasks the task list to be saved
     */
    public static void save(TaskList tasks) {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdir();
            }

            FileWriter fw = new FileWriter(FILE_PATH);
            for (int i = 0; i < tasks.getSize(); i++) {
                Task t = tasks.getTask(i);
                fw.write(t.toFileString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Could not save tasks.");
        }
    }

    /**
     * Loads tasks from the data file into a TaskList.
     * <p>
     * If the data file does not exist, an empty TaskList is returned.
     * <p>
     * If a line in the file is corrupted or cannot be parsed,
     * it is skipped and loading continues for remaining lines.
     *
     * @return a TaskList containing all successfully loaded tasks
     */
    public static TaskList load() {
        TaskList list = new TaskList();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return list;
        }

        try {
            Scanner sc = new Scanner(file);
            TaskParser parser = new TaskParser();
            while (sc.hasNextLine()) {
                Task t = parser.parse(sc.nextLine());
                if (t != null) {
                    list.addTask(t);
                }
            }
            sc.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Could not load tasks.");
        }
        return list;
    }
}
