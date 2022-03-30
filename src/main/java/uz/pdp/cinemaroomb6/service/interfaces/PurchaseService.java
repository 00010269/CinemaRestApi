package uz.pdp.cinemaroomb6.service.interfaces;

import org.springframework.http.HttpEntity;
import uz.pdp.cinemaroomb6.dto.PurchaseDto;

import java.util.UUID;

public interface PurchaseService {
    HttpEntity<?> purchaseTicket(PurchaseDto purchaseDto);

    HttpEntity<?> createStripeSession(UUID id);
}
