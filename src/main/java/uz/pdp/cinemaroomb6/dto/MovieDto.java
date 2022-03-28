package uz.pdp.cinemaroomb6.dto;



import lombok.Data;
import uz.pdp.cinemaroomb6.model.Attachment;
import uz.pdp.cinemaroomb6.model.Cast;
import uz.pdp.cinemaroomb6.model.Genre;
import uz.pdp.cinemaroomb6.model.enums.MovieStatus;


import java.util.List;
import java.util.UUID;

@Data
public class MovieDto {

    private UUID id;
    private String title;
    private String description;
    private int durationInMin;
    private double ticketInitPrice;
    private String trailerVideoUrl;
    private Attachment posterImg;
    private String releaseDate;
    private Double budget;
    private UUID distributor_id;
    private Double distributorShareInPercentage;
    private List<Cast> casts;
    private List<Genre> genres;
    private MovieStatus movieStatus;
}
