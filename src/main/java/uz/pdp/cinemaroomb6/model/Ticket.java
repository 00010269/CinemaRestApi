package uz.pdp.cinemaroomb6.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.cinemaroomb6.model.enums.TicketStatus;
import uz.pdp.cinemaroomb6.model.template.AbsEntity;

import javax.persistence.*;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "tickets")
public class Ticket extends AbsEntity {

    @ManyToOne
    private MovieSession movieSession;

    @OneToOne
    private Seat seat;

    private String qr_code;

    private Double price;

    @ManyToOne
    private Users user;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

}
