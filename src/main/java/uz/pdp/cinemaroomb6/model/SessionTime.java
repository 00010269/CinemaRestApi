package uz.pdp.cinemaroomb6.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.cinemaroomb6.model.template.AbsEntity;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "session_times")
public class SessionTime extends AbsEntity {

    private LocalTime time;
    private double sessionAddFeeInPercent;
}
