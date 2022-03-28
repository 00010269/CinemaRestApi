package uz.pdp.cinemaroomb6.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.cinemaroomb6.model.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "purchase_history")
public class PurchaseHistory extends AbsEntity {

    @ManyToOne
    private Users user;

    @ManyToOne
    private Ticket ticket;

    @ManyToOne
    private PayType payType;
}
