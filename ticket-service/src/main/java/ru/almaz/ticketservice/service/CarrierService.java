package ru.almaz.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.ticketservice.dao.CarrierDao;
import ru.almaz.ticketservice.dto.CarrierDto;
import ru.almaz.ticketservice.dto.AddCarrierRequest;
import ru.almaz.ticketservice.dto.UpdateCarrierDto;
import ru.almaz.ticketservice.entity.Carrier;
import ru.almaz.ticketservice.mapper.CarrierMapper;
import ru.almaz.ticketservice.validator.CarrierValidator;

@Service
@RequiredArgsConstructor
public class CarrierService {

    private final CarrierDao carrierDao;

    private final CarrierMapper carrierMapper;

    private final CarrierValidator carrierValidator;

    @Transactional
    public CarrierDto save(AddCarrierRequest carrierRequest) {
        Carrier carrier = carrierMapper.toCarrier(carrierRequest);
        carrierDao.save(carrier);
        return carrierMapper.toCarrierDto(carrier);
    }

    @Transactional
    public CarrierDto updateCarrier(Long carrierId,UpdateCarrierDto carrierRequest) {
        carrierValidator.isCarrierValid(carrierId);
        Carrier carrier = carrierDao.updateCarrier(carrierId, carrierRequest);
        return carrierMapper.toCarrierDto(carrier);
    }
}
