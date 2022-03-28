package uz.pdp.cinemaroomb6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sun.plugin.util.UIUtil;
import uz.pdp.cinemaroomb6.model.MovieSession;
import uz.pdp.cinemaroomb6.payload.ApiResponse;
import uz.pdp.cinemaroomb6.projection.MovieSessionProjection;
import uz.pdp.cinemaroomb6.repository.MovieSessionRepository;
import uz.pdp.cinemaroomb6.service.interfaces.MovieSessionService;

import java.util.List;
import java.util.UUID;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {

    @Autowired
    MovieSessionRepository movieSessionRepository;

    @Override
    public HttpEntity getMovieSessionsByHallId(UUID hallId) {
        return ResponseEntity.ok(
                new ApiResponse("success",true,movieSessionRepository.findByHallId(hallId))
        );
    }


}
