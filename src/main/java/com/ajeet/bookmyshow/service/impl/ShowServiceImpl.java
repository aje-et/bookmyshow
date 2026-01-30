package com.ajeet.bookmyshow.service.impl;

import com.ajeet.bookmyshow.model.Booking;
import com.ajeet.bookmyshow.model.Seat;
import com.ajeet.bookmyshow.model.Show;
import com.ajeet.bookmyshow.repository.BookingRepository;
import com.ajeet.bookmyshow.repository.SeatRepository;
import com.ajeet.bookmyshow.repository.ShowRepository;
import com.ajeet.bookmyshow.service.ShowService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;

    public ShowServiceImpl(ShowRepository showRepository, SeatRepository seatRepository, BookingRepository bookingRepository) {
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Show create(Show show) {
        // allow creating shows (no extra checks here). If you want to restrict to today's shows, add checks here.
        return showRepository.save(show);
    }

    @Override
    public Show update(Long id, Show show) {
        Show existing = showRepository.findById(id).orElseThrow(() -> new com.ajeet.bookmyshow.exception.ShowNotFoundException("Show not found: " + id));
        java.time.LocalDate today = java.time.LocalDate.now();
        if (!existing.getStartTime().toLocalDate().isEqual(today)) {
            throw new com.ajeet.bookmyshow.exception.ShowOperationException("Can only update shows scheduled for today");
        }

        // apply updates (allow updating time, end time, price, auditorium)
        if (show.getStartTime() != null) existing.setStartTime(show.getStartTime());
        if (show.getEndTime() != null) existing.setEndTime(show.getEndTime());
        if (show.getPrice() != null) existing.setPrice(show.getPrice());
        if (show.getAuditorium() != null) existing.setAuditorium(show.getAuditorium());

        return showRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Show existing = showRepository.findById(id).orElseThrow(() -> new com.ajeet.bookmyshow.exception.ShowNotFoundException("Show not found: " + id));
        java.time.LocalDate today = java.time.LocalDate.now();
        if (!existing.getStartTime().toLocalDate().isEqual(today)) {
            throw new com.ajeet.bookmyshow.exception.ShowOperationException("Can only delete shows scheduled for today");
        }

        List<Booking> bookings = bookingRepository.findByShowIdAndStatusIn(id, Arrays.asList("RESERVED", "CONFIRMED"));
        if (!bookings.isEmpty()) {
            throw new com.ajeet.bookmyshow.exception.ShowOperationException("Cannot delete show with existing bookings");
        }

        showRepository.deleteById(id);
    }
    @Override
    public List<Show> listAll() {
        return showRepository.findAll();
    }

    @Override
    public List<String> getAvailableSeats(Long showId) {
        Optional<Show> s = showRepository.findById(showId);
        if (s.isEmpty()) return Collections.emptyList();
        Show show = s.get();
        Long auditoriumId = show.getAuditorium().getId();
        List<Seat> seats = seatRepository.findByAuditoriumId(auditoriumId);
        List<String> allSeatLabels = seats.stream().map(Seat::getLabel).collect(Collectors.toList());

        List<Booking> bookings = bookingRepository.findByShowIdAndStatusIn(showId, Arrays.asList("RESERVED", "CONFIRMED"));
        Set<String> booked = bookings.stream().flatMap(b -> b.getSeats().stream()).collect(Collectors.toSet());

        return allSeatLabels.stream().filter(label -> !booked.contains(label)).collect(Collectors.toList());
    }
}
