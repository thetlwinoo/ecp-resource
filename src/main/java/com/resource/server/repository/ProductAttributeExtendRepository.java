package com.resource.server.repository;

import com.resource.server.domain.ProductAttribute;

import java.util.List;

public interface ProductAttributeExtendRepository extends ProductAttributeRepository {
    List<ProductAttribute> findAllByProductAttributeSetIdAndSupplierId(Long attributeSetId, Long supplierId);
}
