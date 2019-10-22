package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.PackageTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PackageTypes} and its DTO {@link PackageTypesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PackageTypesMapper extends EntityMapper<PackageTypesDTO, PackageTypes> {



    default PackageTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        PackageTypes packageTypes = new PackageTypes();
        packageTypes.setId(id);
        return packageTypes;
    }
}
