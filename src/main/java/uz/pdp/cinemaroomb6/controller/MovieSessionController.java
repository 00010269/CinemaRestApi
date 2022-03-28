package uz.pdp.cinemaroomb6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.cinemaroomb6.service.MovieSessionServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("/api/movie-session")
public class MovieSessionController {

    @Autowired
    MovieSessionServiceImpl movieSessionService;


    @GetMapping
    public HttpEntity<?> getMovieSessionsByHallId(
            @RequestParam("hall-id")UUID hallId
            ){
        return movieSessionService.getMovieSessionsByHallId(hallId);
    }
}
