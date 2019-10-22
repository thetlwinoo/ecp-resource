package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.DeliveryMethodsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeliveryMethods} and its DTO {@link DeliveryMethodsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeliveryMethodsMapper extends EntityMapper<DeliveryMethodsDTO, DeliveryMethods> {



    default DeliveryMethods fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeliveryMethods deliveryMethods = new DeliveryMethods();
        deliveryMethods.setId(id);
        return deliveryMethods;
    }
}
