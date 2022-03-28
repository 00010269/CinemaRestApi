package uz.pdp.cinemaroomb6.projection;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public interface MovieProjection {

    UUID getId();
    String getTitle();
    UUID getPosterImgId();
    LocalDate getReleaseDate();
}
