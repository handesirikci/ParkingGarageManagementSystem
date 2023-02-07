package com.example.parkinggarage.controller;

import com.example.parkinggarage.service.AvailabilityService;
import com.example.parkinggarage.service.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
@RequiredArgsConstructor
public class GarageController {
    Logger logger = LoggerFactory.getLogger(GarageController.class);
    final AvailabilityService availabilityService;
    final GarageService garageService;

    @PostMapping("/createGarage/{numberOfFloors}/{numberOfParkSpots}")
    void createGarage(@PathVariable("numberOfFloors") Integer numberOfFloors, @PathVariable("numberOfParkSpots") Integer numberOfParkSpots) {
        garageService.createGarage(numberOfFloors, numberOfParkSpots);
        logger.info("Garage is created with "+ numberOfFloors+" floors and "+numberOfParkSpots+" parking spaces for each floor");
    }

    @GetMapping("/getSpots")
    void getSpots() {
        int availableSpots = garageService.getSpots();
        logger.info("Number of available parking spots is "+availableSpots);
    }
}
