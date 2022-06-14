package id.holigo.services.holigoshipservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDto implements Serializable {

    InquiryDto inquiry;

    ShipAvailabilityDto trip;
}
