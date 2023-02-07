package com.example.parkinggarage.service;

import com.example.parkinggarage.model.Floor;
import com.example.parkinggarage.model.ParkSpot;
import com.example.parkinggarage.model.Ticket;
import com.example.parkinggarage.model.Vehicle;
import com.example.parkinggarage.repository.FloorRepository;
import com.example.parkinggarage.repository.ParkSpotRepository;
import com.example.parkinggarage.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GarageService {

    final FloorRepository floorRepository;
    final ParkSpotRepository parkSpotRepository;

    public void createGarage(int numberOfFloors, int numberOfParkingSpots) {
        ArrayList<Floor> floors = new ArrayList<>();
        for (int i = 0; i < numberOfFloors; i++) {
            Floor floor = new Floor();
            floor.setNumber(i + 1);
            ArrayList<ParkSpot> parkSpots = new ArrayList<>();
            floor.setParkSpots(parkSpots);
            for (int j = 0; j < numberOfParkingSpots; j++) {
                ParkSpot p = new ParkSpot();
                p.setNumber(j + 1);
                p.setFloor(floor);
                parkSpots.add(p);
            }
            floors.add(floor);
        }
        floorRepository.saveAll(floors);
    }

    public int getSpots() {
        int result = 0;
        for(ParkSpot parkSpot: parkSpotRepository.findAll()) {
            if(parkSpot.getVehicles().size() ==0) {
                result++;
            }
        }
        return result;
    }

}
