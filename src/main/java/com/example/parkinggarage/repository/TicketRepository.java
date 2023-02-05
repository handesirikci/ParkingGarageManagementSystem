package com.example.parkinggarage.repository;

import com.example.parkinggarage.model.Floor;
import com.example.parkinggarage.model.Ticket;
import com.example.parkinggarage.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.vehicle.id = :vehicleId")
    Optional<Ticket> getTicketWithVehicle(@Param("vehicleId") Long vehicleId);
}
