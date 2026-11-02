package com.example.movietickets.repository;
import com.example.movietickets.model.Booking; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface BookingRepository extends JpaRepository<Booking,Long>{ Optional<Booking> findByBookingCode(String bookingCode); Optional<Booking> findFirstByCustomerEmailIgnoreCaseOrderByBookedAtDesc(String customerEmail); }
