package id.holigo.services.holigoshipservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShipAvailabilityFareDto {

    private String shipClass;

    private String shipSubClass;

    private Integer seatAvailableMale;

    private Integer seatAvailableFemale;

    private BigDecimal fareAmount;

    private BigDecimal hpAmount;

    private BigDecimal priceAdult;

    private BigDecimal priceChild;

    private BigDecimal priceInfant;

    private String selectedId;

}
