package id.holigo.services.holigoshipservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HoligoShipServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoligoShipServiceApplication.class, args);
    }

}
