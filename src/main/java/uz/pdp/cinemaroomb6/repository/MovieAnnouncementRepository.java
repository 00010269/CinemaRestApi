package uz.pdp.cinemaroomb6.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.cinemaroomb6.model.MovieAnnouncement;
import uz.pdp.cinemaroomb6.projection.MovieAnnouncementProjection;

import java.util.UUID;

@Repository
public interface MovieAnnouncementRepository extends JpaRepository<MovieAnnouncement, UUID> {


    @Query(nativeQuery = true,
    value = "select cast(msch.id as varchar) as id,\n" +
            "       cast(m.poster_img_id as varchar) as moviePosterImgId,\n" +
            "       cast(m.id as varchar)as movieId,\n" +
            "       m.title as movieTitle\n" +
            "from movie_announcements msch\n" +
            "join movies m on m.id = msch.movie_id")
    Page<MovieAnnouncementProjection> findAllMovieSchedulesPage(Pageable pageable);



}
