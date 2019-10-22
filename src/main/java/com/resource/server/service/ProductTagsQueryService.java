package com.resource.server.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.resource.server.domain.ProductTags;
import com.resource.server.domain.*; // for static metamodels
import com.resource.server.repository.ProductTagsRepository;
import com.resource.server.service.dto.ProductTagsCriteria;
import com.resource.server.service.dto.ProductTagsDTO;
import com.resource.server.service.mapper.ProductTagsMapper;

/**
 * Service for executing complex queries for {@link ProductTags} entities in the database.
 * The main input is a {@link ProductTagsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductTagsDTO} or a {@link Page} of {@link ProductTagsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductTagsQueryService extends QueryService<ProductTags> {

    private final Logger log = LoggerFactory.getLogger(ProductTagsQueryService.class);

    private final ProductTagsRepository productTagsRepository;

    private final ProductTagsMapper productTagsMapper;

    public ProductTagsQueryService(ProductTagsRepository productTagsRepository, ProductTagsMapper productTagsMapper) {
        this.productTagsRepository = productTagsRepository;
        this.productTagsMapper = productTagsMapper;
    }

    /**
     * Return a {@link List} of {@link ProductTagsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductTagsDTO> findByCriteria(ProductTagsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductTags> specification = createSpecification(criteria);
        return productTagsMapper.toDto(productTagsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductTagsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductTagsDTO> findByCriteria(ProductTagsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductTags> specification = createSpecification(criteria);
        return productTagsRepository.findAll(specification, page)
            .map(productTagsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductTagsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductTags> specification = createSpecification(criteria);
        return productTagsRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductTagsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductTags> createSpecification(ProductTagsCriteria criteria) {
        Specification<ProductTags> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProductTags_.id));
            }
            if (criteria.getTagName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTagName(), ProductTags_.tagName));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(ProductTags_.product, JoinType.LEFT).get(Products_.id)));
            }
        }
        return specification;
    }
}
