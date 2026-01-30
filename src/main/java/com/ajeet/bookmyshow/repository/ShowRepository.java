package com.ajeet.bookmyshow.repository;

import com.ajeet.bookmyshow.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query("select s from Show s join fetch s.auditorium a join fetch s.theatre t where s.movie.id = :movieId and s.startTime >= :start and s.startTime < :end and t.city = :city order by t.name, s.startTime")
    List<Show> findShowsByMovieAndCityAndDate(@Param("movieId") Long movieId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("city") String city);
}
