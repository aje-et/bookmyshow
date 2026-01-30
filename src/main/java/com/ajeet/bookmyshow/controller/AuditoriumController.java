package com.ajeet.bookmyshow.controller;

import com.ajeet.bookmyshow.model.Auditorium;
import com.ajeet.bookmyshow.service.AuditoriumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditoriums")
public class AuditoriumController {

    private final AuditoriumService auditoriumService;

    public AuditoriumController(AuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
    }

    @PostMapping
    public ResponseEntity<Auditorium> create(@RequestBody Auditorium auditorium) {
        return ResponseEntity.ok(auditoriumService.create(auditorium));
    }

    @GetMapping
    public ResponseEntity<List<Auditorium>> list() {
        return ResponseEntity.ok(auditoriumService.listAll());
    }
}
