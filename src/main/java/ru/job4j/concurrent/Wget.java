/**
 * Программа должна скачивать файл из сети с ограничением по скорости скачки.
 * Чтобы ограничить скорость скачивания, нужно засечь время скачивания 1024 байт.
 * Если время меньше указанного, то нужно выставить паузу за счет Thread.sleep.
 * Пауза должна вычисляться, а не быть константой.
 */

package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Wget implements Runnable {
    private static final String FILE = "file.xml";
    private static final long SEC = 1000L;
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(FILE)) {
            byte[] dataBuffer = new byte[speed];
            int byteRead;
            long start;
            long end;
            while ((byteRead = in.read(dataBuffer, 0, speed)) != -1) {
                start = System.nanoTime();
                end = System.nanoTime();
                long difference = end - start;
                if (difference < SEC) {
                    try {
                        Thread.sleep(difference);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                fileOutputStream.write(dataBuffer, 0, byteRead);
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("не правильный формат url-адреса %s", url));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(String.format("url не найден, либо отсутствует соединение %s", FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
