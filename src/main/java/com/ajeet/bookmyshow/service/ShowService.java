package com.ajeet.bookmyshow.service;

import com.ajeet.bookmyshow.model.Show;

import java.util.List;

public interface ShowService {
    Show create(Show show);
    Show update(Long id, Show show);
    void delete(Long id);
    List<Show> listAll();
    List<String> getAvailableSeats(Long showId);
}
