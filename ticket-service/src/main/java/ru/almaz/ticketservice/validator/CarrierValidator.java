package ru.almaz.ticketservice.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.dao.CarrierDao;
import ru.almaz.ticketservice.exception.CarrierNotFoundException;

@Component
@RequiredArgsConstructor
public class CarrierValidator {

    private final CarrierDao carrierDao;

    public void isCarrierValid(Long carrierId) {
        if(!carrierDao.existCarrier(carrierId)) {
            throw new CarrierNotFoundException("carrier.not.found");
        }
    }
}
