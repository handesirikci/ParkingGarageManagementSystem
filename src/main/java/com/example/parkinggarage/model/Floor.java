package com.example.parkinggarage.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int number;
    @OneToMany(cascade = {CascadeType.ALL})
    List<ParkSpot> parkSpots;

}
