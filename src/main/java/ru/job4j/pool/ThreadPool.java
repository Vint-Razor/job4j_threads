package ru.job4j.pool;

import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * это хранилище для ресурсов, которые можно переиспользовать.
 * Клиент берет ресурс из пула, выполняет свою работу и возвращает обратно в пул.
 */
public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

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
                                tasks.poll().run();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            ));
        }
        threads.forEach(Thread::start);
    }

    public void threadsStatus() {
        threads.forEach((thread) -> System.out.println(thread.getState()));
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable first = () -> System.out.println("first");
        Runnable second = () -> System.out.println("second");
        Runnable third = () -> System.out.println("third");
        Runnable four = () -> System.out.println("four");
        Runnable five = () -> System.out.println("five");
        ThreadPool pool = new ThreadPool();
        pool.work(first);
        pool.work(second);
        pool.work(third);
        pool.work(four);
        pool.work(five);
        pool.threadsStatus();
        Thread.sleep(10);
        pool.shutdown();
        Thread.sleep(100);
        pool.threadsStatus();
    }
}
