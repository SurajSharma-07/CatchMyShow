package com.example.movietickets.dto;
import jakarta.validation.constraints.*;
public record MovieAdminRequest(@NotBlank String title,@NotBlank String genre,@NotNull @Positive Integer durationMinutes,@NotBlank String description,@NotBlank String posterUrl,@NotNull @DecimalMin("0.0") Double rating,boolean featured) {}
