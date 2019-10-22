package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.PurchaseOrderLinesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseOrderLines} and its DTO {@link PurchaseOrderLinesDTO}.
 */
@Mapper(componentModel = "spring", uses = {PackageTypesMapper.class, StockItemsMapper.class, PurchaseOrdersMapper.class})
public interface PurchaseOrderLinesMapper extends EntityMapper<PurchaseOrderLinesDTO, PurchaseOrderLines> {

    @Mapping(source = "packageType.id", target = "packageTypeId")
    @Mapping(source = "packageType.packageTypeName", target = "packageTypePackageTypeName")
    @Mapping(source = "stockItem.id", target = "stockItemId")
    @Mapping(source = "stockItem.stockItemName", target = "stockItemStockItemName")
    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    PurchaseOrderLinesDTO toDto(PurchaseOrderLines purchaseOrderLines);

    @Mapping(source = "packageTypeId", target = "packageType")
    @Mapping(source = "stockItemId", target = "stockItem")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    PurchaseOrderLines toEntity(PurchaseOrderLinesDTO purchaseOrderLinesDTO);

    default PurchaseOrderLines fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseOrderLines purchaseOrderLines = new PurchaseOrderLines();
        purchaseOrderLines.setId(id);
        return purchaseOrderLines;
    }
}
