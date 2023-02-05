package com.example.parkinggarage.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ParkSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    Floor floor;
    int number;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    List<Vehicle> vehicles;
}
