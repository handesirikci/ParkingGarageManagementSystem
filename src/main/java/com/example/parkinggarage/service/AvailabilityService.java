package com.example.parkinggarage.service;

import com.example.parkinggarage.model.Bus;
import com.example.parkinggarage.model.ParkSpot;
import com.example.parkinggarage.model.Vehicle;
import com.example.parkinggarage.model.VehicleType;
import com.example.parkinggarage.repository.ParkSpotRepository;
import com.example.parkinggarage.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {
    final ParkSpotRepository parkSpotRepository;
    final VehicleRepository vehicleRepository;

    public boolean getSpot(Vehicle vehicle) {
        List<ParkSpot> allSpots = parkSpotRepository.findAll();
        boolean isParked = getVehicleSpots(allSpots, vehicle);
        parkSpotRepository.saveAll(allSpots);
        return isParked;
    }

    private boolean getVehicleSpots(List<ParkSpot> parkSpots, Vehicle vehicle) {
        VehicleType vehicleType = vehicle.getVehicleType();
        boolean isParked = false;
        if(vehicleType == VehicleType.BUS) {
            isParked = getBusSpots(parkSpots, vehicle);
        } else if(vehicleType == VehicleType.CAR) {
            isParked = getCarSpot(parkSpots, vehicle);
        } else if(vehicleType == VehicleType.MOTORBIKE) {
            isParked = getMotorbikeSpot(parkSpots, vehicle);
        }
        return isParked;
    }

    private boolean getCarSpot(List<ParkSpot> parkSpots, Vehicle vehicle) {
        for(int i=0; i<parkSpots.size(); i++) {
            if(parkSpots.get(i).getVehicles().isEmpty()) {
                parkSpots.get(i).getVehicles().add(vehicle);
                return true;
            }
        }
        return false;
    }

    private boolean getBusSpots(List<ParkSpot> parkSpots, Vehicle vehicle) {
        for(int i=0; i<parkSpots.size()-Bus.REQUIRED_SPOTS; i++) {
            if(hasAdjacentSlots(parkSpots, i, 4)) {
                for(int j=0; j<Bus.REQUIRED_SPOTS; j++) {
                    parkSpots.get(i+j).getVehicles().add(vehicle);
                }
                return true;
            }
        }
        return false;
    }

    private boolean getMotorbikeSpot(List<ParkSpot> parkSpots, Vehicle vehicle) {
        for(int i=0; i<parkSpots.size(); i++) {
            if(parkSpots.get(i).getVehicles().isEmpty()) {
                parkSpots.get(i).getVehicles().add(vehicle);
                return true;
            } else if(parkSpots.get(i).getVehicles().size()==1 &&
                    parkSpots.get(i).getVehicles().get(0).getVehicleType().equals(VehicleType.MOTORBIKE)) {
                parkSpots.get(i).getVehicles().add(vehicle);
                return true;
            }
        }
        return false;
    }
    private boolean hasAdjacentSlots(List<ParkSpot> parkSpots, int startIndex, int requiredSpace) {
        boolean isFitting = true;
        int floorNum = parkSpots.get(startIndex).getFloor().getNumber();
        for(int i=0; i<requiredSpace; i++) {
            isFitting = isFitting && parkSpots.get(startIndex+i).getVehicles().isEmpty();
            isFitting = isFitting && (floorNum == parkSpots.get(startIndex+i).getFloor().getNumber());
        } if (isFitting) {
            return true;
        }
        return false;
    }
}
