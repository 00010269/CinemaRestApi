package uz.pdp.cinemaroomb6.service;

import com.stripe.model.checkout.Session;
import lombok.SneakyThrows;
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
import uz.pdp.cinemaroomb6.projection.PdfTicketProjection;
import uz.pdp.cinemaroomb6.projection.TicketProjection;
import uz.pdp.cinemaroomb6.repository.*;
import uz.pdp.cinemaroomb6.service.interfaces.TicketService;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static uz.pdp.cinemaroomb6.utils.Constants.USER_ID;


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

    @Autowired
    StripePaymentServiceImpl stripePaymentService;

    @Autowired
    EmailSendServiceImpl emailSendService;

    @Autowired
    GenerateQRService qrService;


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
                    seat,
                    "" + movieSessionQRInfoProjection,
                    price,
                    userRepository.getById(UUID.fromString(USER_ID)), TicketStatus.NEW
////                    seat, GenerateQRService.createQRCodeItext(movieSessionQRInfoProjection.toString(), "123"),
////                    seat, GenerateQRService.createQRCode(movieSessionQRInfoProjection.toString(), "123"),

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
    public HttpEntity<?> purchaseTicket(UUID userId, Session session) {

        try {
            List<Ticket> ticketsByUserId = ticketRepository.findAllByUserIdAndStatus(userId, TicketStatus.NEW);
            double ticketPrice = 0;
            for (Ticket ticket : ticketsByUserId) {
                ticketPrice += ticketRepository.getTicketPrice(ticket.getSeat().getId(), ticket.getMovieSession().getId());
                ticket.setStatus(TicketStatus.PURCHASED);
                ticketRepository.save(ticket);
            }

            PdfTicketProjection projection = ticketRepository.getPdfTicket(ticketsByUserId.get(0).getId());
            qrService.createQRCode("This is QR Code");
            qrService.generatePdf(projection);

//            emailSendService.sendMessage(
//                    "wiut00010269@gmail.com",
//                    "What's up man",
//                    "How is it going your studies?"
//            );

            emailSendService.sendMessageWithAttachment(
                    "wiut00010269@gmail.com",
                    "What's up man",
                    "How is it going your studies?",
                    "E:\\Programming\\Unicorn\\Cinema ROOM Rest Service\\Cinema-room-b6\\src\\main\\resources\\NewTicket.pdf"
            );

            purchaseHistoryRepository.save(
                    new PurchaseHistory(ticketsByUserId, null, ticketPrice, false, session.getPaymentIntent())
            );

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


    @SneakyThrows
    @Override
    public HttpEntity<?> refundTicket(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() ->
                new NotFoundException("Ticket: " + ticketId + " not found")
        );
        if (ticket.getStatus().equals(TicketStatus.PURCHASED)) {
            LocalDate sessionDate =
                    movieSessionRepository.getDateByTicketMovieSessionId(ticket.getMovieSession());
            Period period = Period.between(LocalDate.now(), sessionDate);
            double refundSum;
            if (period.getDays() <= 1) {
                refundSum = ticket.getPrice() * 0.2 * 100;
            } else {
                refundSum = ticket.getPrice() * 0.5 * 100;
            }

            String paymentIntent = purchaseHistoryRepository.getPaymentIntent(ticketId);
            if (stripePaymentService.refundTicket(paymentIntent, refundSum)) {
                ticket.setStatus(TicketStatus.REFUNDED);
                ticketRepository.save(ticket);
                purchaseHistoryRepository.save(
                        new PurchaseHistory(Collections.singletonList(ticket), null, refundSum / 100,
                                true, null)
                );
                return ResponseEntity.ok(
                        new ApiResponse("success", true)
                );
            } else {
                return ResponseEntity.ok(
                        new ApiResponse("failed", false)
                );
            }
        } else {
            return ResponseEntity.ok(
                    new ApiResponse("failed", false)
            );
        }
    }


}
