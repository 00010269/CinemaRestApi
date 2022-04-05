package uz.pdp.cinemaroomb6.service.interfaces;

import com.stripe.model.checkout.Session;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.pdp.cinemaroomb6.dto.TicketDto;
import uz.pdp.cinemaroomb6.model.Ticket;

import java.util.List;
import java.util.UUID;


public interface TicketService {

    HttpEntity<?> addToCart(UUID seatId,UUID sessionId);

    void scheduleDeleteTicket(Ticket savedTicket);

    HttpEntity<?> purchaseTicket(UUID ticketId, Session session);

    HttpEntity<?> getCurrentUsersTicket(UUID id);


//    HttpEntity<?> refundTicket(List<TicketDto> ticketDtos); // TODO: 3/29/2022 test refund ticket

    HttpEntity<?> refundTicket(UUID ticketId);








}
