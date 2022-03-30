package uz.pdp.cinemaroomb6.controller;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cinemaroomb6.model.PayType;
import uz.pdp.cinemaroomb6.model.PurchaseHistory;
import uz.pdp.cinemaroomb6.model.Ticket;
import uz.pdp.cinemaroomb6.model.Users;
import uz.pdp.cinemaroomb6.model.enums.TicketStatus;
import uz.pdp.cinemaroomb6.projection.TicketProjection;
import uz.pdp.cinemaroomb6.repository.PayTypeRepository;
import uz.pdp.cinemaroomb6.repository.PurchaseHistoryRepository;
import uz.pdp.cinemaroomb6.repository.TicketRepository;
import uz.pdp.cinemaroomb6.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@RestController
public class StripePaymentController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PayTypeRepository payTypeRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;

    @Value("sk_test_51KiHKuCuRSqWfGJhbQfJ8Y77cAKGYpkinzGr445Q9Pr8aiFp1D4q6wTRSCREdYRNs5urootDbppkz0HDWWLOdyQS00LD6npejD")
    String stripeApiKey;

    String endPointSecret = "whsec_68378f4535f7ef921a1b7a5b90c5f8974299856e9b1eb57f59754a2dbef21601";

    @RequestMapping(value = {"payment-succeeded"}, method = RequestMethod.GET)
    public HttpEntity<?> getSuccessMsg() {
        return ResponseEntity.ok("Payment succeeded");
    }

    @RequestMapping(value = {"payment-failed"}, method = RequestMethod.GET)
    public HttpEntity<?> getFailedMsg() {
        return ResponseEntity.ok("Payment failed");
    }

    @RequestMapping(value = "stripe-webhook", method = RequestMethod.POST)
    public Object handle(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        Stripe.apiKey = stripeApiKey;

        System.out.println("Got payload:->  " + payload);

        Event event = null;


        try {
            event = Webhook.constructEvent(payload, sigHeader, endPointSecret);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }


        // handle the checkout.session.completed.event
        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().get();

            //fulfill the purchase....
            fulfillOrder(session);

        }
        return "";

    }


    /**
     * Checkout dan olindi
     *
     * @param session
     */
    public void fulfillOrder(Session session) {

        // TODO: 3/29/2022 TICKET STATUSLARINI PURCHASEDGA
        //  UZGARTIRISH VA PURCHASED BULGAN TICKETLARNI
        //  PURCHASE HISTORYGA YOZISH


        List<TicketProjection> ticketProjections = ticketRepository.getTicketsByUserId(UUID.fromString(session.getClientReferenceId()));

        for (TicketProjection ticketView : ticketProjections) {
            Ticket ticket = ticketRepository.findById(ticketView.getTicketId()).get();

            if (!ticket.getStatus().equals(TicketStatus.NEW)) {
                continue;
            }

            purchaseHistoryRepository.save(new PurchaseHistory(ticket.getUser(), ticket));
            ticket.setStatus(TicketStatus.PURCHASED);
            ticketRepository.save(ticket);
        }

        System.out.println("Current User ID: " + session.getClientReferenceId());
    }


}
