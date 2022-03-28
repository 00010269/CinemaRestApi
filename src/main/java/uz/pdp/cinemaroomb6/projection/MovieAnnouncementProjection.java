package uz.pdp.cinemaroomb6.projection;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import uz.pdp.cinemaroomb6.model.MovieAnnouncement;

import java.util.List;
import java.util.UUID;

@Projection(types = MovieAnnouncement.class)
public interface MovieAnnouncementProjection {

    UUID getId();
    UUID getMoviePosterImgId();
    UUID getMovieId();
    String getMovieTitle();
    @Value("#{@hallRepository.getAfishaHalls(target.id)}")
    List<HallProjection>getAfishaHalls();
}
