package com.example.parkinggarage.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
@DiscriminatorValue("MOTORBIKE")
public class Motorbike extends Vehicle{
    static final int REQUIRED_SPOTS = 1;
    public Motorbike(String licencePlate) {
        requiredSpots = REQUIRED_SPOTS;
        vehicleType = VehicleType.MOTORBIKE;
    }
}
