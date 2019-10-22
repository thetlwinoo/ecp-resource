package com.resource.server.service.impl;

import com.resource.server.domain.People;
import com.resource.server.repository.PeopleExtendRepository;
import com.resource.server.repository.SuppliersExtendRepository;
import com.resource.server.service.SuppliersExtendService;
import com.resource.server.service.dto.SuppliersDTO;
import com.resource.server.service.mapper.SuppliersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
@Transactional
public class SuppliersExtendServiceImpl implements SuppliersExtendService {

    private final Logger log = LoggerFactory.getLogger(SuppliersExtendServiceImpl.class);
    private final PeopleExtendRepository peopleExtendRepository;
    private final SuppliersExtendRepository suppliersExtendRepository;
    private final SuppliersMapper suppliersMapper;


    public SuppliersExtendServiceImpl(PeopleExtendRepository peopleExtendRepository, SuppliersExtendRepository suppliersExtendRepository, SuppliersMapper suppliersMapper) {
        this.peopleExtendRepository = peopleExtendRepository;
        this.suppliersExtendRepository = suppliersExtendRepository;
        this.suppliersMapper = suppliersMapper;
    }

    public Optional<SuppliersDTO> getSupplierByPrincipal(Principal principal) {
        People people = getUserFromPrinciple(principal);

        return suppliersExtendRepository.findSuppliersByPrimaryContactPersonId(people.getId())
            .map(suppliersMapper::toDto);
    }

    private People getUserFromPrinciple(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }

        String username = principal.getName();
        Optional<People> people = peopleExtendRepository.findPeopleByLogonName(username);
        if (!people.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        return people.get();
    }
}
