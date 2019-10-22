package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.PaymentMethodsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentMethods} and its DTO {@link PaymentMethodsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentMethodsMapper extends EntityMapper<PaymentMethodsDTO, PaymentMethods> {



    default PaymentMethods fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentMethods paymentMethods = new PaymentMethods();
        paymentMethods.setId(id);
        return paymentMethods;
    }
}
