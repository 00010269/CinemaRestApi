package uz.pdp.cinemaroomb6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cinemaroomb6.model.Users;
import uz.pdp.cinemaroomb6.repository.UserRepository;
import uz.pdp.cinemaroomb6.service.StripePaymentServiceImpl;
import uz.pdp.cinemaroomb6.service.TicketServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    TicketServiceImpl ticketService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StripePaymentServiceImpl stripePaymentService;

    @PostMapping("/add-to-cart")
    public HttpEntity<?> addToCart(@RequestParam("session-id") UUID sessionId,
                                   @RequestParam("seat-id") UUID seatId) {
        return ticketService.addToCart(seatId, sessionId);
    }

    @GetMapping("/purchase")
    public HttpEntity<?> purchaseTicket() {
        return stripePaymentService.createStripeSession();
    }


    @GetMapping("/get-my-tickets")
    public HttpEntity<?> getUserTickets() {
        Users nurbek = userRepository.findByUsername("nurbek");
        return ticketService.getCurrentUsersTicket(nurbek.getId());
    }

    @PostMapping("/refund")
    public HttpEntity<?> refundTicket(@RequestParam("ticket-id") UUID ticketId) {
        return ticketService.refundTicket(ticketId);
    }



}


