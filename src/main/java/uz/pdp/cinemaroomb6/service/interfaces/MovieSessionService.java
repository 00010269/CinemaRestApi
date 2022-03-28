package uz.pdp.cinemaroomb6.service.interfaces;

import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface MovieSessionService {


    HttpEntity getMovieSessionsByHallId(UUID hallId);


}
