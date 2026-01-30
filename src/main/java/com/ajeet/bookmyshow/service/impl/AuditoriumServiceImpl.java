package com.ajeet.bookmyshow.service.impl;

import com.ajeet.bookmyshow.model.Auditorium;
import com.ajeet.bookmyshow.repository.AuditoriumRepository;
import com.ajeet.bookmyshow.service.AuditoriumService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriumServiceImpl implements AuditoriumService {

    private final AuditoriumRepository auditoriumRepository;

    public AuditoriumServiceImpl(AuditoriumRepository auditoriumRepository) {
        this.auditoriumRepository = auditoriumRepository;
    }

    @Override
    public Auditorium create(Auditorium auditorium) {
        return auditoriumRepository.save(auditorium);
    }

    @Override
    public List<Auditorium> listAll() {
        return auditoriumRepository.findAll();
    }
}
