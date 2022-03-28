package uz.pdp.cinemaroomb6.projection;

import java.time.LocalDate;
import java.time.LocalTime;

public interface MovieSessionQRInfoProjection {

    String getSessionHallName();
    LocalDate getSessionDate();
    LocalTime getSessionStartTime();
    LocalTime getSessionEndTime();
}
