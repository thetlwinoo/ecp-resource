package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.PaymentTransactionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentTransactions} and its DTO {@link PaymentTransactionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrdersMapper.class})
public interface PaymentTransactionsMapper extends EntityMapper<PaymentTransactionsDTO, PaymentTransactions> {

    @Mapping(source = "paymentOnOrder.id", target = "paymentOnOrderId")
    PaymentTransactionsDTO toDto(PaymentTransactions paymentTransactions);

    @Mapping(source = "paymentOnOrderId", target = "paymentOnOrder")
    PaymentTransactions toEntity(PaymentTransactionsDTO paymentTransactionsDTO);

    default PaymentTransactions fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentTransactions paymentTransactions = new PaymentTransactions();
        paymentTransactions.setId(id);
        return paymentTransactions;
    }
}
