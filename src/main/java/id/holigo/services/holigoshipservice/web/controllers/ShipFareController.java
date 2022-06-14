package id.holigo.services.holigoshipservice.web.controllers;

import id.holigo.services.holigoshipservice.domain.ShipFinalFare;
import id.holigo.services.holigoshipservice.services.ShipService;
import id.holigo.services.holigoshipservice.web.model.RequestFinalFareDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
public class ShipFareController {
    private ShipService shipService;

    public static final String PATH = "/api/v1/ship/fares";

    @Autowired
    public void setShipService(ShipService shipService) {
        this.shipService = shipService;
    }

    @PostMapping(PATH)
    public ResponseEntity<HttpStatus> createFare(@RequestBody RequestFinalFareDto requestFinalFareDto, @RequestHeader("user-id") Long userId) {
        log.info("Fare running");
        ShipFinalFare shipFinalFare = shipService.createFinalFare(requestFinalFareDto, userId);
        if (shipFinalFare == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(UriComponentsBuilder.fromPath(PATH + "/{id}").buildAndExpand(shipFinalFare.getId()).toUri());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }
}
