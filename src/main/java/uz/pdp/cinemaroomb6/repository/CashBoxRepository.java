package uz.pdp.cinemaroomb6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.cinemaroomb6.model.CashBox;

import java.util.UUID;

public interface CashBoxRepository extends JpaRepository<CashBox, UUID> {

}
