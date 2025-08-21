package ru.almaz.ticketservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.almaz.ticketservice.dto.CarrierDto;
import ru.almaz.ticketservice.dto.CarrierRequest;
import ru.almaz.ticketservice.service.CarrierService;

@RestController
@RequiredArgsConstructor
public class CarrierController {

    private final CarrierService carrierService;

    @PostMapping("/carrier")
    public CarrierDto addCarrier(@RequestBody CarrierRequest carrierRequest) {
        return carrierService.save(carrierRequest);
    }
}
