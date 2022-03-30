package uz.pdp.cinemaroomb6.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketDto {

    UUID sessionId;

    UUID seatId;


}
