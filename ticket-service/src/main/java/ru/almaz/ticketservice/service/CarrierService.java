package ru.almaz.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.ticketservice.dao.CarrierDao;
import ru.almaz.ticketservice.dto.CarrierDto;
import ru.almaz.ticketservice.dto.AddCarrierRequest;
import ru.almaz.ticketservice.entity.Carrier;
import ru.almaz.ticketservice.mapper.CarrierMapper;

@Service
@RequiredArgsConstructor
public class CarrierService {

    private final CarrierDao carrierDao;

    private final CarrierMapper carrierMapper;

    @Transactional
    public CarrierDto save(AddCarrierRequest carrierRequest) {
        Carrier carrier = carrierMapper.toCarrier(carrierRequest);
        carrierDao.save(carrier);
        return carrierMapper.toCarrierDto(carrier);
    }
}
