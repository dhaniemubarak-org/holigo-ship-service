package id.holigo.services.holigoshipservice.web.mappers;

import id.holigo.services.holigoshipservice.web.model.RetrossFareDto;
import id.holigo.services.holigoshipservice.web.model.ShipAvailabilityFareDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@DecoratedWith(ShipAvailabilityFareMapperDecorator.class)
@Mapper
public interface ShipAvailabilityFareMapper {

    @Mapping(target = "hpAmount", ignore = true)
    @Mapping(target = "seatAvailableMale", source = "retrossFareDto.seatAvbM")
    @Mapping(target = "seatAvailableFemale", source = "retrossFareDto.seatAvbF")
    ShipAvailabilityFareDto retrossFareDtoToShipAvailabilityFareDto(RetrossFareDto retrossFareDto, Long userId);

}
