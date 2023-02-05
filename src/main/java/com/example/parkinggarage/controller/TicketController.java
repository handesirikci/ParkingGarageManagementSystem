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
        return ticket.isPresent() ? ResponseEntity.ok(ticket.get()) : null;
    }

    @PostMapping("/checkOut")
    ResponseEntity<Ticket> checkOut(@RequestBody Vehicle vehicle) {
        Optional<Ticket> ticket = Optional.ofNullable(ticketService.checkOut(vehicle));
        return ticket.isPresent() ? ResponseEntity.ok(ticket.get()) : null;
    }

}
