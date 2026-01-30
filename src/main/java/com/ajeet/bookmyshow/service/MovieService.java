package com.ajeet.bookmyshow.service;

import com.ajeet.bookmyshow.model.Movie;
import java.util.List;

public interface MovieService {
    Movie create(Movie movie);
    List<Movie> listAll();
}
