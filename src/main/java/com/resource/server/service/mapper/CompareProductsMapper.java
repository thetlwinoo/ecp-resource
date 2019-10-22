package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.CompareProductsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompareProducts} and its DTO {@link CompareProductsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductsMapper.class, ComparesMapper.class})
public interface CompareProductsMapper extends EntityMapper<CompareProductsDTO, CompareProducts> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    @Mapping(source = "compare.id", target = "compareId")
    CompareProductsDTO toDto(CompareProducts compareProducts);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "compareId", target = "compare")
    CompareProducts toEntity(CompareProductsDTO compareProductsDTO);

    default CompareProducts fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompareProducts compareProducts = new CompareProducts();
        compareProducts.setId(id);
        return compareProducts;
    }
}
