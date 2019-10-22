package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.AddressTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AddressTypes} and its DTO {@link AddressTypesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AddressTypesMapper extends EntityMapper<AddressTypesDTO, AddressTypes> {



    default AddressTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        AddressTypes addressTypes = new AddressTypes();
        addressTypes.setId(id);
        return addressTypes;
    }
}
