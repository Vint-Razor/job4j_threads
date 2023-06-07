package ru.job4j.concurrent;

public class ThreadState {

    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println(Thread.currentThread().getName());
        Thread first = new Thread(runnable);
        Thread second = new Thread(runnable);
        first.start();
        second.start();
        boolean threadsNotTerminated = true;
        while (threadsNotTerminated) {
            threadsNotTerminated = first.getState() != Thread.State.TERMINATED
                    && second.getState() != Thread.State.TERMINATED;
        }
        System.out.println(first.getName() + " " + first.getState());
        System.out.println(second.getName() + " " + second.getState());
        System.out.printf("поток %s завершает работу\n", Thread.currentThread().getName());
    }
}
