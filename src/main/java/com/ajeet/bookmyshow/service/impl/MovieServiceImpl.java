package com.ajeet.bookmyshow.service.impl;

import com.ajeet.bookmyshow.model.Movie;
import com.ajeet.bookmyshow.repository.MovieRepository;
import com.ajeet.bookmyshow.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> listAll() {
        return movieRepository.findAll();
    }
}
