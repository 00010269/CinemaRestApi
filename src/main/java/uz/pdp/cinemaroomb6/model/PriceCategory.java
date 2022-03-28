package uz.pdp.cinemaroomb6.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.cinemaroomb6.model.template.AbsEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "price_categories")
public class PriceCategory extends AbsEntity {

    @Column(nullable = false,length = 50)
    private String name;

    @Column(nullable = false)
    private Double vipAddFeeInPercent;

    private String color;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priceCategory")
    private List<Seat> seats;

    public PriceCategory(String name, Double vipAddFeeInPercent, String color) {
        this.name = name;
        this.vipAddFeeInPercent = vipAddFeeInPercent;
        this.color = color;
    }
}
