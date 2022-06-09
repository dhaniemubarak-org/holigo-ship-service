package id.holigo.services.holigoshipservice.services.retross;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.holigo.services.holigoshipservice.web.model.RetrossRequestScheduleDto;
import id.holigo.services.holigoshipservice.web.model.RetrossResponseScheduleDto;

public interface RetrossShipService {

    RetrossResponseScheduleDto getSchedule(RetrossRequestScheduleDto retrossRequestScheduleDto) throws JsonProcessingException;
}
