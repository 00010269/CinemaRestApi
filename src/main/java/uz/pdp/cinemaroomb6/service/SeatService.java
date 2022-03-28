package uz.pdp.cinemaroomb6.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.cinemaroomb6.model.MovieSession;
import uz.pdp.cinemaroomb6.payload.ApiResponse;
import uz.pdp.cinemaroomb6.projection.MovieSessionByIdProjection;
import uz.pdp.cinemaroomb6.projection.SeatProjection;
import uz.pdp.cinemaroomb6.repository.MovieSessionRepository;
import uz.pdp.cinemaroomb6.repository.SeatRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SeatService {

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    MovieSessionRepository movieSessionRepository;

    public HttpEntity getAvailableSessionSeats(UUID sessionId) {
        MovieSessionByIdProjection availableSessionSeats=
                movieSessionRepository.findBySessionId(sessionId);
       return ResponseEntity.ok(
               new ApiResponse("success",true,availableSessionSeats)
       );
    }

}
