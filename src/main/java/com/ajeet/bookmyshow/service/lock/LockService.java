package com.ajeet.bookmyshow.service.lock;

import java.time.Duration;

public interface LockService {
    boolean tryLock(String key, String owner, Duration ttl);
    boolean unlock(String key, String owner);
}
