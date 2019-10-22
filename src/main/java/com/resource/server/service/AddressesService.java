package com.resource.server.service;

import com.resource.server.service.dto.AddressesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.resource.server.domain.Addresses}.
 */
public interface AddressesService {

    /**
     * Save a addresses.
     *
     * @param addressesDTO the entity to save.
     * @return the persisted entity.
     */
    AddressesDTO save(AddressesDTO addressesDTO);

    /**
     * Get all the addresses.
     *
     * @return the list of entities.
     */
    List<AddressesDTO> findAll();


    /**
     * Get the "id" addresses.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AddressesDTO> findOne(Long id);

    /**
     * Delete the "id" addresses.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
