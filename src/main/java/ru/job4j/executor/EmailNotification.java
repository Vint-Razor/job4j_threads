package ru.job4j.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1. Реализовать сервис для рассылки почты. Создайте класс EmailNotification.
 * 2. В классе будет метод emailTo(User user) - он должен через ExecutorService отправлять почту.
 * Так же добавьте метод close() - он должен закрыть pool. То есть в классе EmailNotification должно быть поле pool,
 * которые используется в emailTo и close().
 * 3. Модель User описывают поля username, email.
 * 4. Метод emailTo должен брать данные пользователя и подставлять в шаблон
 * subject = Notification {username} to email {email}.
 * body = Add a new event to {username}
 * 5. Создайте метод public void send(String subject, String body, String email) - он должен быть пустой.
 * 6. Через ExecutorService создайте задачу, которая будет создавать данные
 *  для пользователя и передавать их в метод send.
 */
public class EmailNotification {
    private ExecutorService pool = Executors.newCachedThreadPool();
    private String subject = "";
    private String body = "";

    public void emailTo(User user) {
        String username = user.username();
        String email = user.email();
        subject = String.format("Notification %s to email %s", username, email);
        body = String.format("Add a new event to %s", username);
        pool.submit(() -> send(subject, body, email));
    }

    public void close() {
        pool.shutdown();
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
