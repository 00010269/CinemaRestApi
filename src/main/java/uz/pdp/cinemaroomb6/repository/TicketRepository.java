package uz.pdp.cinemaroomb6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.cinemaroomb6.model.Ticket;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    @Query(nativeQuery = true,
    value = "select (m.ticket_init_price + (ticket_init_price*(select pc.vip_add_fee_in_percent from price_categories pc " +
            " join seats s on pc.id = s.price_category_id " +
            " where s.id=:seatId)/100) + ticket_init_price*(select st.session_add_fee_in_percent from session_times st " +
            " join movie_sessions ms2 on st.id = ms2.start_time_id " +
            " where ms2.id=:sessionId)/100) as finalTicketPrice from movies m " +
            " join movie_announcements ma on m.id = ma.movie_id " +
            " join movie_sessions ms on ma.id = ms.movie_announcement_id " +
            " where ms.id=:sessionId")

    Double getTicketPrice(UUID seatId, UUID sessionId);

}
