package uz.pdp.cinemaroomb6.projection;

import java.util.UUID;

public interface SeatProjection {

    UUID getId();
    Integer getNumber();
    UUID getPriceCategoryId();
    String getPriceCategoryName();
    Double getPriceCategoryFee();

}
