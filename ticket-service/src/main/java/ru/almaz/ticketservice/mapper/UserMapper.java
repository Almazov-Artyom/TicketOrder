package ru.almaz.ticketservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.almaz.ticketservice.dto.auth.RegistrationRequest;
import ru.almaz.ticketservice.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toUser(RegistrationRequest registrationRequest);
}
