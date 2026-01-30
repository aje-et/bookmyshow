package com.ajeet.bookmyshow.controller;

import com.ajeet.bookmyshow.model.Theatre;
import com.ajeet.bookmyshow.service.TheatreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {

    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @PostMapping
    public ResponseEntity<Theatre> create(@RequestBody Theatre theatre) {
        return ResponseEntity.ok(theatreService.create(theatre));
    }

    @GetMapping
    public ResponseEntity<List<Theatre>> list() {
        return ResponseEntity.ok(theatreService.listAll());
    }
}
