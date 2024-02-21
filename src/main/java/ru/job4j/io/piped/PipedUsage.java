package ru.job4j.io.piped;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedUsage {

    public static void main(String[] args) throws IOException {

        final PipedInputStream input = new PipedInputStream();
        final PipedOutputStream output = new PipedOutputStream();

        Thread firsThread = new Thread(() -> {
            try {
                output.write("Job4j".getBytes());
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread secondThread = new Thread(() -> {
            try {
                int character;
                while ((character = input.read()) != -1) {
                    System.out.print((char) character);
                }
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        input.connect(output);
        firsThread.start();
        secondThread.start();
    }
}
