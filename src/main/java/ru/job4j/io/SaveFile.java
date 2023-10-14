package ru.job4j.io;

import java.io.*;

public class SaveFile implements Saver {

    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    @Override
    public synchronized void saveContent(String content) {
        try (Writer buffer = new BufferedWriter(new FileWriter(file))) {
            buffer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
