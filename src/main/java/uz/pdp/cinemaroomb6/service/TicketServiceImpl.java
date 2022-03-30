package uz.pdp.cinemaroomb6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.cinemaroomb6.exceptions.NotFoundException;
import uz.pdp.cinemaroomb6.model.MovieSession;
import uz.pdp.cinemaroomb6.model.PurchaseHistory;
import uz.pdp.cinemaroomb6.model.Seat;
import uz.pdp.cinemaroomb6.model.Ticket;
import uz.pdp.cinemaroomb6.model.enums.TicketStatus;
import uz.pdp.cinemaroomb6.payload.ApiResponse;
import uz.pdp.cinemaroomb6.projection.MovieSessionQRInfoProjection;
import uz.pdp.cinemaroomb6.projection.TicketProjection;
import uz.pdp.cinemaroomb6.repository.*;
import uz.pdp.cinemaroomb6.service.interfaces.TicketService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    MovieSessionRepository movieSessionRepository;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;


    @Override
    public HttpEntity addToCart(UUID seatId, UUID sessionId) {
        try {
            MovieSession movieSession = movieSessionRepository.getById(sessionId);
            Seat seat = seatRepository.getById(seatId);
            MovieSessionQRInfoProjection movieSessionQRInfoProjection = movieSessionRepository.findBySessionIdQRInfo(sessionId);
            Double price = ticketRepository.getTicketPrice(seatId, sessionId);
            System.out.println(movieSessionQRInfoProjection.toString());
            Ticket ticket = new Ticket(
                    movieSession,
                    seat, GenerateQRService.createQRCodeItext(movieSessionQRInfoProjection.toString(),"123"),
//                    seat, GenerateQRService.createQRCode(movieSessionQRInfoProjection.toString(), "123"),
                    price,
                    userRepository.getById(UUID.fromString("0ab4c06b-488d-4a65-9a3f-ad68f7c65414")),
                    TicketStatus.NEW
            );
            Ticket savedTicket = ticketRepository.save(ticket);
            scheduleDeleteTicket(savedTicket);
            return ResponseEntity.ok(
                    new ApiResponse("success", true)
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponse("failed", false)
            );
        }
    }


    @Override
    public void scheduleDeleteTicket(Ticket savedTicket) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ticketRepository.delete(savedTicket);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 360_000);
    }


    @Override
    public HttpEntity<?> purchaseTicket(UUID ticketId) {

        try {
            Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                    () -> new NotFoundException("Ticket: " + ticketId + " not found")
            );
            ticket.setStatus(TicketStatus.PURCHASED);
            purchaseHistoryRepository.save(new PurchaseHistory(
                    userRepository.getById(ticket.getUser().getId()),
                            ticket,
                            null
            ));
            ticketRepository.save(ticket);
            return ResponseEntity.ok(
                    new ApiResponse("success", true)
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ApiResponse("failed", false)
            );
        }
    }


    @Override
    public HttpEntity<?> getCurrentUsersTicket(UUID id) {
        List<TicketProjection> ticketsByUserId = ticketRepository.getTicketsByUserId(id);
        return ResponseEntity.ok(ticketsByUserId);
    }



    @Override   // TODO: 3/29/2022 test refund ticket
    public HttpEntity<?> refundTicket(UUID ticketId) {
        return null;
    }





}
