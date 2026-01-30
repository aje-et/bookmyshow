package com.ajeet.bookmyshow.service.lock;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Primary
@Service
public class InMemoryLockService implements LockService {

    private static class Lock { String owner; long expiresAt; }

    private final Map<String, Lock> locks = new ConcurrentHashMap<>();

    @Override
    public boolean tryLock(String key, String owner, Duration ttl) {
        long now = System.currentTimeMillis();
        long expire = now + ttl.toMillis();
        return locks.compute(key, (k, existing) -> {
            if (existing == null || existing.expiresAt < now) {
                Lock l = new Lock(); l.owner = owner; l.expiresAt = expire; return l;
            }
            return existing;
        }).owner.equals(owner);
    }

    @Override
    public boolean unlock(String key, String owner) {
        return locks.computeIfPresent(key, (k, existing) -> {
            if (existing.owner.equals(owner)) return null; else return existing;
        }) == null;
    }
}
