package com.ajeet.bookmyshow.service;

import com.ajeet.bookmyshow.model.Auditorium;

import java.util.List;

public interface AuditoriumService {
    Auditorium create(Auditorium auditorium);
    List<Auditorium> listAll();
}
