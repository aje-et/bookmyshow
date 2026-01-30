package com.ajeet.bookmyshow.repository;

import com.ajeet.bookmyshow.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByShowIdAndStatusIn(Long showId, List<String> statuses);
}
