package id.holigo.services.holigoshipservice.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.holigo.services.holigoshipservice.domain.Inquiry;
import id.holigo.services.holigoshipservice.repositories.InquiryRepository;
import id.holigo.services.holigoshipservice.services.ShipService;
import id.holigo.services.holigoshipservice.web.exceptions.AvailabilitiesException;
import id.holigo.services.holigoshipservice.web.mappers.InquiryMapper;
import id.holigo.services.holigoshipservice.web.model.InquiryDto;
import id.holigo.services.holigoshipservice.web.model.ListAvailabilityDto;
import id.holigo.services.holigoshipservice.web.model.ShipAvailabilityDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ShipAvailabilityController {

    private InquiryRepository inquiryRepository;

    private InquiryMapper inquiryMapper;

    private ShipService shipService;

    @Autowired
    public void setInquiryRepository(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }


    @Autowired
    public void setShipService(ShipService shipService) {
        this.shipService = shipService;
    }

    @Autowired
    public void setInquiryMapper(InquiryMapper inquiryMapper) {
        this.inquiryMapper = inquiryMapper;
    }

    @GetMapping("/api/v1/ship/availabilities")
    public ResponseEntity<ListAvailabilityDto> getAvailabilities(InquiryDto inquiryDto, @RequestHeader("user-id") Long userId) {
        inquiryDto.setUserId(userId);
        Inquiry inquiry;
        Optional<Inquiry> fetchInquiry = inquiryRepository.getInquiry(
                inquiryDto.getOriginHarborId(),
                inquiryDto.getDestinationHarborId(),
                inquiryDto.getDepartureDate().toString(),
                inquiryDto.getTripType().toString(),
                inquiryDto.getAdultMaleAmount(),
                inquiryDto.getAdultFemaleAmount(),
                inquiryDto.getChildAmount(),
                inquiryDto.getInfantAmount(),
                inquiryDto.getIsFamily()
        );
        if (fetchInquiry.isEmpty()) {
            try {
                inquiry = inquiryRepository.save(inquiryMapper.inquiryDtoToInquiry(inquiryDto));
                inquiryDto.setId(inquiry.getId());
            } catch (AvailabilitiesException e) {
                throw new AvailabilitiesException();
            }
        } else {
            inquiry = fetchInquiry.get();
            inquiryDto.setId(inquiry.getId());
        }

        ListAvailabilityDto listAvailabilityDto;
        try {
            listAvailabilityDto = shipService.getSchedule(inquiryDto);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listAvailabilityDto.getDepartures() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listAvailabilityDto, HttpStatus.OK);
    }
}
