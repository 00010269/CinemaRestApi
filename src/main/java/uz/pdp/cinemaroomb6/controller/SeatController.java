package uz.pdp.cinemaroomb6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.cinemaroomb6.service.SeatService;

import java.util.UUID;

@RestController
@RequestMapping("/api/seat")
public class SeatController {

    @Autowired
    SeatService seatService;

    @GetMapping("/available-session-seats/{session-id}")
    public HttpEntity getAvailableSessionSeats(@PathVariable("session-id") UUID sessionId) {
        return seatService.getAvailableSessionSeats(sessionId);
    }


}

