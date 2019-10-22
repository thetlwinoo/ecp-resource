package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.AddressesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Addresses} and its DTO {@link AddressesDTO}.
 */
@Mapper(componentModel = "spring", uses = {StateProvincesMapper.class, AddressTypesMapper.class, PeopleMapper.class})
public interface AddressesMapper extends EntityMapper<AddressesDTO, Addresses> {

    @Mapping(source = "stateProvince.id", target = "stateProvinceId")
    @Mapping(source = "stateProvince.stateProvinceName", target = "stateProvinceStateProvinceName")
    @Mapping(source = "addressType.id", target = "addressTypeId")
    @Mapping(source = "addressType.addressTypeName", target = "addressTypeAddressTypeName")
    @Mapping(source = "person.id", target = "personId")
    AddressesDTO toDto(Addresses addresses);

    @Mapping(source = "stateProvinceId", target = "stateProvince")
    @Mapping(source = "addressTypeId", target = "addressType")
    @Mapping(source = "personId", target = "person")
    Addresses toEntity(AddressesDTO addressesDTO);

    default Addresses fromId(Long id) {
        if (id == null) {
            return null;
        }
        Addresses addresses = new Addresses();
        addresses.setId(id);
        return addresses;
    }
}
