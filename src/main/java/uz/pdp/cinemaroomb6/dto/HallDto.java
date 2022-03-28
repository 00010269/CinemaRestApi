package uz.pdp.cinemaroomb6.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class HallDto {


    private UUID id;
    private String name;
    private Double vipAddFeeInPercent;

}
