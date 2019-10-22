package com.resource.server.repository;

import com.resource.server.domain.ProductOption;

import java.util.List;

public interface ProductOptionExtendRepository extends ProductOptionRepository {
    List<ProductOption> findAllByProductOptionSetIdAndSupplierId(Long attributeSetId, Long supplierId);
}
