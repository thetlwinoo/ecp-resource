package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.VehicleTemperaturesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleTemperatures} and its DTO {@link VehicleTemperaturesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VehicleTemperaturesMapper extends EntityMapper<VehicleTemperaturesDTO, VehicleTemperatures> {



    default VehicleTemperatures fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehicleTemperatures vehicleTemperatures = new VehicleTemperatures();
        vehicleTemperatures.setId(id);
        return vehicleTemperatures;
    }
}
