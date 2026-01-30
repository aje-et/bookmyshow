package com.ajeet.bookmyshow.service.impl;

import com.ajeet.bookmyshow.model.Theatre;
import com.ajeet.bookmyshow.repository.TheatreRepository;
import com.ajeet.bookmyshow.service.TheatreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreServiceImpl implements TheatreService {

    private final TheatreRepository theatreRepository;

    public TheatreServiceImpl(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    @Override
    public Theatre create(Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    @Override
    public List<Theatre> listAll() {
        return theatreRepository.findAll();
    }
}
