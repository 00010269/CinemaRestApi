package uz.pdp.cinemaroomb6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.cinemaroomb6.model.Cast;
import uz.pdp.cinemaroomb6.projection.CastProjection;

import java.util.List;
import java.util.UUID;

public interface CastRepository extends JpaRepository<Cast, UUID> {

    @Query(nativeQuery = true, value = "SELECT " +
            "cast(c.id as varchar) as id," +
            " c.full_name as fullName \n" +
            "FROM casts c\n" +
            "         JOIN movies_casts mc\n" +
            "              on c.id = mc.casts_id\n" +
            "         JOIN movies m on mc.movies_id = m.id\n" +
            "where m.id = :movieId")
    List<CastProjection> findByMovieId(UUID movieId);

}
