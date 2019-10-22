package com.resource.server.service;

import com.resource.server.service.dto.PurchaseOrdersDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.resource.server.domain.PurchaseOrders}.
 */
public interface PurchaseOrdersService {

    /**
     * Save a purchaseOrders.
     *
     * @param purchaseOrdersDTO the entity to save.
     * @return the persisted entity.
     */
    PurchaseOrdersDTO save(PurchaseOrdersDTO purchaseOrdersDTO);

    /**
     * Get all the purchaseOrders.
     *
     * @return the list of entities.
     */
    List<PurchaseOrdersDTO> findAll();


    /**
     * Get the "id" purchaseOrders.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseOrdersDTO> findOne(Long id);

    /**
     * Delete the "id" purchaseOrders.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
