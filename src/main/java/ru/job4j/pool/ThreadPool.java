package ru.job4j.pool;

import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * это хранилище для ресурсов, которые можно переиспользовать.
 * Клиент берет ресурс из пула, выполняет свою работу и возвращает обратно в пул.
 */
public class ThreadPool {
    private static final int QUEUE_LIMIT = 3;
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(QUEUE_LIMIT);

    public ThreadPool() {
        fillListThreads(size);
    }

    /**
     * добавляет задачи в блокирующую очередь
     * @param job объект Runnable
     */
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /**
     * метод завершает все запущенные задачи.
     */
    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    private void fillListThreads(int size) {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.poll();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            ));
        }
        threads.forEach(Thread::start);
    }
}
