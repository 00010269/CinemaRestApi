package uz.pdp.cinemaroomb6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.cinemaroomb6.model.Seat;
import uz.pdp.cinemaroomb6.projection.SeatProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeatRepository extends JpaRepository<Seat, UUID> {
    Seat findByNumber(Integer number);



    @Query(nativeQuery = true,
    value = "select cast(s.id as varchar) as id, s.number as number," +
            "cast(s.price_category_id as varchar) as priceCategoryId," +
            "pc.name as priceCategoryName," +
            "pc.vip_add_fee_in_percent as priceCategoryFee from seats s\n" +
            "join rows r on s.row_id = r.id\n" +
            "join halls h on h.id=r.hall_id\n" +
            "join movie_sessions ms on h.id = ms.hall_id\n" +
            "join price_categories pc on s.price_category_id = pc.id " +
            "where ms.id=:sessionId and s.id not in(select t.seat_id from tickets t where t.status='NEW'\n" +
            "    or t.status='PURCHASED')")

    List<SeatProjection> getAvailableSessionSeats(UUID sessionId);
}
