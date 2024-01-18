package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Это блокирующая очередь, ограниченная по размеру.
 * В данном шаблоне Producer помещает данные в очередь, а Consumer извлекает данные из очереди.
 * Если очередь заполнена полностью, то при попытке добавления поток Producer блокируется,
 * до тех пор пока Consumer не извлечет очередные данные, т.е. в очереди появится свободное место.
 * И наоборот если очередь пуста поток Consumer блокируется, до тех пор пока Producer не поместит в очередь данные.
 * @param <T>
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    /**
     * создание новой очереди с ограничениями по размеру
     * @param limit - ограничение по размеру.
     */
    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    /**
     * добавляет обект типа Т в очередь
     * @param value обект типа Т
     */
    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= limit) {
            wait();
        }
        queue.offer(value);
        notifyAll();
    }

    /**
     * Метод poll() возвращает объект из внутренней коллекции. Если в коллекции объектов нет,
     * то нужно перевести текущую нить в состояние ожидания.
     * @return объект типа T
     */
    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T result = queue.poll();
        notifyAll();
        return result;
    }

    /**
     * проверяет пустая очередь или нет
     * @return true - если пусто, false - если есть обекты.
     */
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
