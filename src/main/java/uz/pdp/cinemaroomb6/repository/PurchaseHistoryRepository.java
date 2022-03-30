package uz.pdp.cinemaroomb6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.cinemaroomb6.model.PurchaseHistory;

import java.util.UUID;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, UUID> {

}
