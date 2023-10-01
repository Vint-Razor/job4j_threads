package ru.job4j.concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class FileDownload {
private static final int BUFFER_SIZE = 1024;
    public static void main(String[] args) throws IOException {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp.xml");
        String url = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            byte[] dateBuffer = new byte[BUFFER_SIZE];
            int byteRead;
            while ((byteRead = in.read(dateBuffer, 0, BUFFER_SIZE)) != -1) {
                var downloadAt = System.nanoTime();
                out.write(dateBuffer, 0, byteRead);
                var sysNanoTime = System.nanoTime() - downloadAt;
                double bytePerMilSec = BUFFER_SIZE / (double) sysNanoTime * 1_000_000;
                System.out.printf("Read %d bytes : %d nano %d b/ms\n", BUFFER_SIZE, sysNanoTime,
                        (int) bytePerMilSec);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Files.size(file.toPath()) + " bytes");
    }
}
