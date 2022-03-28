package uz.pdp.cinemaroomb6.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import uz.pdp.cinemaroomb6.model.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;



@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "billing_infos")
public class BillingInfo extends AbsEntity {

    @ManyToOne
    private Users user;
    private String card_holder_name;

    @Check(constraints = "length(card_number)==16")
    private String card_number;
    private String expiration_month;
    private String expiration_year;
    private String cvc_code;


}
