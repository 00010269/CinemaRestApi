package uz.pdp.cinemaroomb6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.cinemaroomb6.model.MovieAnnouncement;
import uz.pdp.cinemaroomb6.payload.ApiResponse;
import uz.pdp.cinemaroomb6.projection.MovieAnnouncementProjection;
import uz.pdp.cinemaroomb6.repository.MovieAnnouncementRepository;
import uz.pdp.cinemaroomb6.repository.MovieRepository;
import uz.pdp.cinemaroomb6.service.interfaces.MovieAnnouncementsService;

import java.util.UUID;


@Service
public class MovieAnnouncementsServiceImpl implements MovieAnnouncementsService {

    @Autowired
    MovieAnnouncementRepository movieAnnouncementRepository;

    @Autowired
    MovieRepository movieRepository;



    @Override
    public HttpEntity getAllMovieAnnouncements(int size, int page) {
        Pageable pageable= PageRequest.of(page,size);
        Page<MovieAnnouncementProjection> movieSchedulesPage = movieAnnouncementRepository.findAllMovieSchedulesPage(pageable);
        return ResponseEntity.ok(new ApiResponse("success",true,movieSchedulesPage));
    }


    @Override
    public HttpEntity saveMovieAnnouncement(UUID movieAnnouncementId, UUID movieId, String active) {
        MovieAnnouncement movieAnnouncement=new MovieAnnouncement();
        if(movieAnnouncementId!=null){
            movieAnnouncement.setId(movieAnnouncementId);
        }
        try {
            movieAnnouncement.setMovie(movieRepository.getById(movieId));
            movieAnnouncement.setActive(Boolean.parseBoolean(active));
            movieAnnouncementRepository.save(movieAnnouncement);
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse("failed",false));
        }
        return ResponseEntity.ok(new ApiResponse("success",true));
    }

    @Override
    public HttpEntity deleteMovieAnnouncement(UUID movieAnnouncementId) {
        try {
            movieAnnouncementRepository.deleteById(movieAnnouncementId);
            return ResponseEntity.ok(new ApiResponse("success",true));
        }catch (Exception e){
            return ResponseEntity.ok(new ApiResponse("failed",false));
        }
    }
}
