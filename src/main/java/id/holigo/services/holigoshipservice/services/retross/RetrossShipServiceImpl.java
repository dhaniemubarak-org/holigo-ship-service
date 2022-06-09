package id.holigo.services.holigoshipservice.services.retross;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.holigo.services.holigoshipservice.web.model.RetrossRequestScheduleDto;
import id.holigo.services.holigoshipservice.web.model.RetrossResponseScheduleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RetrossShipServiceImpl implements RetrossShipService {
    private static final String RETROSS_ID = "holigo";

    private static final String RETROSS_PASSKEY = "H0LJSHRG3754875Y4698NKJWEF8UHIGO";

    private static final String RETROSS_APP_INFORMATION = "information";

    private static final String RETROSS_ACTION_GET_SCHEDULE = "get_schedule";


    private RetrossShipServiceFeignClient retrossShipServiceFeignClient;


    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setRetrossShipServiceFeignClient(RetrossShipServiceFeignClient retrossShipServiceFeignClient) {
        this.retrossShipServiceFeignClient = retrossShipServiceFeignClient;
    }

    @Override
    public RetrossResponseScheduleDto getSchedule(RetrossRequestScheduleDto retrossRequestScheduleDto) throws JsonProcessingException {
        retrossRequestScheduleDto.setMmid(RETROSS_ID);
        retrossRequestScheduleDto.setRqid(RETROSS_PASSKEY);
        retrossRequestScheduleDto.setApp(RETROSS_APP_INFORMATION);
        retrossRequestScheduleDto.setAction(RETROSS_ACTION_GET_SCHEDULE);
        log.info("Calling get schedule with request -> {}", objectMapper.writeValueAsString(retrossRequestScheduleDto));

        RetrossResponseScheduleDto retrossResponseScheduleDto;
        ResponseEntity<String> responseEntity = retrossShipServiceFeignClient.getSchedule(retrossRequestScheduleDto);

        log.info("ResponseEntity body -> {}", responseEntity.getBody());
        retrossResponseScheduleDto = objectMapper.readValue(responseEntity.getBody(), RetrossResponseScheduleDto.class);
        log.info("retross -> {}", objectMapper.writeValueAsString(retrossResponseScheduleDto));
        return retrossResponseScheduleDto;
    }
}
