package com.ajeet.bookmyshow.controller;

import com.ajeet.bookmyshow.model.Show;
import com.ajeet.bookmyshow.service.ShowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping
    public ResponseEntity<Show> create(@RequestBody Show show) {
        return ResponseEntity.ok(showService.create(show));
    }

    @GetMapping
    public ResponseEntity<List<Show>> list() {
        return ResponseEntity.ok(showService.listAll());
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<List<String>> availability(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getAvailableSeats(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<com.ajeet.bookmyshow.model.Show> update(@PathVariable Long id, @RequestBody com.ajeet.bookmyshow.model.Show show) {
        return ResponseEntity.ok(showService.update(id, show));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        showService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
