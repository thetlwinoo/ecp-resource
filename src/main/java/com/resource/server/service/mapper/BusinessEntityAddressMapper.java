package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.BusinessEntityAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BusinessEntityAddress} and its DTO {@link BusinessEntityAddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {AddressesMapper.class, PeopleMapper.class, AddressTypesMapper.class})
public interface BusinessEntityAddressMapper extends EntityMapper<BusinessEntityAddressDTO, BusinessEntityAddress> {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "addressType.id", target = "addressTypeId")
    @Mapping(source = "addressType.addressTypeName", target = "addressTypeAddressTypeName")
    BusinessEntityAddressDTO toDto(BusinessEntityAddress businessEntityAddress);

    @Mapping(source = "addressId", target = "address")
    @Mapping(source = "personId", target = "person")
    @Mapping(source = "addressTypeId", target = "addressType")
    BusinessEntityAddress toEntity(BusinessEntityAddressDTO businessEntityAddressDTO);

    default BusinessEntityAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        BusinessEntityAddress businessEntityAddress = new BusinessEntityAddress();
        businessEntityAddress.setId(id);
        return businessEntityAddress;
    }
}
