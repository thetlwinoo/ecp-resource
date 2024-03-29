package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ProductDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductDocument} and its DTO {@link ProductDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {WarrantyTypesMapper.class, CultureMapper.class})
public interface ProductDocumentMapper extends EntityMapper<ProductDocumentDTO, ProductDocument> {

    @Mapping(source = "warrantyType.id", target = "warrantyTypeId")
    @Mapping(source = "warrantyType.warrantyTypeName", target = "warrantyTypeWarrantyTypeName")
    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.cultureName", target = "cultureCultureName")
    ProductDocumentDTO toDto(ProductDocument productDocument);

    @Mapping(source = "warrantyTypeId", target = "warrantyType")
    @Mapping(source = "cultureId", target = "culture")
    @Mapping(target = "product", ignore = true)
    ProductDocument toEntity(ProductDocumentDTO productDocumentDTO);

    default ProductDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductDocument productDocument = new ProductDocument();
        productDocument.setId(id);
        return productDocument;
    }
}
