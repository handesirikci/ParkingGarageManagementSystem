package com.example.parkinggarage.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(cascade = {CascadeType.ALL})
    Vehicle vehicle;

    Instant checkIn;
    Instant checkOut;
    int paymentAmount;
}
