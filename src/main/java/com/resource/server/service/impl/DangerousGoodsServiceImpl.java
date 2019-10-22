package com.resource.server.service.impl;

import com.resource.server.service.DangerousGoodsService;
import com.resource.server.domain.DangerousGoods;
import com.resource.server.repository.DangerousGoodsRepository;
import com.resource.server.service.dto.DangerousGoodsDTO;
import com.resource.server.service.mapper.DangerousGoodsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DangerousGoods}.
 */
@Service
@Transactional
public class DangerousGoodsServiceImpl implements DangerousGoodsService {

    private final Logger log = LoggerFactory.getLogger(DangerousGoodsServiceImpl.class);

    private final DangerousGoodsRepository dangerousGoodsRepository;

    private final DangerousGoodsMapper dangerousGoodsMapper;

    public DangerousGoodsServiceImpl(DangerousGoodsRepository dangerousGoodsRepository, DangerousGoodsMapper dangerousGoodsMapper) {
        this.dangerousGoodsRepository = dangerousGoodsRepository;
        this.dangerousGoodsMapper = dangerousGoodsMapper;
    }

    /**
     * Save a dangerousGoods.
     *
     * @param dangerousGoodsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DangerousGoodsDTO save(DangerousGoodsDTO dangerousGoodsDTO) {
        log.debug("Request to save DangerousGoods : {}", dangerousGoodsDTO);
        DangerousGoods dangerousGoods = dangerousGoodsMapper.toEntity(dangerousGoodsDTO);
        dangerousGoods = dangerousGoodsRepository.save(dangerousGoods);
        return dangerousGoodsMapper.toDto(dangerousGoods);
    }

    /**
     * Get all the dangerousGoods.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DangerousGoodsDTO> findAll() {
        log.debug("Request to get all DangerousGoods");
        return dangerousGoodsRepository.findAll().stream()
            .map(dangerousGoodsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dangerousGoods by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DangerousGoodsDTO> findOne(Long id) {
        log.debug("Request to get DangerousGoods : {}", id);
        return dangerousGoodsRepository.findById(id)
            .map(dangerousGoodsMapper::toDto);
    }

    /**
     * Delete the dangerousGoods by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DangerousGoods : {}", id);
        dangerousGoodsRepository.deleteById(id);
    }
}
