package com.resource.server.repository;

import com.resource.server.domain.ProductChoice;

import java.util.List;

public interface ProductChoiceExtendRepository extends ProductChoiceRepository {
    List<ProductChoice> findAllByProductCategoryId(Long categoryId);
}
