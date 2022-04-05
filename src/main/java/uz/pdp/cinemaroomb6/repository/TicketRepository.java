package uz.pdp.cinemaroomb6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.cinemaroomb6.model.Ticket;
import uz.pdp.cinemaroomb6.model.enums.TicketStatus;
import uz.pdp.cinemaroomb6.projection.PdfTicketProjection;
import uz.pdp.cinemaroomb6.projection.TicketProjection;

import java.util.List;
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



    @Query(nativeQuery = true,
    value = "select cast(t.id as varchar) as ticketId, " +
            " t.price, " +
            " m.title as movieTitle " +
            " from tickets t " +
            " join users u on t.user_id = u.id " +
            " join movie_sessions ms on ms.id = t.movie_session_id " +
            " join movie_announcements ma on ma.id = ms.movie_announcement_id " +
            " join movies m on ma.movie_id = m.id " +
            " where t.user_id = :userId")

    List<TicketProjection> getTicketsByUserId(UUID userId);



    @Query(nativeQuery = true,
    value = "select * from tickets " +
            " join users u on u.id = tickets.user_id" +
            " where user_id =:userId and status = 'NEW'")

    List<Ticket> findAllByUserId(UUID userId);



    List<Ticket> findByUserId(UUID userId);

    List<Ticket> findAllByUserIdAndStatus(UUID userId, TicketStatus status);



    @Query(nativeQuery = true,
    value = "select sum(price) from tickets " +
            " where user_id = :userId and status = 'NEW'")
    Double getTotalAmountOfTickets(UUID userId);




    @Query(nativeQuery = true, value = "select cast(t.id as varchar) as ticketId, " +
            " m.title as movieTitle, " +
            " h.name as hallName, " +
            " r.number as rowNumber, " +
            " s.number as seatNumber, " +
            " sd.date as sessionDate " +
            " from tickets t " +
            " join seats s on s.id = t.seat_id " +
            " join movie_sessions ms on ms.id = t.movie_session_id " +
            " join halls h on h.id = ms.hall_id " +
            " join rows r on s.row_id = r.id " +
            " join session_dates sd on ms.session_date_id = sd.id " +
            " join movie_announcements ma on ma.id = ms.movie_announcement_id " +
            " join movies m on ma.movie_id = m.id " +
            " where t.id=:id")
    PdfTicketProjection getPdfTicket(UUID id);


}
