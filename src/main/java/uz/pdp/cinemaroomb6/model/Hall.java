package uz.pdp.cinemaroomb6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.cinemaroomb6.model.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;



@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "halls")
public class Hall extends AbsEntity {

    @Column(nullable = false,length = 50)
    private String name;

    private Double vipAddFeeInPercent;

    public Hall(String name) {
        this.name = name;
    }


}
