package uz.pdp.cinemaroomb6.dto;

import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.UUID;

public class ReservedHallDto {

    private UUID hallId;
    private String startDate;
    private String endDate;
    private List<String>sessionTimeDtoList;
}
