package com.example.movietickets.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String title;
    private String genre;
    private Integer durationMinutes;
    @Column(length=1000) private String description;
    private String posterUrl;
    private Double rating;
    private boolean featured;
    private boolean active=true;
    @OneToMany(mappedBy="movie", fetch=FetchType.LAZY) @JsonIgnore private List<Showtime> showtimes;
    public Movie() {}
    public Movie(String title, String genre, Integer durationMinutes, String description, String posterUrl, Double rating, boolean featured) { this.title=title; this.genre=genre; this.durationMinutes=durationMinutes; this.description=description; this.posterUrl=posterUrl; this.rating=rating; this.featured=featured; this.active=true; }
    public Long getId(){return id;} public String getTitle(){return title;} public String getGenre(){return genre;} public Integer getDurationMinutes(){return durationMinutes;} public String getDescription(){return description;} public String getPosterUrl(){return posterUrl;} public Double getRating(){return rating;} public boolean isFeatured(){return featured;} public boolean isActive(){return active;}
    public void update(String title,String genre,Integer durationMinutes,String description,String posterUrl,Double rating,boolean featured){this.title=title;this.genre=genre;this.durationMinutes=durationMinutes;this.description=description;this.posterUrl=posterUrl;this.rating=rating;this.featured=featured;}
    public void setActive(boolean active){this.active=active;}
}
