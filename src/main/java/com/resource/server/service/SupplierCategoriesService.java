package com.resource.server.service;

import com.resource.server.service.dto.SupplierCategoriesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.resource.server.domain.SupplierCategories}.
 */
public interface SupplierCategoriesService {

    /**
     * Save a supplierCategories.
     *
     * @param supplierCategoriesDTO the entity to save.
     * @return the persisted entity.
     */
    SupplierCategoriesDTO save(SupplierCategoriesDTO supplierCategoriesDTO);

    /**
     * Get all the supplierCategories.
     *
     * @return the list of entities.
     */
    List<SupplierCategoriesDTO> findAll();


    /**
     * Get the "id" supplierCategories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupplierCategoriesDTO> findOne(Long id);

    /**
     * Delete the "id" supplierCategories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
