package com.example.movietickets.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"movie_id","start_time"}))
public class Showtime {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JsonIgnore private Movie movie;
    @Column(name="start_time", nullable=false) private LocalDateTime startTime;
    @Column(nullable=false) private String screenName;
    @Column(nullable=false) private Double ticketPrice;
    private Double classicPrice; private Double premiumPrice; private Double reclinerPrice;
    private String format="2D"; private Double formatSurcharge=0.0; private boolean active=true;
    private int totalSeats=48;
    public Showtime() {}
    public Showtime(Movie movie, LocalDateTime startTime, String screenName, Double ticketPrice){this.movie=movie;this.startTime=startTime;this.screenName=screenName;this.ticketPrice=ticketPrice;this.classicPrice=ticketPrice;this.premiumPrice=ticketPrice+50;this.reclinerPrice=ticketPrice+150;this.format="2D";this.formatSurcharge=0.0;this.active=true;}
    public Long getId(){return id;} public Movie getMovie(){return movie;} public LocalDateTime getStartTime(){return startTime;} public String getScreenName(){return screenName;} public Double getTicketPrice(){return classicPrice==null?ticketPrice:classicPrice+(formatSurcharge==null?0:formatSurcharge);} public Double getClassicPrice(){return classicPrice;} public Double getPremiumPrice(){return premiumPrice;} public Double getReclinerPrice(){return reclinerPrice;} public String getFormat(){return format;} public Double getFormatSurcharge(){return formatSurcharge==null?0.0:formatSurcharge;} public boolean isActive(){return active;} public int getTotalSeats(){return totalSeats;}
    public void update(Movie movie,LocalDateTime startTime,String screenName,String format,Double formatSurcharge,Double classicPrice,Double premiumPrice,Double reclinerPrice){this.movie=movie;this.startTime=startTime;this.screenName=screenName;this.format=format;this.formatSurcharge=formatSurcharge;this.classicPrice=classicPrice;this.premiumPrice=premiumPrice;this.reclinerPrice=reclinerPrice;this.ticketPrice=classicPrice+formatSurcharge;}
    public void setActive(boolean active){this.active=active;}
    public void migratePricing(){if(classicPrice==null)classicPrice=ticketPrice==null?180.0:ticketPrice;if(premiumPrice==null)premiumPrice=classicPrice+50;if(reclinerPrice==null)reclinerPrice=classicPrice+150;if(format==null)format="2D";if(formatSurcharge==null)formatSurcharge=0.0;if(ticketPrice==null)ticketPrice=classicPrice+formatSurcharge;}
}
