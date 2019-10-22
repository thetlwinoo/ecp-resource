package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.SystemParametersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SystemParameters} and its DTO {@link SystemParametersDTO}.
 */
@Mapper(componentModel = "spring", uses = {CitiesMapper.class})
public interface SystemParametersMapper extends EntityMapper<SystemParametersDTO, SystemParameters> {

    @Mapping(source = "deliveryCity.id", target = "deliveryCityId")
    @Mapping(source = "deliveryCity.cityName", target = "deliveryCityCityName")
    @Mapping(source = "postalCity.id", target = "postalCityId")
    @Mapping(source = "postalCity.cityName", target = "postalCityCityName")
    SystemParametersDTO toDto(SystemParameters systemParameters);

    @Mapping(source = "deliveryCityId", target = "deliveryCity")
    @Mapping(source = "postalCityId", target = "postalCity")
    SystemParameters toEntity(SystemParametersDTO systemParametersDTO);

    default SystemParameters fromId(Long id) {
        if (id == null) {
            return null;
        }
        SystemParameters systemParameters = new SystemParameters();
        systemParameters.setId(id);
        return systemParameters;
    }
}
