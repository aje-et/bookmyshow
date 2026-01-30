package com.ajeet.bookmyshow.service;

import com.ajeet.bookmyshow.model.Seat;

import java.util.List;

public interface SeatService {
    Seat create(Seat seat);
    List<Seat> listByAuditorium(Long auditoriumId);
}
