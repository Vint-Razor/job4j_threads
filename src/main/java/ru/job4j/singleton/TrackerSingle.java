package ru.job4j.singleton;

import java.util.ArrayList;
import java.util.List;

public final class TrackerSingle {

    private ArrayList<Item> list = new ArrayList<>();

    private TrackerSingle() {
    }

    public static TrackerSingle getInstance() {
        return Holder.INSTANCE;
    }

    public boolean add(Item model) {
        return list.add(model);
    }

    public List<Item> getList() {
        return list;
    }

    private static final class Holder {
        private static final TrackerSingle INSTANCE = new TrackerSingle();
    }
}
