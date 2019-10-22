package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductOptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductOption} and its DTO {@link ProductOptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductOptionSetMapper.class, SuppliersMapper.class})
public interface ProductOptionMapper extends EntityMapper<ProductOptionDTO, ProductOption> {

    @Mapping(source = "productOptionSet.id", target = "productOptionSetId")
    @Mapping(source = "productOptionSet.productOptionSetValue", target = "productOptionSetProductOptionSetValue")
    @Mapping(source = "supplier.id", target = "supplierId")
    ProductOptionDTO toDto(ProductOption productOption);

    @Mapping(source = "productOptionSetId", target = "productOptionSet")
    @Mapping(source = "supplierId", target = "supplier")
    ProductOption toEntity(ProductOptionDTO productOptionDTO);

    default ProductOption fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductOption productOption = new ProductOption();
        productOption.setId(id);
        return productOption;
    }
}
