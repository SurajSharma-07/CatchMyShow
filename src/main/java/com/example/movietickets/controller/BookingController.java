package com.example.movietickets.controller;
import com.example.movietickets.dto.BookingRequest; import com.example.movietickets.model.Booking; import com.example.movietickets.service.BookingService; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.transaction.annotation.Transactional; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException; import java.util.*;
@RestController @RequestMapping("/api/bookings")
public class BookingController { private final BookingService service; public BookingController(BookingService service){this.service=service;}
 @PostMapping public ResponseEntity<?> create(@Valid @RequestBody BookingRequest request){try{return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(service.create(request)));}catch(IllegalStateException|IllegalArgumentException e){return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()));}}
 @Transactional(readOnly=true) @GetMapping("/lookup") public Map<String,Object> lookup(@RequestParam String email){try{return toResponse(service.findLatestByEmail(email));}catch(NoSuchElementException e){throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No booking found for this email");}}
 @Transactional(readOnly=true) @GetMapping("/{code}") public Map<String,Object> get(@PathVariable String code){try{return toResponse(service.find(code));}catch(NoSuchElementException e){throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Booking not found");}}
 private Map<String,Object> toResponse(Booking b){
  Map<String,Object> response=new LinkedHashMap<>();
  response.put("bookingCode",b.getBookingCode()); response.put("customerName",b.getCustomerName()); response.put("customerEmail",b.getCustomerEmail()); response.put("totalAmount",b.getTotalAmount()); response.put("bookedAt",b.getBookedAt());
  response.put("paymentMethod",java.util.Optional.ofNullable(b.getPaymentMethod()).orElse("N/A")); response.put("paymentStatus",java.util.Optional.ofNullable(b.getPaymentStatus()).orElse("N/A")); response.put("paymentReference",java.util.Optional.ofNullable(b.getPaymentReference()).orElse("N/A"));
  response.put("movieTitle",b.getShowtime().getMovie().getTitle()); response.put("showtime",b.getShowtime().getStartTime()); response.put("screen",b.getShowtime().getScreenName()); response.put("seats",b.getSeats().stream().map(s->s.getSeatNumber()).toList());
  return response;
}
}
