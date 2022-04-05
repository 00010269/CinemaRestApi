package uz.pdp.cinemaroomb6.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.cinemaroomb6.model.template.AbsEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "purchase_history")
public class PurchaseHistory extends AbsEntity {

    @ManyToOne
    private Users user;

    @ManyToMany
    @JoinTable(
            name = "purchase_hisories_tickets",
            joinColumns = @JoinColumn(name = "purchase_history_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id")
    )
    private List<Ticket> ticketList;

    @ManyToOne
    private PayType payType;

    private Double amount;

    @NotNull
    @Column(nullable = false)
    private boolean isRefunded = false;

    private String paymentIntent;

    public PurchaseHistory(List<Ticket> ticketList, PayType payType, Double amount, boolean isRefunded, String paymentIntent) {
        this.ticketList = ticketList;
        this.payType = payType;
        this.amount = amount;
        this.isRefunded = isRefunded;
        this.paymentIntent = paymentIntent;
    }
}
