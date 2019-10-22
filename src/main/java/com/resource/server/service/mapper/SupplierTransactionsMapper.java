package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.SupplierTransactionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupplierTransactions} and its DTO {@link SupplierTransactionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {SuppliersMapper.class, TransactionTypesMapper.class, PurchaseOrdersMapper.class, PaymentMethodsMapper.class})
public interface SupplierTransactionsMapper extends EntityMapper<SupplierTransactionsDTO, SupplierTransactions> {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.supplierName", target = "supplierSupplierName")
    @Mapping(source = "transactionType.id", target = "transactionTypeId")
    @Mapping(source = "transactionType.transactionTypeName", target = "transactionTypeTransactionTypeName")
    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    @Mapping(source = "paymentMethod.id", target = "paymentMethodId")
    @Mapping(source = "paymentMethod.paymentMethodName", target = "paymentMethodPaymentMethodName")
    SupplierTransactionsDTO toDto(SupplierTransactions supplierTransactions);

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "transactionTypeId", target = "transactionType")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    @Mapping(source = "paymentMethodId", target = "paymentMethod")
    SupplierTransactions toEntity(SupplierTransactionsDTO supplierTransactionsDTO);

    default SupplierTransactions fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplierTransactions supplierTransactions = new SupplierTransactions();
        supplierTransactions.setId(id);
        return supplierTransactions;
    }
}
