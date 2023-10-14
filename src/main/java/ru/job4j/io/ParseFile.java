package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile implements Parser {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private synchronized String content(Predicate<Character> filter) {
        StringBuilder sb = new StringBuilder();
        int data;
        try (InputStream in = new FileInputStream(file);
             BufferedInputStream buffer = new BufferedInputStream(in)) {
            while ((data = buffer.read()) > 0) {
                if (filter.test((char) data)) {
                    sb.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public synchronized String getContent() {
        return content(data -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return content(data -> data < 0x80);
    }
}
