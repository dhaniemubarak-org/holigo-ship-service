package id.holigo.services.holigoshipservice.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetrossFareDto implements Serializable {

    @JsonProperty(value = "Class")
    private String shipClass;

    @JsonProperty(value = "SubClass")
    private String shipSubClass;

    @JsonProperty(value = "SeatAvb_m")
    private Integer seatAvbM;

    @JsonProperty(value = "SeatAvb_f")
    private Integer seatAvbF;

    @JsonProperty(value = "TotalFare")
    private BigDecimal fareAmount;

    @JsonProperty(value = "priceAdt")
    private BigDecimal priceAdult;

    @JsonProperty(value = "priceChd")
    private BigDecimal priceChild;

    @JsonProperty(value = "priceInf")
    private BigDecimal priceInfant;

    @JsonProperty(value = "selectedIDdep")
    private String selectedId;

}
