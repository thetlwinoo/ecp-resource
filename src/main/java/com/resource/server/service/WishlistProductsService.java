package com.resource.server.service;

import com.resource.server.service.dto.WishlistProductsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.resource.server.domain.WishlistProducts}.
 */
public interface WishlistProductsService {

    /**
     * Save a wishlistProducts.
     *
     * @param wishlistProductsDTO the entity to save.
     * @return the persisted entity.
     */
    WishlistProductsDTO save(WishlistProductsDTO wishlistProductsDTO);

    /**
     * Get all the wishlistProducts.
     *
     * @return the list of entities.
     */
    List<WishlistProductsDTO> findAll();


    /**
     * Get the "id" wishlistProducts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WishlistProductsDTO> findOne(Long id);

    /**
     * Delete the "id" wishlistProducts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
