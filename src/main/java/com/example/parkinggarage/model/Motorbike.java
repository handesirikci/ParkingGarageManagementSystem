package com.example.parkinggarage.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
@DiscriminatorValue("MOTORBIKE")
public class Motorbike extends Vehicle{

}
