package com.ajeet.bookmyshow.service.impl;

import com.ajeet.bookmyshow.model.Auditorium;
import com.ajeet.bookmyshow.model.Seat;
import com.ajeet.bookmyshow.repository.AuditoriumRepository;
import com.ajeet.bookmyshow.repository.SeatRepository;
import com.ajeet.bookmyshow.service.SeatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final AuditoriumRepository auditoriumRepository;

    public SeatServiceImpl(SeatRepository seatRepository, AuditoriumRepository auditoriumRepository) {
        this.seatRepository = seatRepository;
        this.auditoriumRepository = auditoriumRepository;
    }

    @Override
    public Seat create(Seat seat) {
        if (seat.getAuditorium() != null && seat.getAuditorium().getId() != null) {
            Optional<Auditorium> a = auditoriumRepository.findById(seat.getAuditorium().getId());
            a.ifPresent(seat::setAuditorium);
        }
        return seatRepository.save(seat);
    }

    @Override
    public List<Seat> listByAuditorium(Long auditoriumId) {
        return seatRepository.findByAuditoriumId(auditoriumId);
    }
}
