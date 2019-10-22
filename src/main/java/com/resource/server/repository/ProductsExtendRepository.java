package com.resource.server.repository;

import com.resource.server.domain.Products;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductsExtendRepository extends ProductsRepository{
    List<Products> findAllByProductCategoryId(Pageable pageable, Long productCategoryId);

    List<Products> findTop18ByOrderByLastEditedWhenDesc();

    List<Products> findTop12ByOrderBySellCountDesc();

    List<Products> findTop12ByProductCategoryIdAndIdIsNotOrderBySellCountDesc(Long productCategoryId, Long id);

    List<Products> findAllByProductCategoryIdIsNotOrderBySellCountDesc(Long productCategoryId, Pageable pageable);

    List<Products> findAllByProductNameContainingIgnoreCase(String name, Pageable pageable);

    List<Products> findAllByProductNameContainingIgnoreCase(String name);

    Products findProductsById(Long id);
}
