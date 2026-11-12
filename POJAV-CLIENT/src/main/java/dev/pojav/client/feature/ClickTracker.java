package dev.pojav.client.feature;

import java.util.ArrayDeque;
import java.util.Deque;

public final class ClickTracker {
    private static final Deque<Long> LEFT = new ArrayDeque<>();
    private static final Deque<Long> RIGHT = new ArrayDeque<>();
    private ClickTracker() {}

    public static synchronized void register(int button) {
        long now = System.currentTimeMillis();
        Deque<Long> target = button == 0 ? LEFT : button == 1 ? RIGHT : null;
        if (target == null) return;
        target.addLast(now);
        prune(target, now);
    }

    public static synchronized int cps(int button) {
        long now = System.currentTimeMillis();
        Deque<Long> target = button == 0 ? LEFT : RIGHT;
        prune(target, now);
        return target.size();
    }

    private static void prune(Deque<Long> queue, long now) {
        while (!queue.isEmpty() && now - queue.peekFirst() > 1000L) queue.removeFirst();
    }
}
