package ketchup.storage;

import ketchup.parser.TaskParser;
import ketchup.tasks.Task;
import ketchup.tasks.TaskList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private static final String FILE_PATH = "data/ketchup.txt";

    public static void clear() {
        try {
            FileWriter fw = new FileWriter("data/ketchup.txt");
            fw.write("");
            fw.close();
        } catch (IOException e) {
            System.out.println("Could not clear data file.");
        }
    }

    public static void save(TaskList tasks) {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdir();
            }

            FileWriter fw = new FileWriter(FILE_PATH);
            for (int i = 0; i < tasks.getSize(); i ++) {
                Task t = tasks.getTask(i);
                fw.write(t.toFileString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Could not save tasks.");
        }
    }

    public static TaskList load() {
        TaskList list = new TaskList();
        File file = new File(FILE_PATH);

        if (!file.exists()) return list;

        try {
            Scanner sc = new Scanner(file);
            TaskParser parser = new TaskParser();
            while (sc.hasNextLine()) {
                Task t = parser.parse(sc.nextLine());
                if (t != null) list.addTask(t); // corrupted lines skipped
            }
            sc.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Could not load tasks.");
        }
        return list;
    }
}
