package uz.pdp.cinemaroomb6.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface MovieSessionByIdProjection {

    UUID getId();
//    UUID getSessionDateId();
//    LocalDate getSessionDate();
//    UUID getStartTimeId();
//    LocalTime getStartTime();
//    UUID getEndTimeId();
//    LocalTime getEndTime();
    @Value("#{@seatRepository.getAvailableSessionSeats(target.id)}")
    List<SeatProjection> getAvailableSessionSeats();

}
