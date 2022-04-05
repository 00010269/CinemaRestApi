package uz.pdp.cinemaroomb6.service;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.Refund;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.cinemaroomb6.payload.ApiResponse;
import uz.pdp.cinemaroomb6.projection.TicketProjection;
import uz.pdp.cinemaroomb6.repository.TicketRepository;
import uz.pdp.cinemaroomb6.service.interfaces.StripePaymentService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static uz.pdp.cinemaroomb6.utils.Constants.*;

@Service
public class StripePaymentServiceImpl implements StripePaymentService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketServiceImpl ticketService;

    @Value("sk_test_51KiHKuCuRSqWfGJhbQfJ8Y77cAKGYpkinzGr445Q9Pr8aiFp1D4q6wTRSCREdYRNs5urootDbppkz0HDWWLOdyQS00LD6npejD")
    String stripeApiKey;

    String endPointSecret = "whsec_68378f4535f7ef921a1b7a5b90c5f8974299856e9b1eb57f59754a2dbef21601";


    @Override
    public boolean refundTicket(String paymentIntent, Double refundSum) {

        try {
            Stripe.apiKey = stripeApiKey;
            RefundCreateParams params = RefundCreateParams
                    .builder()
                    .setPaymentIntent(paymentIntent)
                    .setAmount(refundSum.longValue())
                    .build();

            Refund refund = Refund.create(params);
            return refund.getStatus().equals("succeeded");
        } catch (StripeException e) {
            e.printStackTrace();
            return false;
        }

    }


    @Override
    public HttpEntity<?> createStripeSession() {

        try {
            Stripe.apiKey = stripeApiKey;
            List<TicketProjection> userTickets = ticketRepository.getTicketsByUserId(
                    UUID.fromString(USER_ID)
            );
            ArrayList<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
            for (TicketProjection userTicket : userTickets) {
                SessionCreateParams.LineItem.PriceData.ProductData productData =
                        SessionCreateParams.LineItem.PriceData.ProductData
                                .builder()
                                .setName(userTicket.getMovieTitle())
                                .build();

                Double price = (userTicket.getPrice() + FIXED_STRIPE_FEE) / (1 - PERCENT_STRIPE_FEE) * 100;
                System.out.println(price);

                SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData
                        .builder()
                        .setCurrency("usd")
                        .setUnitAmount(price.longValue())
                        .setProductData(productData)
                        .build();


                SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem
                        .builder()
                        .setPriceData(priceData)
                        .setQuantity(1L)
                        .build();
                lineItems.add(lineItem);
            }

            SessionCreateParams params = SessionCreateParams
                    .builder()
                    .addAllLineItem(lineItems)
                    .setCancelUrl("http://localhost:8080/payment-failed")
                    .setSuccessUrl("http://localhost:8080/payment-succeeded")
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setClientReferenceId(USER_ID)
                    .build();


            Session session = Session.create(params);
            String url = session.getUrl();

            return ResponseEntity.ok(
                    new ApiResponse("success", true, url)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(
                    new ApiResponse("failed", false)
            );
        }
    }


    @Override
    public HttpEntity<?> handle(String payload, String sigHeader) {
        Stripe.apiKey = stripeApiKey;
        System.out.println("Got payload: " + payload);
        Event event = null;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endPointSecret);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }

        //Handle the checkout.session.completed event
        if (event.getType().equals("checkout.session.completed")) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().get();

            return fulfillOrder(session);
        } else {
            return ResponseEntity.ok(
                    new ApiResponse("failed", false)
            );
        }
    }


    @Override
    public HttpEntity<?> fulfillOrder(Session session) {
        String userId = session.getClientReferenceId();
        return ticketService.purchaseTicket(UUID.fromString(userId), session);
    }
}
