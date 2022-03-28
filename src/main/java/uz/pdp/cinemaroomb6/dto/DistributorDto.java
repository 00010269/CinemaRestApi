package uz.pdp.cinemaroomb6.dto;

import lombok.Data;
import uz.pdp.cinemaroomb6.model.Attachment;

import java.util.UUID;


@Data
public class DistributorDto {

    private UUID id;
    private String name;
    private String description;
    private UUID logo_id;
}
