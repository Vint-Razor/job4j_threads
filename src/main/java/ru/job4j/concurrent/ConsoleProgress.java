package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    private static final String SEPARATOR = System.lineSeparator();

    public static void main(String[] args) throws InterruptedException {

        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }

    @Override
    public void run() {
        var progress = new char[]{'-', '\\', '|', '/'};
        int index = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + progress[index]);
            index = index == progress.length - 1 ? 0 : index + 1;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
