package uz.pdp.cinemaroomb6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.cinemaroomb6.model.Hall;
import uz.pdp.cinemaroomb6.projection.HallProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface HallRepository extends JpaRepository<Hall,UUID> {
    Hall findByName(String name);

    @Query(nativeQuery = true,
    value = "select cast(h.id as varchar) as id,h.name from halls h join movie_sessions ms on h.id = ms.hall_id " +
            "and ms.movie_announcement_id=:afishaId")
    List<HallProjection> getAfishaHalls(UUID afishaId);
}
