package com.example.parkinggarage.controller;

import com.example.parkinggarage.model.Ticket;
import com.example.parkinggarage.model.Vehicle;
import com.example.parkinggarage.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TicketController {
    Logger logger = LoggerFactory.getLogger(TicketController.class);
    final TicketService ticketService;

    @PostMapping("/checkIn")
    ResponseEntity<Ticket> checkIn(@RequestBody Vehicle vehicle) {
        Optional<Ticket> ticket = Optional.ofNullable(ticketService.checkIn(vehicle));
        printCheckInMessage(ticket);
        return ticket.isPresent() ? ResponseEntity.ok(ticket.get()) : null;
    }

    @PostMapping("/checkOut")
    ResponseEntity<Ticket> checkOut(@RequestBody Vehicle vehicle) {
        Optional<Ticket> ticket = Optional.ofNullable(ticketService.checkOut(vehicle));
        printCheckOutMessage(ticket);
        return ticket.isPresent() ? ResponseEntity.ok(ticket.get()) : null;
    }

    private void printCheckInMessage(Optional<Ticket> ticket) {
        if(ticket.isPresent()) {
            logger.info("Check-in time "+ getDateOfInstant(ticket.get().getCheckIn())+
                    " for the vehicle with "+ticket.get().getVehicle().getLicencePlate()+" licence plate");
        } else {
            logger.info("Ticket not created since no space found for the vehicle");
        }
    }

    private void printCheckOutMessage(Optional<Ticket> ticket) {
        if(ticket.isPresent()) {
            logger.info("Check-out time "+ getDateOfInstant(ticket.get().getCheckOut())+
                    " for the vehicle with "+ticket.get().getVehicle().getLicencePlate()+" licence plate. Total payment is "+
                    ticket.get().getPaymentAmount());
        } else {
            logger.info("Related ticket not found");
        }
    }

    private String getDateOfInstant(Instant instant) {
        Date myDate = Date.from(instant);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
        String formattedDate = formatter.format(myDate);
        return formattedDate;
    }

}
