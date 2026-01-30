package com.ajeet.bookmyshow.service;

import com.ajeet.bookmyshow.dto.BookingRequest;
import com.ajeet.bookmyshow.model.Booking;

import java.util.List;

public interface BookingService {
    Booking reserve(BookingRequest request);
    List<Booking> listAll();
}
