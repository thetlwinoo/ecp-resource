package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductCategory} and its DTO {@link ProductCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductCategoryMapper extends EntityMapper<ProductCategoryDTO, ProductCategory> {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    ProductCategoryDTO toDto(ProductCategory productCategory);

    @Mapping(source = "parentId", target = "parent")
    ProductCategory toEntity(ProductCategoryDTO productCategoryDTO);

    default ProductCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(id);
        return productCategory;
    }
}
