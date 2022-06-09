package id.holigo.services.holigoshipservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipAvailabilityDto implements Serializable {

    private UUID id;

    private String shipName;

    private String shipNumber;

    private String originHarborId;

    private String destinationHarborId;

    private Date departureDate;

    private Time departureTime;

    private Date arrivalDate;

    private Time arrivalTime;

    private String imageUrl;

    private String shipClass;

    private String shipSubClass;

    private ShipAvailabilityFareDto fare;
}
