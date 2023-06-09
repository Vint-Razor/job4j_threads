/**
 * Программа должна скачивать файл из сети с ограничением по скорости скачки.
 * Чтобы ограничить скорость скачивания, нужно засечь время скачивания 1024 байт.
 * Если время меньше указанного, то нужно выставить паузу за счет Thread.sleep.
 * Пауза должна вычисляться, а не быть константой.
 *
 * Алгоритм:
 * 1. замерить скорость System.nanoTime();
 * 1.1 скачиваем указанное количество байт (байт/сек)
 * 1.2 смотрим, осталось ли ещё время до секунды
 * 2. если осталось то интегрируем sleep
 * 3. sleep выставляем равным оставшемуся времени
 */

package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Wget implements Runnable {
    private static final String FILE = "file2.xml";
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
            int byteRead = 0;
            long start = 0;
            long end = 0;
            while (byteRead != -1) {
                start = System.nanoTime();
                byteRead = in.read(dataBuffer, 0, speed);
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
            throw new IllegalArgumentException(String.format("неправильно указан url-адрес %s", url));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(String.format("файл не найден %s", FILE));
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
