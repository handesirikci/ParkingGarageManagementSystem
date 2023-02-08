package com.example.parkinggarage.service;

import com.example.parkinggarage.model.Ticket;
import com.example.parkinggarage.model.Vehicle;
import com.example.parkinggarage.repository.ParkSpotRepository;
import com.example.parkinggarage.repository.TicketRepository;
import com.example.parkinggarage.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
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
        Optional<Vehicle> vehicleMaybe = vehicleRepository.findByLicencePlate(vehicle.getLicencePlate());
        Vehicle existingVehicle;
        if (vehicleMaybe.isEmpty()) {
            existingVehicle = vehicleRepository.save(vehicle);
        } else {
            existingVehicle = vehicleMaybe.get();
        }
        Optional<Ticket> ticketMaybe = ticketRepository.findByCheckOutIsNullAndVehicle_LicencePlateEquals(vehicle.getLicencePlate());
        if(ticketMaybe.isPresent()) {
            logger.info("Vehicle is already checked in!");
            return ticketMaybe.get();
        }
        boolean isParked = availabilityService.getSpot(existingVehicle);
        if(!isParked) {
            printUnsuccessfullCheckInMessage();
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setCheckIn(Instant.now());
        ticket.setVehicle(existingVehicle);
        printSuccessfullCheckInMessage(ticket);
        ticketRepository.save(ticket);
        return ticket;
    }

    public Ticket checkOut(Vehicle vehicle) {
        Instant checkOutTime = Instant.now();
        Optional<Ticket> ticket = ticketRepository.findByCheckOutIsNullAndVehicle_LicencePlateEquals(vehicle.getLicencePlate());
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
            printSuccessfullCheckOutMessage(ticket.get());
            ticketRepository.save(ticket.get());
            return ticket.get();
        }
        printUnsuccessfullCheckOutMessage();
        return null;
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

    private void printSuccessfullCheckInMessage(Ticket ticket) {
        logger.info("Check-in time "+ getDateOfInstant(ticket.getCheckIn())+
                " for the vehicle with "+ticket.getVehicle().getLicencePlate()+" licence plate");
    }

    private void printUnsuccessfullCheckInMessage() {
        logger.info("Ticket not created since no space found for the vehicle");
    }

    private void printSuccessfullCheckOutMessage(Ticket ticket) {
        logger.info("Check-out time "+ getDateOfInstant(ticket.getCheckOut())+
                " for the vehicle with "+ticket.getVehicle().getLicencePlate()+" licence plate. Total payment is "+
                    ticket.getPaymentAmount());
    }

    private void printUnsuccessfullCheckOutMessage() {
        logger.info("Vehicle has no check-in ticket!");
    }

    private String getDateOfInstant(Instant instant) {
        Date myDate = Date.from(instant);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
        String formattedDate = formatter.format(myDate);
        return formattedDate;
    }

    private Optional<Ticket> getTicketIfExists(Vehicle vehicle) {
        for(Ticket ticket: ticketRepository.findAll()) {
            if(ticket.getVehicle().equals(vehicle)) {
                return Optional.of(ticket);
            }
        }
        return Optional.empty();
    }

}
