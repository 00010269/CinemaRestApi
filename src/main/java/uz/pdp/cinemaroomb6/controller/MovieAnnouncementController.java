package uz.pdp.cinemaroomb6.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cinemaroomb6.service.MovieAnnouncementsServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("/api/movie-announcement")
public class MovieAnnouncementController {

    @Autowired
    MovieAnnouncementsServiceImpl movieAnnouncementsService;

    @GetMapping
    public HttpEntity<?> getMovieAnnouncements(@RequestParam(value = "size",required = false,defaultValue = "2")Integer size,
                                        @RequestParam(value = "page",required = false,defaultValue = "1")Integer page){
        return movieAnnouncementsService.getAllMovieAnnouncements(size,page-1);
    }

    @PostMapping
    public HttpEntity<?> saveMovieAnnouncement(@RequestParam(value = "movie-announcement-id",required = false)UUID movieAnnouncementId,
                                               @RequestParam(value = "movie-id")UUID movieId,
                                               @RequestParam(value = "active")String active){
        return movieAnnouncementsService.saveMovieAnnouncement(movieAnnouncementId, movieId, active);
    }
    @DeleteMapping
    public HttpEntity<?>deleteMovieAnnouncement(@RequestParam(value = "movie-announcement-id")UUID movieAnnouncementId){
        return movieAnnouncementsService.deleteMovieAnnouncement(movieAnnouncementId);
    }
}
