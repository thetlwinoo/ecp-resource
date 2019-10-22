package com.resource.server.service;

import com.resource.server.service.dto.VehicleTemperaturesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.resource.server.domain.VehicleTemperatures}.
 */
public interface VehicleTemperaturesService {

    /**
     * Save a vehicleTemperatures.
     *
     * @param vehicleTemperaturesDTO the entity to save.
     * @return the persisted entity.
     */
    VehicleTemperaturesDTO save(VehicleTemperaturesDTO vehicleTemperaturesDTO);

    /**
     * Get all the vehicleTemperatures.
     *
     * @return the list of entities.
     */
    List<VehicleTemperaturesDTO> findAll();


    /**
     * Get the "id" vehicleTemperatures.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VehicleTemperaturesDTO> findOne(Long id);

    /**
     * Delete the "id" vehicleTemperatures.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
