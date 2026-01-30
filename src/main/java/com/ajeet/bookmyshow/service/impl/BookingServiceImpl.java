package com.ajeet.bookmyshow.service.impl;

import com.ajeet.bookmyshow.dto.BookingRequest;
import com.ajeet.bookmyshow.exception.SeatUnavailableException;
import com.ajeet.bookmyshow.model.Booking;
import com.ajeet.bookmyshow.model.Show;
import com.ajeet.bookmyshow.model.User;
import com.ajeet.bookmyshow.repository.BookingRepository;
import com.ajeet.bookmyshow.repository.ShowRepository;
import com.ajeet.bookmyshow.repository.UserRepository;
import com.ajeet.bookmyshow.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;
    private final com.ajeet.bookmyshow.service.lock.LockService lockService;

    public BookingServiceImpl(BookingRepository bookingRepository, ShowRepository showRepository, UserRepository userRepository, com.ajeet.bookmyshow.service.lock.LockService lockService) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.lockService = lockService;
    }

    @Override
    @Transactional
    public Booking reserve(BookingRequest request) {
        Booking booking = new Booking();

        if (request.getUserId() != null) {
            Optional<User> u = userRepository.findById(request.getUserId());
            u.ifPresent(booking::setUser);
        }

        if (request.getShowId() != null) {
            Optional<Show> s = showRepository.findById(request.getShowId());
            if (s.isPresent()) booking.setShow(s.get());
            else throw new IllegalArgumentException("Show not found: " + request.getShowId());
        }

        List<String> requested = Optional.ofNullable(request.getSeats()).orElse(Collections.emptyList());

        // validate seats exist in auditorium
        List<String> existingSeats = booking.getShow().getAuditorium().getSeats().stream().map(seat -> seat.getLabel()).collect(Collectors.toList());
        for (String r : requested) {
            if (!existingSeats.contains(r)) {
                throw new IllegalArgumentException("Seat does not exist: " + r);
            }
        }

        // Seat locking
        String lockOwner = java.util.UUID.randomUUID().toString();
        java.time.Duration lockTtl = java.time.Duration.ofMinutes(5);
        java.util.List<String> lockedKeys = new java.util.ArrayList<>();
        try {
            for (String seatLabel : requested) {
                String key = "show:" + booking.getShow().getId() + ":seat:" + seatLabel;
                boolean ok = lockService.tryLock(key, lockOwner, lockTtl);
                if (!ok) {
                    throw new SeatUnavailableException("Seat locked/unavailable: " + seatLabel);
                }
                lockedKeys.add(key);
            }

            // check already booked
            List<String> statuses = Arrays.asList("RESERVED", "CONFIRMED");
            List<Booking> bookings = bookingRepository.findByShowIdAndStatusIn(booking.getShow().getId(), statuses);
            Set<String> already = bookings.stream().flatMap(b -> b.getSeats().stream()).collect(Collectors.toSet());
            for (String r : requested) {
                if (already.contains(r)) {
                    throw new SeatUnavailableException("Seat already booked: " + r);
                }
            }

            booking.setSeats(requested);
            booking.setStatus("RESERVED");
            Booking saved = bookingRepository.save(booking);
            return saved;
        } finally {
            // release locks
            for (String k : lockedKeys) {
                lockService.unlock(k, lockOwner);
            }
        }
    }

    @Override
    public List<Booking> listAll() {
        return bookingRepository.findAll();
    }
}
