package id.holigo.services.holigoshipservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.holigo.services.holigoshipservice.repositories.ShipAvailabilityRepository;
import id.holigo.services.holigoshipservice.services.retross.RetrossShipService;
import id.holigo.services.holigoshipservice.web.mappers.ShipAvailabilityMapper;
import id.holigo.services.holigoshipservice.web.model.InquiryDto;
import id.holigo.services.holigoshipservice.web.model.ListAvailabilityDto;
import id.holigo.services.holigoshipservice.web.model.RetrossRequestScheduleDto;
import id.holigo.services.holigoshipservice.web.model.RetrossResponseScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class ShipServiceImpl implements ShipService {

    private RetrossShipService retrossShipService;

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

    @Transactional
    @Override
    public void saveAvailabilities(ListAvailabilityDto listAvailabilityDto) {
        shipAvailabilityRepository.saveAll(listAvailabilityDto.getDepartures().stream().map(shipAvailabilityMapper::shipAvailabilityDtoToShipAvailability).collect(Collectors.toList()));
    }
}
