package uz.pdp.cinemaroomb6.service.interfaces;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.pdp.cinemaroomb6.model.Ticket;

import java.util.UUID;


public interface TicketService {

    HttpEntity<?> addToCart(UUID seatId,UUID sessionId);

    void scheduleDeleteTicket(Ticket savedTicket);
}
