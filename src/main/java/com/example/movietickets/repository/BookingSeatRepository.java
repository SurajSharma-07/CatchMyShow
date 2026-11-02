package com.example.movietickets.repository;
import com.example.movietickets.model.BookingSeat; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface BookingSeatRepository extends JpaRepository<BookingSeat,Long>{ @Query("select b.seatNumber from BookingSeat b where b.showtime.id=:showtimeId") Set<String> findBookedSeats(@Param("showtimeId") Long showtimeId); }
