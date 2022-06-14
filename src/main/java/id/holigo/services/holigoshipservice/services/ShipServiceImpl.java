package id.holigo.services.holigoshipservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.common.model.FareDto;
import id.holigo.services.common.model.TripType;
import id.holigo.services.holigoshipservice.components.Fare;
import id.holigo.services.holigoshipservice.config.FeeConfig;
import id.holigo.services.holigoshipservice.domain.ShipAvailability;
import id.holigo.services.holigoshipservice.domain.ShipFinalFare;
import id.holigo.services.holigoshipservice.domain.ShipFinalFareTrip;
import id.holigo.services.holigoshipservice.repositories.ShipAvailabilityRepository;
import id.holigo.services.holigoshipservice.services.retross.RetrossShipService;
import id.holigo.services.holigoshipservice.web.exceptions.FinalFareBadRequestException;
import id.holigo.services.holigoshipservice.web.mappers.ShipAvailabilityMapper;
import id.holigo.services.holigoshipservice.web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShipServiceImpl implements ShipService {

    private ObjectMapper objectMapper;

    private RetrossShipService retrossShipService;

    private Fare fare;

    private ShipAvailabilityMapper shipAvailabilityMapper;

    private ShipAvailabilityRepository shipAvailabilityRepository;

    @Autowired
    public void setRetrossShipService(RetrossShipService retrossShipService) {
        this.retrossShipService = retrossShipService;
    }

    @Autowired
    public void setShipAvailabilityMapper(ShipAvailabilityMapper shipAvailabilityMapper) {
        this.shipAvailabilityMapper = shipAvailabilityMapper;
    }

    @Autowired
    public void setShipAvailabilityRepository(ShipAvailabilityRepository shipAvailabilityRepository) {
        this.shipAvailabilityRepository = shipAvailabilityRepository;
    }

    @Autowired
    public void setFare(Fare fare) {
        this.fare = fare;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ListAvailabilityDto getSchedule(InquiryDto inquiryDto) throws JsonProcessingException {

        RetrossRequestScheduleDto retrossRequestScheduleDto = RetrossRequestScheduleDto.builder()
                .org(inquiryDto.getOriginHarborId())
                .des(inquiryDto.getDestinationHarborId())
                .tgl_dep(inquiryDto.getDepartureDate().toString())
                .trip(inquiryDto.getTripType().toString())
                .adt_m(inquiryDto.getAdultMaleAmount())
                .adt_f(inquiryDto.getAdultFemaleAmount())
                .inf(inquiryDto.getInfantAmount())
                .build();
        RetrossResponseScheduleDto retrossResponseScheduleDto = retrossShipService.getSchedule(retrossRequestScheduleDto);
        if (retrossResponseScheduleDto.getError_code().equals("001")) {
            return null;
        }
        ListAvailabilityDto listAvailabilityDto = shipAvailabilityMapper.retrossResponseScheduleDtoToListAvailabilityDto(retrossResponseScheduleDto, inquiryDto.getUserId());
        listAvailabilityDto.setInquiry(inquiryDto);
        saveAvailabilities(listAvailabilityDto);
        return listAvailabilityDto;
    }

    @Override
    public ShipFinalFare createFinalFare(RequestFinalFareDto requestFinalFareDto, Long userId) {
        FareDto fareDto = fare.getFare(userId);
        ShipFinalFare shipFinalFare = new ShipFinalFare();
        shipFinalFare.setId(UUID.randomUUID());
        shipFinalFare.setUserId(userId);
        shipFinalFare.setIsBookable(true);
        shipFinalFare.setNtaAmount(FeeConfig.ADMIN_AMOUNT.subtract(FeeConfig.NRA_AMOUNT));
        shipFinalFare.setNraAmount(FeeConfig.NRA_AMOUNT);
        shipFinalFare.setCpAmount(fareDto.getCpAmount());
        shipFinalFare.setMpAmount(fareDto.getMpAmount());
        shipFinalFare.setIpAmount(fareDto.getIpAmount());
        shipFinalFare.setHpAmount(fareDto.getHpAmount());
        shipFinalFare.setHvAmount(fareDto.getHvAmount());
        shipFinalFare.setPrAmount(fareDto.getPrAmount());
        shipFinalFare.setIpcAmount(fareDto.getIpcAmount());
        shipFinalFare.setHpcAmount(fareDto.getHpcAmount());
        shipFinalFare.setPrcAmount(fareDto.getPrcAmount());
        shipFinalFare.setLossAmount(fareDto.getLossAmount());
        shipFinalFare.setAdminAmount(shipFinalFare.getNtaAmount()
                .add(fareDto.getCpAmount())
                .add(fareDto.getMpAmount())
                .add(fareDto.getIpAmount())
                .add(fareDto.getHpAmount())
                .add(fareDto.getHvAmount())
                .add(fareDto.getPrAmount())
                .add(fareDto.getIpcAmount())
                .add(fareDto.getHpcAmount())
                .add(fareDto.getPrcAmount()));
        shipFinalFare.setFareAmount(shipFinalFare.getAdminAmount());
        for (TripDto tripDto : requestFinalFareDto.getTrips()) {
            Optional<ShipAvailability> fetchShipAvailability = shipAvailabilityRepository.findById(tripDto.getTrip().getId());
            if (fetchShipAvailability.isEmpty()) {
                throw new FinalFareBadRequestException();
            }
            ShipAvailability shipAvailability = fetchShipAvailability.get();
            ShipFinalFareTrip shipFinalFareTrip = shipAvailabilityMapper.shipAvailabilityToShipFinalFareTrip(shipAvailability);
            ShipAvailabilityFareDto shipAvailabilityFareDto;
            try {
                shipAvailabilityFareDto = objectMapper.readValue(shipAvailability.getFare(), ShipAvailabilityFareDto.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            shipFinalFareTrip.setSupplierId(shipAvailabilityFareDto.getSelectedId());
            shipFinalFareTrip.setFareAmount(shipAvailabilityFareDto.getFareAmount());
            shipFinalFareTrip.setAdminAmount(shipFinalFare.getAdminAmount());
            shipFinalFareTrip.setNraAmount(shipFinalFare.getNraAmount());
            shipFinalFareTrip.setNtaAmount(shipFinalFare.getFareAmount()
                    .add(shipFinalFareTrip.getAdminAmount().subtract(shipFinalFare.getNraAmount())));
            shipFinalFareTrip.setCpAmount(shipFinalFare.getCpAmount());
            shipFinalFareTrip.setMpAmount(shipFinalFare.getMpAmount());
            shipFinalFareTrip.setIpAmount(shipFinalFare.getIpAmount());
            shipFinalFareTrip.setHpcAmount(shipFinalFare.getHpAmount());
            shipFinalFareTrip.setHvAmount(shipFinalFare.getHvAmount());
            shipFinalFareTrip.setPrAmount(shipFinalFare.getPrAmount());
            shipFinalFareTrip.setIpcAmount(shipFinalFare.getIpcAmount());
            shipFinalFareTrip.setHpcAmount(shipFinalFare.getHpcAmount());
            shipFinalFareTrip.setPrcAmount(shipFinalFare.getPrcAmount());
            shipFinalFareTrip.setLossAmount(shipFinalFare.getLossAmount());
            shipFinalFare.setNtaAmount(shipFinalFare.getNtaAmount().add(shipFinalFareTrip.getFareAmount()));
            shipFinalFare.setFareAmount(shipFinalFare.getFareAmount().add(shipFinalFareTrip.getFareAmount()));
            shipFinalFare.addToTrips(shipFinalFareTrip);
        }
        return shipFinalFare;
    }

    @Transactional
    @Override
    public void saveAvailabilities(ListAvailabilityDto listAvailabilityDto) {
        shipAvailabilityRepository.saveAll(listAvailabilityDto.getDepartures().stream().map(shipAvailabilityMapper::shipAvailabilityDtoToShipAvailability).collect(Collectors.toList()));
    }
}
