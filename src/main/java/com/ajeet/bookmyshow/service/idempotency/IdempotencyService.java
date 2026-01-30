package com.ajeet.bookmyshow.service.idempotency;

import java.util.Optional;

public interface IdempotencyService {
    Optional<Object> get(String key);
    void put(String key, Object value, long ttlMs);
}
