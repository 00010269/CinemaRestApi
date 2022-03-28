package uz.pdp.cinemaroomb6.dto;

import lombok.Data;

import java.util.UUID;


@Data
public class RowDto {

    private UUID id;
    private Integer number;
    private UUID hall_id;
}
