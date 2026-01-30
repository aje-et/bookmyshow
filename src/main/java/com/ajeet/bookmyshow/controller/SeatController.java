package com.ajeet.bookmyshow.controller;

import com.ajeet.bookmyshow.model.Seat;
import com.ajeet.bookmyshow.service.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    public ResponseEntity<Seat> create(@RequestBody Seat seat) {
        return ResponseEntity.ok(seatService.create(seat));
    }

    @GetMapping("/auditorium/{auditoriumId}")
    public ResponseEntity<List<Seat>> listByAuditorium(@PathVariable Long auditoriumId) {
        return ResponseEntity.ok(seatService.listByAuditorium(auditoriumId));
    }
}
