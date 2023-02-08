package com.example.parkinggarage.repository;

import com.example.parkinggarage.model.ParkSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkSpotRepository extends JpaRepository<ParkSpot, Long> {
}
