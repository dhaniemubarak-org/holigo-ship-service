package id.holigo.services.holigoshipservice.services.retross;

import id.holigo.services.holigoshipservice.web.model.RetrossRequestScheduleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "retross-ship", url = "http://ws.retross.com")
public interface RetrossShipServiceFeignClient {

    public static final String GET_SCHEDULE = "/pelni/";

    @RequestMapping(method = RequestMethod.POST, value = GET_SCHEDULE)
    ResponseEntity<String> getSchedule(@RequestBody RetrossRequestScheduleDto retrossRequestScheduleDto);

}
