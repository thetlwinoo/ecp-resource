package com.resource.server.service.impl;

import com.resource.server.domain.People;
import com.resource.server.repository.PeopleExtendRepository;
import com.resource.server.repository.ProductAttributeExtendRepository;
import com.resource.server.repository.SuppliersExtendRepository;
import com.resource.server.service.ProductAttributeExtendService;
import com.resource.server.service.dto.ProductAttributeDTO;
import com.resource.server.service.dto.SuppliersDTO;
import com.resource.server.service.mapper.ProductAttributeMapper;
import com.resource.server.service.mapper.SuppliersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductAttributeExtendServiceImpl implements ProductAttributeExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductAttributeExtendServiceImpl.class);
    private final ProductAttributeExtendRepository productAttributeExtendRepository;
    private final ProductAttributeMapper productAttributeMapper;
    private final PeopleExtendRepository peopleExtendRepository;
    private final SuppliersExtendRepository suppliersExtendRepository;
    private final SuppliersMapper suppliersMapper;

    public ProductAttributeExtendServiceImpl(ProductAttributeExtendRepository productAttributeExtendRepository, ProductAttributeMapper productAttributeMapper, PeopleExtendRepository peopleExtendRepository, SuppliersExtendRepository suppliersExtendRepository, SuppliersMapper suppliersMapper) {
        this.productAttributeExtendRepository = productAttributeExtendRepository;
        this.productAttributeMapper = productAttributeMapper;
        this.peopleExtendRepository = peopleExtendRepository;
        this.suppliersExtendRepository = suppliersExtendRepository;
        this.suppliersMapper = suppliersMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductAttributeDTO> getAllProductAttributes(Long attributeSetId, Principal principal) {
        log.debug("Request to get all ProductAttributes");
        People people = getUserFromPrinciple(principal);
        Optional<SuppliersDTO> suppliersDTOOptional = suppliersExtendRepository.findSuppliersByPrimaryContactPersonId(people.getId())
            .map(suppliersMapper::toDto);

        return productAttributeExtendRepository.findAllByProductAttributeSetIdAndSupplierId(attributeSetId, suppliersDTOOptional.get().getId()).stream()
            .map(productAttributeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    private People getUserFromPrinciple(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }

        Optional<People> people = peopleExtendRepository.findPeopleByLogonName(principal.getName());
        if (!people.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        return people.get();
    }
}
