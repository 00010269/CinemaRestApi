package uz.pdp.cinemaroomb6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.cinemaroomb6.dto.DistributorDto;
import uz.pdp.cinemaroomb6.model.Distributor;
import uz.pdp.cinemaroomb6.projection.DistributorProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface DistributorRepository extends JpaRepository<Distributor, UUID> {

    @Query(nativeQuery = true,value = "select cast(d.id as varchar) as id,d.name from distributors d " +
            "join movies m on d.id = m.distributor_id where m.id=:movieId")
    List<DistributorProjection> findByMovieId(UUID movieId);
}
