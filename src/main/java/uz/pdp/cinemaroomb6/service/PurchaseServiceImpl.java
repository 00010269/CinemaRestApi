package uz.pdp.cinemaroomb6.service;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.cinemaroomb6.dto.PurchaseDto;
import uz.pdp.cinemaroomb6.projection.TicketProjection;
import uz.pdp.cinemaroomb6.repository.TicketRepository;
import uz.pdp.cinemaroomb6.service.interfaces.PurchaseService;
import uz.pdp.cinemaroomb6.service.interfaces.TicketService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Value("sk_test_51KiHKuCuRSqWfGJhbQfJ8Y77cAKGYpkinzGr445Q9Pr8aiFp1D4q6wTRSCREdYRNs5urootDbppkz0HDWWLOdyQS00LD6npejD")
    String stripeApiKey;


    @Autowired
    TicketRepository ticketRepository;


    @Override
    public HttpEntity<?> purchaseTicket(PurchaseDto purchaseDto) {
        List<UUID> ticketIds = purchaseDto.getTicketIds();

        //        List<Ticket> allById = ticketRepository.findAllById(ticketIds);

        return null;
    }


    @SneakyThrows
    @Override
    public HttpEntity<?> createStripeSession(UUID currentUserId) {

        Stripe.apiKey = stripeApiKey;

        List<TicketProjection> userTickets = ticketRepository.getTicketsByUserId(currentUserId);
        ArrayList<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        for (TicketProjection userTicket : userTickets) {
            Double price = userTicket.getPrice();

            String name = userTicket.getMovieTitle();

            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData
                    .builder()
                    .setName(name)
                    .build();


            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData
                    .builder()
                    .setCurrency("USD")
                    .setUnitAmount(price.longValue() * 100)
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
                .setClientReferenceId(currentUserId.toString())
                .build();

        Session session = Session.create(params);

        String url = session.getUrl();

        return ResponseEntity.ok(url);
    }
}
