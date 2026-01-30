package com.ajeet.bookmyshow.controller;

import com.ajeet.bookmyshow.model.Partner;
import com.ajeet.bookmyshow.service.PartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping
    public ResponseEntity<Partner> create(@RequestBody Partner partner) {
        return ResponseEntity.ok(partnerService.create(partner));
    }

    @GetMapping
    public ResponseEntity<List<Partner>> list() {
        return ResponseEntity.ok(partnerService.listAll());
    }
}
