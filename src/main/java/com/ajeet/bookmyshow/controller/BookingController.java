package com.ajeet.bookmyshow.controller;

import com.ajeet.bookmyshow.dto.BookingRequest;
import com.ajeet.bookmyshow.dto.BookingResponse;
import com.ajeet.bookmyshow.model.Booking;
import com.ajeet.bookmyshow.service.BookingService;
import com.ajeet.bookmyshow.service.idempotency.IdempotencyService;
import com.ajeet.bookmyshow.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final IdempotencyService idempotencyService;
    private final UserRepository userRepository;

    public BookingController(BookingService bookingService, IdempotencyService idempotencyService, UserRepository userRepository) {
        this.bookingService = bookingService;
        this.idempotencyService = idempotencyService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> reserve(@RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey,
                                     @Valid @RequestBody BookingRequest request,
                                     java.security.Principal principal) {
        // Idempotency check
        if (idempotencyKey != null) {
            java.util.Optional<Object> prev = idempotencyService.get(idempotencyKey);
            if (prev.isPresent()) {
                return ResponseEntity.ok(prev.get());
            }
        }

        // set authenticated user if available
        if (principal != null) {
            String email = principal.getName();
            userRepository.findByEmail(email).ifPresent(u -> request.setUserId(u.getId()));
        }

        Booking saved = bookingService.reserve(request);
        BookingResponse resp = new BookingResponse();
        resp.setBookingId(saved.getId());
        resp.setStatus(saved.getStatus());
        resp.setSeats(saved.getSeats());
        resp.setCreatedAt(saved.getCreatedAt());

        if (idempotencyKey != null) {
            idempotencyService.put(idempotencyKey, resp, 1000L * 60 * 10); // 10 minutes
        }

        return ResponseEntity.ok(resp);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> list() {
        List<BookingResponse> list = bookingService.listAll().stream().map(b -> {
            BookingResponse r = new BookingResponse();
            r.setBookingId(b.getId());
            r.setStatus(b.getStatus());
            r.setSeats(b.getSeats());
            r.setCreatedAt(b.getCreatedAt());
            return r;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}
