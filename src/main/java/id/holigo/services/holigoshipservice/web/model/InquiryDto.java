package id.holigo.services.holigoshipservice.web.model;

import id.holigo.services.common.model.TripType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDto implements Serializable {

    private UUID id;

    private Long userId;

    private String originHarborId;

    private String destinationHarborId;

    private Date departureDate;

    private TripType tripType;

    private Integer adultMaleAmount;

    private Integer adultFemaleAmount;

    private Integer childAmount;

    private Integer infantAmount;

    private Boolean isFamily;


}
