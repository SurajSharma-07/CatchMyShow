package com.example.movietickets.dto;
import jakarta.validation.constraints.*;
public record AuthRequest(@NotBlank @Email String email,@NotBlank @Size(min=6) String password,String fullName) {}
