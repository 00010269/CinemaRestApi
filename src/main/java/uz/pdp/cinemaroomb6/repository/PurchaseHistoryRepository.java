package uz.pdp.cinemaroomb6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.cinemaroomb6.model.PurchaseHistory;

import java.util.UUID;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, UUID> {


    @Query(nativeQuery = true,
    value = "select ph.payment_intent from purchase_history ph " +
            " join purchase_hisories_tickets pht on ph.id = pht.purchase_history_id " +
            " where pht.ticket_id =:ticketId")
    String getPaymentIntent(UUID ticketId);


//    @Query(nativeQuery = true,
//    value = "select ph.* from purchase_history ph " +
//            " join pur")
//    Optional<PurchaseHistory> findByTicketListContains(List<Ticket> ticketList);

}
