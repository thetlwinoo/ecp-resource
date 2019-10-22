package com.resource.server.service.impl;

import com.resource.server.service.ProductDocumentService;
import com.resource.server.domain.ProductDocument;
import com.resource.server.repository.ProductDocumentRepository;
import com.resource.server.service.dto.ProductDocumentDTO;
import com.resource.server.service.mapper.ProductDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link ProductDocument}.
 */
@Service
@Transactional
public class ProductDocumentServiceImpl implements ProductDocumentService {

    private final Logger log = LoggerFactory.getLogger(ProductDocumentServiceImpl.class);

    private final ProductDocumentRepository productDocumentRepository;

    private final ProductDocumentMapper productDocumentMapper;

    public ProductDocumentServiceImpl(ProductDocumentRepository productDocumentRepository, ProductDocumentMapper productDocumentMapper) {
        this.productDocumentRepository = productDocumentRepository;
        this.productDocumentMapper = productDocumentMapper;
    }

    /**
     * Save a productDocument.
     *
     * @param productDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductDocumentDTO save(ProductDocumentDTO productDocumentDTO) {
        log.debug("Request to save ProductDocument : {}", productDocumentDTO);
        ProductDocument productDocument = productDocumentMapper.toEntity(productDocumentDTO);
        productDocument = productDocumentRepository.save(productDocument);
        return productDocumentMapper.toDto(productDocument);
    }

    /**
     * Get all the productDocuments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductDocumentDTO> findAll() {
        log.debug("Request to get all ProductDocuments");
        return productDocumentRepository.findAll().stream()
            .map(productDocumentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the productDocuments where Product is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ProductDocumentDTO> findAllWhereProductIsNull() {
        log.debug("Request to get all productDocuments where Product is null");
        return StreamSupport
            .stream(productDocumentRepository.findAll().spliterator(), false)
            .filter(productDocument -> productDocument.getProduct() == null)
            .map(productDocumentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one productDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDocumentDTO> findOne(Long id) {
        log.debug("Request to get ProductDocument : {}", id);
        return productDocumentRepository.findById(id)
            .map(productDocumentMapper::toDto);
    }

    /**
     * Delete the productDocument by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductDocument : {}", id);
        productDocumentRepository.deleteById(id);
    }
}
