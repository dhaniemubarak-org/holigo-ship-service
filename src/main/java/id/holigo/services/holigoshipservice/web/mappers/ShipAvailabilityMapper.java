package id.holigo.services.holigoshipservice.web.mappers;

import id.holigo.services.holigoshipservice.domain.ShipAvailability;
import id.holigo.services.holigoshipservice.domain.ShipFinalFareTrip;
import id.holigo.services.holigoshipservice.web.model.*;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ShipAvailabilityFareMapper.class})
@DecoratedWith(ShipAvailabilityMapperDecorator.class)
public interface ShipAvailabilityMapper {

    @Mapping(target = "originHarborId", source = "retrossDepartureDto.sta")
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fare", ignore = true)
    @Mapping(target = "destinationHarborId", source = "retrossDepartureDto.std")
    @Mapping(target = "departureTime", ignore = true)
    @Mapping(target = "departureDate", ignore = true)
    @Mapping(target = "arrivalTime", ignore = true)
    @Mapping(target = "arrivalDate", ignore = true)
    ShipAvailabilityDto retrossDepartureDtoToTrainAvailabilityDto(RetrossDepartureDto retrossDepartureDto, RetrossFareDto retrossFareDto, Long userId);

    @Mapping(target = "inquiry", ignore = true)
    @Mapping(target = "departures", ignore = true)
    ListAvailabilityDto retrossResponseScheduleDtoToListAvailabilityDto(RetrossResponseScheduleDto retrossResponseScheduleDto, Long userId);


    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "origin", ignore = true)
    @Mapping(target = "destination", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "fare", ignore = true)
    ShipAvailability shipAvailabilityDtoToShipAvailability(ShipAvailabilityDto shipAvailabilityDto);

    @Mapping(target = "supplierId", ignore = true)
    @Mapping(target = "prcAmount", ignore = true)
    @Mapping(target = "prAmount", ignore = true)
    @Mapping(target = "ntaAmount", ignore = true)
    @Mapping(target = "nraAmount", ignore = true)
    @Mapping(target = "mpAmount", ignore = true)
    @Mapping(target = "lossAmount", ignore = true)
    @Mapping(target = "ipcAmount", ignore = true)
    @Mapping(target = "ipAmount", ignore = true)
    @Mapping(target = "hvAmount", ignore = true)
    @Mapping(target = "hpcAmount", ignore = true)
    @Mapping(target = "hpAmount", ignore = true)
    @Mapping(target = "finalFare", ignore = true)
    @Mapping(target = "fareAmount", ignore = true)
    @Mapping(target = "cpAmount", ignore = true)
    @Mapping(target = "adminAmount", ignore = true)
    ShipFinalFareTrip shipAvailabilityToShipFinalFareTrip(ShipAvailability shipAvailability);
}
