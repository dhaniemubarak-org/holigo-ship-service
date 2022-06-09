package id.holigo.services.holigoshipservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.holigo.services.holigoshipservice.web.model.InquiryDto;
import id.holigo.services.holigoshipservice.web.model.ListAvailabilityDto;

public interface ShipService {

    ListAvailabilityDto getSchedule(InquiryDto inquiryDto) throws JsonProcessingException;

    void saveAvailabilities(ListAvailabilityDto listAvailabilityDto);
}
