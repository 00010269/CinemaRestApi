package uz.pdp.cinemaroomb6.service.interfaces;

import org.springframework.http.HttpEntity;

import java.util.UUID;


public interface MovieAnnouncementsService {

    HttpEntity getAllMovieAnnouncements(int size, int page);

    HttpEntity saveMovieAnnouncement(UUID movieAnnouncementId, UUID movieId, String active);

    HttpEntity deleteMovieAnnouncement(UUID movieAnnouncementId);
}
