package com.example.parkinggarage.service;

import com.example.parkinggarage.controller.TicketController;
import com.example.parkinggarage.model.Ticket;
import com.example.parkinggarage.model.Vehicle;
import com.example.parkinggarage.repository.ParkSpotRepository;
import com.example.parkinggarage.repository.TicketRepository;
import com.example.parkinggarage.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    final TicketRepository ticketRepository;
    final VehicleRepository vehicleRepository;
    final ParkSpotRepository parkSpotRepository;
    final AvailabilityService availabilityService;

    Logger logger = LoggerFactory.getLogger(TicketService.class);

    List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket checkIn(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        Optional<Ticket> ticketMaybe = ticketRepository.getTicketWithVehicle(vehicle.getId());
        if(ticketMaybe.isPresent()) {
            logger.info("Vehicle is already checked in!");
            return ticketMaybe.get();
        }
        boolean isParked = availabilityService.getSpot(vehicle);
        if(!isParked) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setCheckIn(Instant.now());
        ticket.setVehicle(vehicle);
        ticketRepository.save(ticket);
        vehicleRepository.save(vehicle);
        return ticket;
    }

    public Ticket checkOut(Vehicle vehicle) {
        Instant checkOutTime = Instant.now();
        Optional<Ticket> ticket = ticketRepository.getTicketWithVehicle(vehicle.getId());
        if(ticket.isPresent()) {
            if(ticket.get().getPaymentAmount()!=0) {
                logger.info("Vehicle is already checked out!");
                return ticket.get();
            }
            ticket.get().setCheckOut(checkOutTime);
            Duration duration = Duration.between(checkOutTime, ticket.get().getCheckIn());
            int hours = duration.toHoursPart() + 1;
            int payment = calculatePayment(hours);
            ticket.get().setPaymentAmount(payment);
            ticketRepository.save(ticket.get());
            vehicleRepository.save(vehicle);
        }
        return ticket.isPresent() ? ticket.get() : null;
    }

    private int calculatePayment(int hours) {
        if(hours == 1) {
            return 3;
        } else if(hours == 2) {
            return 5;
        } else if(hours == 3) {
            return 7;
        } return 7 + (hours-3);
    }


}
