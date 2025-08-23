package ru.almaz.ticketservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.almaz.ticketservice.dto.carrier.CarrierDto;
import ru.almaz.ticketservice.dto.carrier.AddCarrierRequest;
import ru.almaz.ticketservice.dto.carrier.UpdateCarrierDto;
import ru.almaz.ticketservice.service.CarrierService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carrier")
public class CarrierController {

    private final CarrierService carrierService;

    @PostMapping
    public CarrierDto addCarrier(@RequestBody @Valid AddCarrierRequest carrierRequest) {
        return carrierService.save(carrierRequest);
    }

    @PatchMapping("/{carrierId}")
    public CarrierDto updateCarrier(@PathVariable Long carrierId,
                                    @RequestBody @Valid UpdateCarrierDto updateCarrierDto) {
        return carrierService.updateCarrier(carrierId,updateCarrierDto);
    }

    @DeleteMapping("/{carrierId}")
    public void deleteCarrier(@PathVariable Long carrierId) {
        carrierService.deleteCarrier(carrierId);
    }
}
