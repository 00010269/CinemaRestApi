package uz.pdp.cinemaroomb6.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MovieSessionDto {

    private UUID id;

    private UUID hall_id;
    private UUID s_date_id;
    private UUID start_time_id;
    private UUID end_time_id;
    private UUID movie_announcement_id;

//    List<ReservedHallDto> reservedHallDtoList;
}
