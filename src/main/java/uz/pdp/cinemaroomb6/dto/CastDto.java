package uz.pdp.cinemaroomb6.dto;

import lombok.Data;
import uz.pdp.cinemaroomb6.model.Attachment;
import uz.pdp.cinemaroomb6.model.enums.CastType;


import java.util.UUID;


@Data
public class CastDto {

    private UUID id;
    private String fullName;
    private Attachment photo;
    private CastType castType;
}
