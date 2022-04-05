package uz.pdp.cinemaroomb6.projection;

import java.time.LocalDate;
import java.util.UUID;

public interface PdfTicketProjection {
    UUID getTicketId();
    String getMovieTitle();
    String getHallName();
    Integer getRowNumber();
    Integer getSeatNumber();
    LocalDate getSessionDate();
}
