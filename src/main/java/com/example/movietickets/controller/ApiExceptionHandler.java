package com.example.movietickets.controller;
import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestControllerAdvice public class ApiExceptionHandler { @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<?> validation(MethodArgumentNotValidException e){return ResponseEntity.badRequest().body(Map.of("message","Please provide a valid name, email, showtime and at least one seat."));} }
