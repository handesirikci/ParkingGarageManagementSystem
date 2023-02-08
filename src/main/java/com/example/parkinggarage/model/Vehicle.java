package com.example.parkinggarage.model;


import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Inheritance
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String licencePlate;
    int requiredSpots;

    VehicleType vehicleType;
}
