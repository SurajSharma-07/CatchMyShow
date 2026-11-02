package com.example.movietickets.model;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"showtime_id","seat_number"}))
public class BookingSeat {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) private Booking booking;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) private Showtime showtime;
    @Column(nullable=false) private String seatNumber;
    public BookingSeat() {} public BookingSeat(Showtime showtime,String seatNumber){this.showtime=showtime;this.seatNumber=seatNumber;}
    public Long getId(){return id;} public Booking getBooking(){return booking;} public void setBooking(Booking booking){this.booking=booking;} public Showtime getShowtime(){return showtime;} public String getSeatNumber(){return seatNumber;}
}
