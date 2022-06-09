package id.holigo.services.holigoshipservice.web.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.holigoshipservice.domain.ShipAvailability;
import id.holigo.services.holigoshipservice.web.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class ShipAvailabilityMapperDecorator implements ShipAvailabilityMapper {

    private ObjectMapper objectMapper;
    private ShipAvailabilityMapper shipAvailabilityMapper;

    private ShipAvailabilityFareMapper shipAvailabilityFareMapper;

    @Autowired
    public void setShipAvailabilityMapper(ShipAvailabilityMapper shipAvailabilityMapper) {
        this.shipAvailabilityMapper = shipAvailabilityMapper;
    }

    @Autowired
    public void setShipAvailabilityFareMapper(ShipAvailabilityFareMapper shipAvailabilityFareMapper) {
        this.shipAvailabilityFareMapper = shipAvailabilityFareMapper;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ShipAvailabilityDto retrossDepartureDtoToTrainAvailabilityDto(RetrossDepartureDto retrossDepartureDto, RetrossFareDto retrossFareDto, Long userId) {
        ShipAvailabilityDto shipAvailabilityDto = shipAvailabilityMapper.retrossDepartureDtoToTrainAvailabilityDto(retrossDepartureDto, retrossFareDto, userId);
        shipAvailabilityDto.setId(UUID.randomUUID());
        shipAvailabilityDto.setImageUrl("https://ik.imagekit.io/holigo/transportasi/logo-main-pelni_30zHBjuto.png");
        shipAvailabilityDto.setDepartureDate(Date.valueOf(LocalDate.parse(retrossDepartureDto.getEtd().substring(0, 10))));
        shipAvailabilityDto.setDepartureTime(Time.valueOf(LocalTime.parse(retrossDepartureDto.getEtd().substring(11, 16))));
        shipAvailabilityDto.setArrivalDate(Date.valueOf(LocalDate.parse(retrossDepartureDto.getEta().substring(0, 10))));
        shipAvailabilityDto.setArrivalTime(Time.valueOf(LocalTime.parse(retrossDepartureDto.getEta().substring(11, 16))));
        shipAvailabilityDto.setFare(shipAvailabilityFareMapper.retrossFareDtoToShipAvailabilityFareDto(retrossFareDto, userId));
        return shipAvailabilityDto;
    }

    @Override
    public ListAvailabilityDto retrossResponseScheduleDtoToListAvailabilityDto(RetrossResponseScheduleDto retrossResponseScheduleDto, Long userId) {
        List<ShipAvailabilityDto> shipAvailabilityDtoList = new ArrayList<>();

        for (int i = 0; i < retrossResponseScheduleDto.getSchedule().getDepartures().size(); i++) {
            log.info("For loop -> {}", i);
            RetrossDepartureDto retrossDepartureDto = retrossResponseScheduleDto.getSchedule().getDepartures().get(i);
            retrossDepartureDto.getFares().forEach(fare -> {
                shipAvailabilityDtoList.add(retrossDepartureDtoToTrainAvailabilityDto(retrossDepartureDto, fare, userId));
            });
        }

        ListAvailabilityDto listAvailabilityDto = new ListAvailabilityDto();
        listAvailabilityDto.setDepartures(shipAvailabilityDtoList);
        return listAvailabilityDto;
    }

    @Override
    public ShipAvailability shipAvailabilityDtoToShipAvailability(ShipAvailabilityDto shipAvailabilityDto) {
        ShipAvailability shipAvailability = shipAvailabilityMapper.shipAvailabilityDtoToShipAvailability(shipAvailabilityDto);
        try {
            shipAvailability.setFare(objectMapper.writeValueAsString(shipAvailabilityDto.getFare()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return shipAvailability;
    }
}
