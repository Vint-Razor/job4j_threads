package ru.job4j.threads.threadlocal;

public class FirstThread extends Thread {
    @Override
    public void run() {
        ThreadLocalDemo.tl.set("Это поток 1");
        System.out.println(ThreadLocalDemo.tl.get());
    }
}
