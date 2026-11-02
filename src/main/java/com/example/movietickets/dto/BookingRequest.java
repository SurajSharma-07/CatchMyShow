package com.example.movietickets.dto;
import jakarta.validation.constraints.*; import java.util.List;
public record BookingRequest(@NotNull Long showtimeId,@NotBlank String customerName,@Email @NotBlank String customerEmail,@NotEmpty List<@NotBlank String> seats,@NotBlank String paymentMethod) {}
