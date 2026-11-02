package com.example.movietickets.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Booking {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String bookingCode;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) private Showtime showtime;
    @Column(nullable=false) private String customerName;
    @Column(nullable=false) private String customerEmail;
    @Column(nullable=false) private Double totalAmount;
    @Column(nullable=false) private LocalDateTime bookedAt;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentReference;
    @OneToMany(mappedBy="booking", cascade=CascadeType.ALL, orphanRemoval=true) private List<BookingSeat> seats=new ArrayList<>();
    public Booking() {}
    public Booking(Showtime showtime,String customerName,String customerEmail,Double totalAmount,String bookingCode,String paymentMethod,String paymentStatus,String paymentReference){this.showtime=showtime;this.customerName=customerName;this.customerEmail=customerEmail;this.totalAmount=totalAmount;this.bookingCode=bookingCode;this.bookedAt=LocalDateTime.now();this.paymentMethod=paymentMethod;this.paymentStatus=paymentStatus;this.paymentReference=paymentReference;}
    public Long getId(){return id;} public String getBookingCode(){return bookingCode;} public Showtime getShowtime(){return showtime;} public String getCustomerName(){return customerName;} public String getCustomerEmail(){return customerEmail;} public Double getTotalAmount(){return totalAmount;} public LocalDateTime getBookedAt(){return bookedAt;} public String getPaymentMethod(){return paymentMethod;} public String getPaymentStatus(){return paymentStatus;} public String getPaymentReference(){return paymentReference;} public List<BookingSeat> getSeats(){return seats;} public void addSeat(BookingSeat seat){seats.add(seat);seat.setBooking(this);}
}
