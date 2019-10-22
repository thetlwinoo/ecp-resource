package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.SuppliersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Suppliers} and its DTO {@link SuppliersDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class, SupplierCategoriesMapper.class, DeliveryMethodsMapper.class, CitiesMapper.class})
public interface SuppliersMapper extends EntityMapper<SuppliersDTO, Suppliers> {

    @Mapping(source = "primaryContactPerson.id", target = "primaryContactPersonId")
    @Mapping(source = "primaryContactPerson.fullName", target = "primaryContactPersonFullName")
    @Mapping(source = "alternateContactPerson.id", target = "alternateContactPersonId")
    @Mapping(source = "alternateContactPerson.fullName", target = "alternateContactPersonFullName")
    @Mapping(source = "supplierCategory.id", target = "supplierCategoryId")
    @Mapping(source = "supplierCategory.supplierCategoryName", target = "supplierCategorySupplierCategoryName")
    @Mapping(source = "deliveryMethod.id", target = "deliveryMethodId")
    @Mapping(source = "deliveryMethod.deliveryMethodName", target = "deliveryMethodDeliveryMethodName")
    @Mapping(source = "deliveryCity.id", target = "deliveryCityId")
    @Mapping(source = "deliveryCity.cityName", target = "deliveryCityCityName")
    @Mapping(source = "postalCity.id", target = "postalCityId")
    @Mapping(source = "postalCity.cityName", target = "postalCityCityName")
    SuppliersDTO toDto(Suppliers suppliers);

    @Mapping(source = "primaryContactPersonId", target = "primaryContactPerson")
    @Mapping(source = "alternateContactPersonId", target = "alternateContactPerson")
    @Mapping(source = "supplierCategoryId", target = "supplierCategory")
    @Mapping(source = "deliveryMethodId", target = "deliveryMethod")
    @Mapping(source = "deliveryCityId", target = "deliveryCity")
    @Mapping(source = "postalCityId", target = "postalCity")
    Suppliers toEntity(SuppliersDTO suppliersDTO);

    default Suppliers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Suppliers suppliers = new Suppliers();
        suppliers.setId(id);
        return suppliers;
    }
}
