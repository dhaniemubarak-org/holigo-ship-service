package id.holigo.services.holigoshipservice.web.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.holigo.services.common.model.FareDetailDto;
import id.holigo.services.common.model.FareDto;
import id.holigo.services.holigoshipservice.services.fare.FareService;
import id.holigo.services.holigoshipservice.web.model.RetrossFareDto;
import id.holigo.services.holigoshipservice.web.model.ShipAvailabilityFareDto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class ShipAvailabilityFareMapperDecorator implements ShipAvailabilityFareMapper {

    private FareService fareService;

    private ShipAvailabilityFareMapper shipAvailabilityFareMapper;

    @Autowired
    public void setShipAvailabilityFareMapper(ShipAvailabilityFareMapper shipAvailabilityFareMapper) {
        this.shipAvailabilityFareMapper = shipAvailabilityFareMapper;
    }

    @Autowired
    public void setFareService(FareService fareService) {
        this.fareService = fareService;
    }

    @Override
    public ShipAvailabilityFareDto retrossFareDtoToShipAvailabilityFareDto(RetrossFareDto retrossFareDto, Long userId) {
        ShipAvailabilityFareDto shipAvailabilityFareDto = shipAvailabilityFareMapper.retrossFareDtoToShipAvailabilityFareDto(retrossFareDto, userId);

        shipAvailabilityFareDto.setFareAmount(shipAvailabilityFareDto.getFareAmount().setScale(2, RoundingMode.UP));
        shipAvailabilityFareDto.setPriceAdult(shipAvailabilityFareDto.getPriceAdult().setScale(2, RoundingMode.UP));
        shipAvailabilityFareDto.setPriceChild(shipAvailabilityFareDto.getPriceChild().setScale(2, RoundingMode.UP));
        shipAvailabilityFareDto.setPriceInfant(shipAvailabilityFareDto.getPriceInfant().setScale(2, RoundingMode.UP));
        FareDetailDto fareDetailDto = FareDetailDto.builder()
                .userId(userId)
                .productId(2)
                .nraAmount(BigDecimal.valueOf(5000.00))
                .ntaAmount(BigDecimal.valueOf(0.00)).build();
        FareDto fareDto;
        try {
            fareDto = fareService.getFareDetail(fareDetailDto);
        } catch (JMSException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        assert fareDto != null;
        shipAvailabilityFareDto.setHpAmount(fareDto.getHpAmount());
        return shipAvailabilityFareDto;
    }
}
