package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.CustomerCategoriesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerCategories} and its DTO {@link CustomerCategoriesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerCategoriesMapper extends EntityMapper<CustomerCategoriesDTO, CustomerCategories> {



    default CustomerCategories fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerCategories customerCategories = new CustomerCategories();
        customerCategories.setId(id);
        return customerCategories;
    }
}
