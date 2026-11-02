package com.example.movietickets.repository;
import com.example.movietickets.model.Showtime; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface ShowtimeRepository extends JpaRepository<Showtime,Long>{ List<Showtime> findByMovieIdAndActiveTrueOrderByStartTime(Long movieId); @Query("select s from Showtime s join fetch s.movie where s.id=:id and s.active=true and s.movie.active=true") Optional<Showtime> findDetailedActive(@Param("id") Long id); }
