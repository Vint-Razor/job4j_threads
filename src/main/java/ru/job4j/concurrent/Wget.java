/**
 * Программа должна скачивать файл из сети с ограничением по скорости скачки.
 * Чтобы ограничить скорость скачивания, нужно засечь время скачивания 1024 байт.
 * Если время меньше указанного, то нужно выставить паузу за счет Thread.sleep.
 * Пауза должна вычисляться, а не быть константой.
 */

package ru.job4j.concurrent;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Wget implements Runnable {
    private static final long SEC = 1000L;
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    private static String getFileName(String url) {
        String file = "";
        try {
            file = Paths.get(new URI(url).getPath()).getFileName().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public void run() {
        String file = String.format("tmp_%s", getFileName(url));
        try (var in = new URL(url).openStream();
             var fileOutputStream = new FileOutputStream(file)) {
            byte[] dateBuffer = new byte[speed];
            int byteRead;
            int byteCount = 0;
            var startAt = System.currentTimeMillis();
            while ((byteRead = in.read(dateBuffer, 0, speed)) != -1) {
                fileOutputStream.write(dateBuffer, 0, byteRead);
                var downloadTime = System.currentTimeMillis() - startAt;
                byteCount += byteRead;
                if (byteCount >= speed) {
                    var sleepTime = downloadTime < SEC ? SEC - downloadTime : 0;
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    startAt = System.currentTimeMillis();
                    byteCount = 0;
                }

            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("не правильный формат url-адреса %s", url));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(String.format("url не найден, либо отсутствует соединение %s", file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void validator(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("аргументы программы не найденны."
                    + " Задайте два аргумента, адрес файла в сети и скорость скачивания."
                    + " Например: www.web.com/file.txt 1000");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validator(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
