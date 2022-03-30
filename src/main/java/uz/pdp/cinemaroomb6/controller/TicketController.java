package uz.pdp.cinemaroomb6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cinemaroomb6.model.Users;
import uz.pdp.cinemaroomb6.repository.UserRepository;
import uz.pdp.cinemaroomb6.service.TicketServiceImpl;
import uz.pdp.cinemaroomb6.service.interfaces.TicketService;

import java.util.UUID;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    TicketServiceImpl ticketService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/add-to-cart")
    public HttpEntity<?> addToCart(@RequestParam("session-id")UUID sessionId,
                                   @RequestParam("seat-id")UUID seatId){
        return ticketService.addToCart(seatId,sessionId);
    }


    @PostMapping("/purchase")
    public HttpEntity<?> purchaseTicket(@RequestParam("ticket-id")UUID ticketId) {
        return ticketService.purchaseTicket(ticketId);
    }




    // TODO: 3/29/2022 todays

    @GetMapping("get-my-tickets")
    public HttpEntity<?> getUserTickets() {
        Users nurbek = userRepository.findByUsername("nurbek");
        return ticketService.getCurrentUsersTicket(nurbek.getId());
    }


}


