package com.ajeet.bookmyshow.service;

import com.ajeet.bookmyshow.dto.SearchResponseDTO;
import java.time.LocalDate;

public interface SearchService {
    SearchResponseDTO searchShows(Long movieId, String city, LocalDate date, int page, int size);
}
