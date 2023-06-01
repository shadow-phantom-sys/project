package com.rideManagement.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.context.RideDbContext;
import com.rideManagement.model.DTOs.BookingDTO;
import com.rideManagement.model.DTOs.FareDTO;
import com.rideManagement.model.DTOs.RideDTO;
import com.rideManagement.model.DTOs.SearchDTO;
import com.rideManagement.service.RideService;

@RestController
@RequestMapping("api/")
public class RideController {

    private final RideDbContext context;
    private final RideService service;			

    @Autowired
    public RideController(RideDbContext context, JMapper mapper) {
        this.context = context;
        this.service = new RideService(context, mapper);
    }

    @GetMapping("distances")
    public ResponseEntity<Object> getDistances() {
        Object r = service.getDistances();
        return ResponseEntity.ok(r);
    }

    @GetMapping("rides/calculatefare")
    public ResponseEntity<Object> getRideFare(@RequestParam FareDTO fareDTO) {
        Object r = service.getFare(fareDTO);
        return ResponseEntity.ok(r);
    }

    @PostMapping("rides/schedule")
    public ResponseEntity<Object> scheduleRide(@RequestBody RideDTO rideDTO) {
        Object r = service.scheduleRide(rideDTO);
        return ResponseEntity.ok(r);
    }

    @GetMapping("rides/search")
    public ResponseEntity<Object> searchRides(@RequestParam SearchDTO searchDTO) {
        Object r = service.searchRide(searchDTO);
        return ResponseEntity.ok(r);
    }

    @PostMapping("rides/book")
    public ResponseEntity<Object> bookRide(@RequestBody BookingDTO bookingDTO) {
        Object r = service.bookRide(bookingDTO);
        return ResponseEntity.ok(r);
    }

    @GetMapping("GetRegNo/{userId}")
    public ResponseEntity<Object> getRegNo(@PathVariable int userId) {
        Object r = service.getRegNo(userId);
        return ResponseEntity.ok(r);
    }
}
