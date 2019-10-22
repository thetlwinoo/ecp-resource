package com.resource.server.service.impl;

import com.resource.server.domain.People;
import com.resource.server.repository.PeopleExtendRepository;
import com.resource.server.repository.ProductOptionExtendRepository;
import com.resource.server.repository.SuppliersExtendRepository;
import com.resource.server.service.ProductOptionExtendService;
import com.resource.server.service.dto.ProductOptionDTO;
import com.resource.server.service.dto.SuppliersDTO;
import com.resource.server.service.mapper.ProductOptionMapper;
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
public class ProductOptionExtendServiceImpl implements ProductOptionExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductOptionExtendServiceImpl.class);
    private final PeopleExtendRepository peopleExtendRepository;
    private final SuppliersExtendRepository suppliersExtendRepository;
    private final SuppliersMapper suppliersMapper;
    private final ProductOptionExtendRepository productOptionExtendRepository;
    private final ProductOptionMapper productOptionMapper;

    public ProductOptionExtendServiceImpl(PeopleExtendRepository peopleExtendRepository, SuppliersExtendRepository suppliersExtendRepository, SuppliersMapper suppliersMapper, ProductOptionExtendRepository productOptionExtendRepository, ProductOptionMapper productOptionMapper) {
        this.peopleExtendRepository = peopleExtendRepository;
        this.suppliersExtendRepository = suppliersExtendRepository;
        this.suppliersMapper = suppliersMapper;
        this.productOptionExtendRepository = productOptionExtendRepository;
        this.productOptionMapper = productOptionMapper;
    }

    public List<ProductOptionDTO> getAllProductOptions(Long optionSetId, Principal principal) {
        log.debug("Request to get all ProductAttributes");
        People people = getUserFromPrinciple(principal);
        Optional<SuppliersDTO> suppliersDTOOptional = suppliersExtendRepository.findSuppliersByPrimaryContactPersonId(people.getId())
            .map(suppliersMapper::toDto);

        return productOptionExtendRepository.findAllByProductOptionSetIdAndSupplierId(optionSetId, suppliersDTOOptional.get().getId()).stream()
            .map(productOptionMapper::toDto)
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
