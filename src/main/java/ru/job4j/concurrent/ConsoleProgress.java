package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

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
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\r load: " + progress[index]);
                Thread.sleep(500);
                index = index == progress.length - 1 ? 0 : index + 1;
            }
        } catch (InterruptedException e) {
            System.err.println("\nДoчepний поток прерван.");
        }
    }
}
