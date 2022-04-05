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
import uz.pdp.cinemaroomb6.payload.ApiResponse;
import uz.pdp.cinemaroomb6.repository.*;
import uz.pdp.cinemaroomb6.service.StripePaymentServiceImpl;
import uz.pdp.cinemaroomb6.service.TicketServiceImpl;

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

    @Autowired
    CashBoxRepository cashBoxRepository;

    @Autowired
    TicketServiceImpl ticketService;

    @Autowired
    StripePaymentServiceImpl stripePaymentService;

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
    public HttpEntity<?> handle(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        return stripePaymentService.handle(payload, sigHeader);
    }

}
