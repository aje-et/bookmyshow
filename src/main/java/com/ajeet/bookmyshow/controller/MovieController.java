package com.ajeet.bookmyshow.controller;

import com.ajeet.bookmyshow.model.Movie;
import com.ajeet.bookmyshow.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.create(movie));
    }

    @GetMapping
    public ResponseEntity<List<Movie>> list() {
        return ResponseEntity.ok(movieService.listAll());
    }
}
