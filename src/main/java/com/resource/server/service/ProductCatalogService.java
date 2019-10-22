package com.resource.server.service;

import com.resource.server.service.dto.ProductCatalogDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.resource.server.domain.ProductCatalog}.
 */
public interface ProductCatalogService {

    /**
     * Save a productCatalog.
     *
     * @param productCatalogDTO the entity to save.
     * @return the persisted entity.
     */
    ProductCatalogDTO save(ProductCatalogDTO productCatalogDTO);

    /**
     * Get all the productCatalogs.
     *
     * @return the list of entities.
     */
    List<ProductCatalogDTO> findAll();


    /**
     * Get the "id" productCatalog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductCatalogDTO> findOne(Long id);

    /**
     * Delete the "id" productCatalog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
