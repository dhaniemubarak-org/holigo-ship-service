package id.holigo.services.holigoshipservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.holigo.services.holigoshipservice.domain.ShipFinalFare;
import id.holigo.services.holigoshipservice.web.model.InquiryDto;
import id.holigo.services.holigoshipservice.web.model.ListAvailabilityDto;
import id.holigo.services.holigoshipservice.web.model.RequestFinalFareDto;

public interface ShipService {

    ListAvailabilityDto getSchedule(InquiryDto inquiryDto) throws JsonProcessingException;

    ShipFinalFare createFinalFare(RequestFinalFareDto requestFinalFareDto, Long userId);

    void saveAvailabilities(ListAvailabilityDto listAvailabilityDto);
}
