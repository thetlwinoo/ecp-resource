package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Products} and its DTO {@link ProductsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductDocumentMapper.class, SuppliersMapper.class, ProductCategoryMapper.class, ProductBrandMapper.class})
public interface ProductsMapper extends EntityMapper<ProductsDTO, Products> {

    @Mapping(source = "document.id", target = "documentId")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.supplierName", target = "supplierSupplierName")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    @Mapping(source = "productBrand.id", target = "productBrandId")
    @Mapping(source = "productBrand.productBrandName", target = "productBrandProductBrandName")
    ProductsDTO toDto(Products products);

    @Mapping(source = "documentId", target = "document")
    @Mapping(target = "stockItemLists", ignore = true)
    @Mapping(target = "removeStockItemList", ignore = true)
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "productCategoryId", target = "productCategory")
    @Mapping(source = "productBrandId", target = "productBrand")
    Products toEntity(ProductsDTO productsDTO);

    default Products fromId(Long id) {
        if (id == null) {
            return null;
        }
        Products products = new Products();
        products.setId(id);
        return products;
    }
}
