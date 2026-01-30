package com.ajeet.bookmyshow.service.impl;

import com.ajeet.bookmyshow.dto.*;
import com.ajeet.bookmyshow.model.Booking;
import com.ajeet.bookmyshow.model.Show;
import com.ajeet.bookmyshow.repository.BookingRepository;
import com.ajeet.bookmyshow.repository.ShowRepository;
import com.ajeet.bookmyshow.service.SearchService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private final ShowRepository showRepository;
    private final BookingRepository bookingRepository;

    public SearchServiceImpl(ShowRepository showRepository, BookingRepository bookingRepository) {
        this.showRepository = showRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public SearchResponseDTO searchShows(Long movieId, String city, LocalDate date, int page, int size) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        List<Show> shows = showRepository.findShowsByMovieAndCityAndDate(movieId, start, end, city);

        // group by theatre -> auditorium
        Map<Long, TheatreSearchResultDTO> theatreMap = new LinkedHashMap<>();

        for (Show s : shows) {
            Long theatreId = s.getTheatre().getId();
            TheatreSearchResultDTO theatre = theatreMap.computeIfAbsent(theatreId, id -> {
                TheatreSearchResultDTO t = new TheatreSearchResultDTO();
                t.setTheatreId(id);
                t.setName(s.getTheatre().getName());
                t.setAddress(s.getTheatre().getAddress());
                t.setCity(s.getTheatre().getCity());
                return t;
            });

            // auditorium
            Long audId = s.getAuditorium().getId();
            AuditoriumResultDTO aud = theatre.getAuditoriums().stream()
                    .filter(a -> a.getAuditoriumId().equals(audId)).findFirst()
                    .orElseGet(() -> {
                        AuditoriumResultDTO a = new AuditoriumResultDTO();
                        a.setAuditoriumId(audId);
                        a.setName(s.getAuditorium().getName());
                        theatre.getAuditoriums().add(a);
                        return a;
                    });

            // compute seats
            int totalSeats = Optional.ofNullable(s.getAuditorium().getSeats()).map(List::size).orElse(0);
            List<String> statuses = Arrays.asList("RESERVED", "CONFIRMED");
            List<Booking> bookings = bookingRepository.findByShowIdAndStatusIn(s.getId(), statuses);
            int bookedSeats = bookings.stream().mapToInt(b -> Optional.ofNullable(b.getSeats()).map(List::size).orElse(0)).sum();
            int available = Math.max(0, totalSeats - bookedSeats);

            ShowResultDTO showDto = new ShowResultDTO();
            showDto.setShowId(s.getId());
            showDto.setStartTime(s.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            showDto.setPrice(s.getPrice());
            showDto.setTotalSeats(totalSeats);
            showDto.setAvailableSeats(available);
            showDto.setBookingUrl("/bookings/" + s.getId());

            aud.getShows().add(showDto);
        }

        List<TheatreSearchResultDTO> results = new ArrayList<>(theatreMap.values());

        // simple pagination in-memory
        int from = Math.min(page * size, results.size());
        int to = Math.min(from + size, results.size());
        List<TheatreSearchResultDTO> pageList = results.subList(from, to);

        SearchResponseDTO resp = new SearchResponseDTO();
        resp.setMovieId(movieId);
        resp.setMovieTitle(shows.isEmpty() ? null : shows.get(0).getMovie().getTitle());
        resp.setDate(date.toString());
        resp.setCity(city);
        resp.setPage(page);
        resp.setSize(size);
        resp.setTotalResults(results.size());
        resp.setResults(pageList);
        return resp;
    }
}
