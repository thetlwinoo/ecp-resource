package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.SupplierCategoriesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupplierCategories} and its DTO {@link SupplierCategoriesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SupplierCategoriesMapper extends EntityMapper<SupplierCategoriesDTO, SupplierCategories> {



    default SupplierCategories fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplierCategories supplierCategories = new SupplierCategories();
        supplierCategories.setId(id);
        return supplierCategories;
    }
}
