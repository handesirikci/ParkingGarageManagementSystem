package com.example.parkinggarage.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
@DiscriminatorValue("BUS")
public class Bus extends Vehicle{
    public static final int REQUIRED_SPOTS = 4;

}
