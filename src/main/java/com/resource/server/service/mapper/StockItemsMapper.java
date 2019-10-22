package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.StockItemsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StockItems} and its DTO {@link StockItemsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReviewLinesMapper.class, UnitMeasureMapper.class, ProductAttributeMapper.class, ProductOptionMapper.class, MaterialsMapper.class, CurrencyMapper.class, BarcodeTypesMapper.class, ProductsMapper.class})
public interface StockItemsMapper extends EntityMapper<StockItemsDTO, StockItems> {

    @Mapping(source = "stockItemOnReviewLine.id", target = "stockItemOnReviewLineId")
    @Mapping(source = "itemLengthUnit.id", target = "itemLengthUnitId")
    @Mapping(source = "itemLengthUnit.unitMeasureCode", target = "itemLengthUnitUnitMeasureCode")
    @Mapping(source = "itemWidthUnit.id", target = "itemWidthUnitId")
    @Mapping(source = "itemWidthUnit.unitMeasureCode", target = "itemWidthUnitUnitMeasureCode")
    @Mapping(source = "itemHeightUnit.id", target = "itemHeightUnitId")
    @Mapping(source = "itemHeightUnit.unitMeasureCode", target = "itemHeightUnitUnitMeasureCode")
    @Mapping(source = "packageLengthUnit.id", target = "packageLengthUnitId")
    @Mapping(source = "packageLengthUnit.unitMeasureCode", target = "packageLengthUnitUnitMeasureCode")
    @Mapping(source = "packageWidthUnit.id", target = "packageWidthUnitId")
    @Mapping(source = "packageWidthUnit.unitMeasureCode", target = "packageWidthUnitUnitMeasureCode")
    @Mapping(source = "packageHeightUnit.id", target = "packageHeightUnitId")
    @Mapping(source = "packageHeightUnit.unitMeasureCode", target = "packageHeightUnitUnitMeasureCode")
    @Mapping(source = "itemPackageWeightUnit.id", target = "itemPackageWeightUnitId")
    @Mapping(source = "itemPackageWeightUnit.unitMeasureCode", target = "itemPackageWeightUnitUnitMeasureCode")
    @Mapping(source = "productAttribute.id", target = "productAttributeId")
    @Mapping(source = "productAttribute.productAttributeValue", target = "productAttributeProductAttributeValue")
    @Mapping(source = "productOption.id", target = "productOptionId")
    @Mapping(source = "productOption.productOptionValue", target = "productOptionProductOptionValue")
    @Mapping(source = "material.id", target = "materialId")
    @Mapping(source = "material.materialName", target = "materialMaterialName")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.currencyCode", target = "currencyCurrencyCode")
    @Mapping(source = "barcodeType.id", target = "barcodeTypeId")
    @Mapping(source = "barcodeType.barcodeTypeName", target = "barcodeTypeBarcodeTypeName")
    @Mapping(source = "product.id", target = "productId")
    StockItemsDTO toDto(StockItems stockItems);

    @Mapping(source = "stockItemOnReviewLineId", target = "stockItemOnReviewLine")
    @Mapping(target = "photoLists", ignore = true)
    @Mapping(target = "removePhotoList", ignore = true)
    @Mapping(target = "dangerousGoodLists", ignore = true)
    @Mapping(target = "removeDangerousGoodList", ignore = true)
    @Mapping(target = "specialDiscounts", ignore = true)
    @Mapping(target = "removeSpecialDiscount", ignore = true)
    @Mapping(source = "itemLengthUnitId", target = "itemLengthUnit")
    @Mapping(source = "itemWidthUnitId", target = "itemWidthUnit")
    @Mapping(source = "itemHeightUnitId", target = "itemHeightUnit")
    @Mapping(source = "packageLengthUnitId", target = "packageLengthUnit")
    @Mapping(source = "packageWidthUnitId", target = "packageWidthUnit")
    @Mapping(source = "packageHeightUnitId", target = "packageHeightUnit")
    @Mapping(source = "itemPackageWeightUnitId", target = "itemPackageWeightUnit")
    @Mapping(source = "productAttributeId", target = "productAttribute")
    @Mapping(source = "productOptionId", target = "productOption")
    @Mapping(source = "materialId", target = "material")
    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "barcodeTypeId", target = "barcodeType")
    @Mapping(target = "stockItemHolding", ignore = true)
    @Mapping(source = "productId", target = "product")
    StockItems toEntity(StockItemsDTO stockItemsDTO);

    default StockItems fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockItems stockItems = new StockItems();
        stockItems.setId(id);
        return stockItems;
    }
}
