package ru.job4j.threads.threadlocal;

public class SecondThread extends Thread {
    @Override
    public void run() {
        ThreadLocalDemo.tl.set("Это покок 2");
        System.out.println(ThreadLocalDemo.tl.get());
    }
}
