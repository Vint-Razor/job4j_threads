package ru.job4j.singleton;

public class Main {
    public static void main(String[] args) {
        TrackerSingle tracker = TrackerSingle.getInstance();
        TrackerSingle tracker2 = TrackerSingle.getInstance();
        System.out.println(tracker2 == tracker);
        tracker2.add(new Item(1, "first"));
        tracker.add(new Item(2, "second"));
        tracker.getList().forEach(System.out::println);
    }
}
