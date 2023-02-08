package com.example.parkinggarage.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@DiscriminatorValue("CAR")
public class Car extends Vehicle{

}
