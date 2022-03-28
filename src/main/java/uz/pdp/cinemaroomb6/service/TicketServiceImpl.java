package uz.pdp.cinemaroomb6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.cinemaroomb6.model.MovieSession;
import uz.pdp.cinemaroomb6.model.Seat;
import uz.pdp.cinemaroomb6.model.Ticket;
import uz.pdp.cinemaroomb6.model.enums.TicketStatus;
import uz.pdp.cinemaroomb6.payload.ApiResponse;
import uz.pdp.cinemaroomb6.projection.MovieSessionQRInfoProjection;
import uz.pdp.cinemaroomb6.repository.*;
import uz.pdp.cinemaroomb6.service.interfaces.TicketService;

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


    @Override
    public HttpEntity addToCart(UUID seatId, UUID sessionId) {
        try {
            MovieSession movieSession = movieSessionRepository.getById(sessionId);
            Seat seat =seatRepository.getById(seatId);
            MovieSessionQRInfoProjection movieSessionQRInfoProjection = movieSessionRepository.findBySessionIdQRInfo(sessionId);
            Double price = ticketRepository.getTicketPrice(seatId, sessionId);
            System.out.println(movieSessionQRInfoProjection.toString());
            Ticket ticket = new Ticket(
                    movieSession,
//                    seat, GenerateQRService.createQRCodeItext(movieSessionQRInfoProjection.toString(),"123"),
                    seat, GenerateQRService.createQRCode(movieSessionQRInfoProjection.toString(),"123"),
                    price,
                    userRepository.getById(UUID.fromString("d943770d-29a4-4ee3-937e-a132513fa4d5")),
                    TicketStatus.NEW
            );
            Ticket savedTicket =ticketRepository.save(ticket);
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
        timer.schedule(task, 120_000);
    }

}
