package uz.pdp.cinemaroomb6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.cinemaroomb6.service.TicketServiceImpl;
import uz.pdp.cinemaroomb6.service.interfaces.TicketService;

import java.util.UUID;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    TicketServiceImpl ticketService;

    @GetMapping("/add-to-cart")
    public HttpEntity<?> addToCart(@RequestParam("session-id")UUID sessionId,
                                   @RequestParam("seat-id")UUID seatId){
        return ticketService.addToCart(seatId,sessionId);
    }

    
}


