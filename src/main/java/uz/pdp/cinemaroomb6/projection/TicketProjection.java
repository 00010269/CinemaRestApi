package uz.pdp.cinemaroomb6.projection;

import java.util.UUID;

public interface TicketProjection {

    UUID getTicketId();

    Double getPrice();

    String getMovieTitle();

}
