package com.resource.server.repository;

import com.resource.server.domain.ProductCategory;

import java.util.List;

public interface ProductCategoryExtendRepository extends ProductCategoryRepository {
    List<ProductCategory> findAllByParentIdIsNull();
    List<ProductCategory> findAllByParentId(Long parentId);
    ProductCategory findProductCategoryByNameContaining(String categoryName);
}
