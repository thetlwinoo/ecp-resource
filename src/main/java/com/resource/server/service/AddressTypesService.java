package com.resource.server.service;

import com.resource.server.service.dto.AddressTypesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.resource.server.domain.AddressTypes}.
 */
public interface AddressTypesService {

    /**
     * Save a addressTypes.
     *
     * @param addressTypesDTO the entity to save.
     * @return the persisted entity.
     */
    AddressTypesDTO save(AddressTypesDTO addressTypesDTO);

    /**
     * Get all the addressTypes.
     *
     * @return the list of entities.
     */
    List<AddressTypesDTO> findAll();


    /**
     * Get the "id" addressTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AddressTypesDTO> findOne(Long id);

    /**
     * Delete the "id" addressTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
