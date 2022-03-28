package uz.pdp.cinemaroomb6.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.cinemaroomb6.model.Movie;
import uz.pdp.cinemaroomb6.projection.MovieProjection;


import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
    
    @Query(nativeQuery = true,value = "select cast(m.id as varchar) as id,m.title,m.release_date as releaseDate,cast(m.poster_img_id as varchar) as posterImgId " +
            " from movies m;")
    Page<MovieProjection> findAllMoviesPage(Pageable pageable); // TODO: 3/16/2022
}
