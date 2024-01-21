package ru.job4j.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public void emailTo(User user) {
        String username = user.username();
        String email = user.email();
        String subject = String.format("Notification %s to email %s", username, email);
        String body = String.format("Add a new event to %s", username);
        pool.submit(() -> send(subject, body, email));
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }

    public static void main(String[] args) {
        User john = new User("John", "www.john@mail.com");
        EmailNotification notification = new EmailNotification();
        notification.emailTo(john);
        notification.close();
    }
}
