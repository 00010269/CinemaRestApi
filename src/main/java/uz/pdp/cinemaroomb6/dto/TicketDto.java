package uz.pdp.cinemaroomb6.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketDto {

    UUID ticketId;

    UUID sessionId;

    UUID seatId;

    UUID userId;

    private double price;

    private String movieTitle;

}
