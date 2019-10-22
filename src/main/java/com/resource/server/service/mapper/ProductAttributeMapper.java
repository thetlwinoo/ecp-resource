package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductAttributeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductAttribute} and its DTO {@link ProductAttributeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductAttributeSetMapper.class, SuppliersMapper.class})
public interface ProductAttributeMapper extends EntityMapper<ProductAttributeDTO, ProductAttribute> {

    @Mapping(source = "productAttributeSet.id", target = "productAttributeSetId")
    @Mapping(source = "productAttributeSet.productAttributeSetName", target = "productAttributeSetProductAttributeSetName")
    @Mapping(source = "supplier.id", target = "supplierId")
    ProductAttributeDTO toDto(ProductAttribute productAttribute);

    @Mapping(source = "productAttributeSetId", target = "productAttributeSet")
    @Mapping(source = "supplierId", target = "supplier")
    ProductAttribute toEntity(ProductAttributeDTO productAttributeDTO);

    default ProductAttribute fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setId(id);
        return productAttribute;
    }
}
