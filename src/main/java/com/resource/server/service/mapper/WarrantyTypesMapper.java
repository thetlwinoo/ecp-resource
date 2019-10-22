package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.WarrantyTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WarrantyTypes} and its DTO {@link WarrantyTypesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WarrantyTypesMapper extends EntityMapper<WarrantyTypesDTO, WarrantyTypes> {



    default WarrantyTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        WarrantyTypes warrantyTypes = new WarrantyTypes();
        warrantyTypes.setId(id);
        return warrantyTypes;
    }
}
