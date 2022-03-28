package uz.pdp.cinemaroomb6.projection;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface MovieSessionProjection {

    UUID getId();
    UUID getMovieAnnouncementId();
    UUID getSessionDateId();
    LocalDate getSessionDate();
    UUID getStartTimeId();
    LocalTime getSessionStartTime();
    UUID getEndTimeId();
    LocalTime getSessionEndTime();

}
