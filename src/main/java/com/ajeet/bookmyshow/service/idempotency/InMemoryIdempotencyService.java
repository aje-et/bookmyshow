package com.ajeet.bookmyshow.service.idempotency;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

@Service
public class InMemoryIdempotencyService implements IdempotencyService {

    private static class Entry { Object value; long expiresAt; }

    private final Map<String, Entry> store = new ConcurrentHashMap<>();
    private final ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();

    public InMemoryIdempotencyService() {
        cleaner.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            store.entrySet().removeIf(e -> e.getValue().expiresAt < now);
        }, 1, 1, TimeUnit.MINUTES);
    }

    @Override
    public Optional<Object> get(String key) {
        Entry e = store.get(key);
        if (e == null) return Optional.empty();
        if (e.expiresAt < System.currentTimeMillis()) { store.remove(key); return Optional.empty(); }
        return Optional.ofNullable(e.value);
    }

    @Override
    public void put(String key, Object value, long ttlMs) {
        Entry e = new Entry(); e.value = value; e.expiresAt = System.currentTimeMillis() + ttlMs;
        store.put(key, e);
    }
}
