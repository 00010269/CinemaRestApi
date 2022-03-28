package uz.pdp.cinemaroomb6.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.cinemaroomb6.model.enums.CastType;
import uz.pdp.cinemaroomb6.model.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "casts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cast extends AbsEntity {

    private String fullName;

    @OneToOne
    private Attachment photo;

    @Enumerated(EnumType.STRING)
    private CastType castType;


}
