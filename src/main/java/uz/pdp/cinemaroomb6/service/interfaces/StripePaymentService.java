package uz.pdp.cinemaroomb6.service.interfaces;

import com.stripe.model.checkout.Session;
import org.springframework.http.HttpEntity;

public interface StripePaymentService {

    HttpEntity<?> handle(String payload, String sigHeader);

    HttpEntity<?> fulfillOrder(Session session);

    boolean refundTicket(String paymentIntent, Double refundSum);

    HttpEntity<?> createStripeSession();



}
