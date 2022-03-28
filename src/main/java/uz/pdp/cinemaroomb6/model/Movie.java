package uz.pdp.cinemaroomb6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.cinemaroomb6.model.enums.MovieStatus;
import uz.pdp.cinemaroomb6.model.template.AbsEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "movies")
public class Movie extends AbsEntity {

    @Column(nullable = false,length = 50)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    private int durationInMin;

    private double ticketInitPrice;

    @Column(nullable = false)
    private String trailerVideoUrl;

    @OneToOne
    private Attachment posterImg;

    @Column(nullable = false)
    private LocalDate releaseDate;

    private Double budget;

    @ManyToOne
    private Distributor distributor;

    @Column(nullable = false)
    private Double distributorShareInPercentage;

    @ManyToMany
    private List<Cast> casts;

    @ManyToMany
    private List<Genre> genres;

    @Enumerated(EnumType.STRING)
    private MovieStatus movieStatus;

}
