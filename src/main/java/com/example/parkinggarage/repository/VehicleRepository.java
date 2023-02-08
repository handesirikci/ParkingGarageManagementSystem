package com.example.parkinggarage.repository;

import com.example.parkinggarage.model.Floor;
import com.example.parkinggarage.model.Ticket;
import com.example.parkinggarage.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
        Optional<Vehicle> findByLicencePlate(String licencePlate);
}
