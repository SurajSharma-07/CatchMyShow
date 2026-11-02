package com.example.movietickets.dto;
import jakarta.validation.constraints.*; import java.time.LocalDateTime;
public record ShowtimeAdminRequest(@NotNull Long movieId,@NotNull LocalDateTime startTime,@NotBlank String screenName,@NotBlank String format,@NotNull @DecimalMin("0.0") Double formatSurcharge,@NotNull @DecimalMin("0.0") Double classicPrice,@NotNull @DecimalMin("0.0") Double premiumPrice,@NotNull @DecimalMin("0.0") Double reclinerPrice) {}
