package com.ajeet.bookmyshow.service;

import com.ajeet.bookmyshow.dto.SearchResponseDTO;
import com.ajeet.bookmyshow.model.Auditorium;
import com.ajeet.bookmyshow.model.Booking;
import com.ajeet.bookmyshow.model.Seat;
import com.ajeet.bookmyshow.model.Show;
import com.ajeet.bookmyshow.model.Theatre;
import com.ajeet.bookmyshow.model.Movie;
import com.ajeet.bookmyshow.repository.BookingRepository;
import com.ajeet.bookmyshow.repository.ShowRepository;
import com.ajeet.bookmyshow.service.impl.SearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchServiceImplTest {

    @Mock
    ShowRepository showRepository;

    @Mock
    BookingRepository bookingRepository;

    @InjectMocks
    SearchServiceImpl searchService;

    private Show show;

    @BeforeEach
    void setUp() {
        show = new Show();
        show.setId(1L);
        Movie m = new Movie(); m.setId(100L); m.setTitle("Galactic Odyssey");
        show.setMovie(m);
        Theatre t = new Theatre(); t.setId(10L); t.setName("Cine"); t.setCity("Mumbai");
        show.setTheatre(t);
        Auditorium a = new Auditorium(); a.setId(101L); a.setName("Hall A");
        Seat s1 = new Seat(); s1.setLabel("A1");
        Seat s2 = new Seat(); s2.setLabel("A2");
        a.getSeats().add(s1); a.getSeats().add(s2);
        show.setAuditorium(a);
        show.setStartTime(LocalDateTime.of(2026,2,5,10,30));
        show.setPrice(BigDecimal.valueOf(499.00));
    }

    @Test
    void searchShows_returnsResultGroupedByTheatre() {
        when(showRepository.findShowsByMovieAndCityAndDate(anyLong(), any(), any(), any())).thenReturn(Collections.singletonList(show));
        when(bookingRepository.findByShowIdAndStatusIn(anyLong(), anyList())).thenReturn(Collections.emptyList());

        SearchResponseDTO resp = searchService.searchShows(100L, "Mumbai", LocalDate.of(2026,2,5), 0, 20);
        assertThat(resp.getResults()).hasSize(1);
        assertThat(resp.getResults().get(0).getAuditoriums()).hasSize(1);
        assertThat(resp.getResults().get(0).getAuditoriums().get(0).getShows()).hasSize(1);
    }

    @Test
    void searchShows_computesAvailableSeats_correctly() {
        Booking b = new Booking(); b.setSeats(Collections.singletonList("A1"));
        when(showRepository.findShowsByMovieAndCityAndDate(anyLong(), any(), any(), any())).thenReturn(Collections.singletonList(show));
        when(bookingRepository.findByShowIdAndStatusIn(anyLong(), anyList())).thenReturn(Collections.singletonList(b));

        SearchResponseDTO resp = searchService.searchShows(100L, "Mumbai", LocalDate.of(2026,2,5), 0, 20);
        int available = resp.getResults().get(0).getAuditoriums().get(0).getShows().get(0).getAvailableSeats();
        assertThat(available).isEqualTo(1);
    }

    @Test
    void searchShows_returnsNoContent_whenEmpty() {
        when(showRepository.findShowsByMovieAndCityAndDate(anyLong(), any(), any(), any())).thenReturn(Collections.emptyList());
        SearchResponseDTO resp = searchService.searchShows(100L, "Mumbai", LocalDate.of(2026,2,5), 0, 20);
        assertThat(resp.getResults()).isEmpty();
    }
}
