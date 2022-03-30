package uz.pdp.cinemaroomb6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.cinemaroomb6.model.PayType;

import java.util.UUID;

public interface PayTypeRepository extends JpaRepository<PayType, UUID> {

    PayType findByName(String payType);

}
