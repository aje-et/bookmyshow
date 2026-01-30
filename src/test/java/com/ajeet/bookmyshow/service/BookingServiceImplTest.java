package com.ajeet.bookmyshow.service;

import com.ajeet.bookmyshow.dto.BookingRequest;
import com.ajeet.bookmyshow.exception.SeatUnavailableException;
import com.ajeet.bookmyshow.model.Auditorium;
import com.ajeet.bookmyshow.model.Booking;
import com.ajeet.bookmyshow.model.Seat;
import com.ajeet.bookmyshow.model.Show;
import com.ajeet.bookmyshow.repository.BookingRepository;
import com.ajeet.bookmyshow.repository.ShowRepository;
import com.ajeet.bookmyshow.repository.UserRepository;
import com.ajeet.bookmyshow.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {

    @Mock
    BookingRepository bookingRepository;

    @Mock
    ShowRepository showRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    com.ajeet.bookmyshow.service.lock.LockService lockService;

    @InjectMocks
    BookingServiceImpl bookingService;

    private Show show;

    @BeforeEach
    void setUp() {
        show = new Show();
        show.setId(100L);
        Auditorium auditorium = new Auditorium();
        auditorium.setSeats(new ArrayList<>());
        Seat s1 = new Seat(); s1.setId(1L); s1.setLabel("A1");
        Seat s2 = new Seat(); s2.setId(2L); s2.setLabel("A2");
        auditorium.getSeats().add(s1);
        auditorium.getSeats().add(s2);
        show.setAuditorium(auditorium);
    }

    @Test
    void reserve_successful_acquiresLocks_andSavesBooking() {
        BookingRequest req = new BookingRequest();
        req.setShowId(100L);
        req.setSeats(Arrays.asList("A1","A2"));

        when(showRepository.findById(100L)).thenReturn(Optional.of(show));
        when(lockService.tryLock(anyString(), anyString(), any(Duration.class))).thenReturn(true);
        when(bookingRepository.findByShowIdAndStatusIn(anyLong(), anyList())).thenReturn(Collections.emptyList());
        when(bookingRepository.save(any(Booking.class))).thenAnswer(inv -> {
            Booking b = inv.getArgument(0);
            b.setId(55L);
            return b;
        });

        Booking saved = bookingService.reserve(req);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(55L);
        assertThat(saved.getSeats()).containsExactlyInAnyOrder("A1","A2");
        assertThat(saved.getStatus()).isEqualTo("RESERVED");

        verify(lockService, times(2)).tryLock(anyString(), anyString(), any(Duration.class));
        verify(lockService, times(2)).unlock(anyString(), anyString());
    }

    @Test
    void reserve_fails_whenLockNotAcquired() {
        BookingRequest req = new BookingRequest();
        req.setShowId(100L);
        req.setSeats(Collections.singletonList("A1"));

        when(showRepository.findById(100L)).thenReturn(Optional.of(show));
        when(lockService.tryLock(anyString(), anyString(), any(Duration.class))).thenReturn(false);

        assertThatThrownBy(() -> bookingService.reserve(req))
                .isInstanceOf(SeatUnavailableException.class)
                .hasMessageContaining("Seat locked/unavailable");

        // No locks should have been acquired, so unlock should not be called
        verify(lockService, never()).unlock(anyString(), anyString());
    }

    @Test
    void reserve_fails_whenSeatAlreadyBooked() {
        BookingRequest req = new BookingRequest();
        req.setShowId(100L);
        req.setSeats(Collections.singletonList("A1"));

        when(showRepository.findById(100L)).thenReturn(Optional.of(show));
        when(lockService.tryLock(anyString(), anyString(), any(Duration.class))).thenReturn(true);

        Booking existing = new Booking();
        existing.setSeats(Collections.singletonList("A1"));
        when(bookingRepository.findByShowIdAndStatusIn(100L, Arrays.asList("RESERVED","CONFIRMED")))
                .thenReturn(Collections.singletonList(existing));

        assertThatThrownBy(() -> bookingService.reserve(req))
                .isInstanceOf(SeatUnavailableException.class)
                .hasMessageContaining("already booked");

        verify(lockService).unlock(anyString(), anyString());
    }
}
