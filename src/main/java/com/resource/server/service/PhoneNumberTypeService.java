package com.resource.server.service;

import com.resource.server.service.dto.PhoneNumberTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.resource.server.domain.PhoneNumberType}.
 */
public interface PhoneNumberTypeService {

    /**
     * Save a phoneNumberType.
     *
     * @param phoneNumberTypeDTO the entity to save.
     * @return the persisted entity.
     */
    PhoneNumberTypeDTO save(PhoneNumberTypeDTO phoneNumberTypeDTO);

    /**
     * Get all the phoneNumberTypes.
     *
     * @return the list of entities.
     */
    List<PhoneNumberTypeDTO> findAll();


    /**
     * Get the "id" phoneNumberType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PhoneNumberTypeDTO> findOne(Long id);

    /**
     * Delete the "id" phoneNumberType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
