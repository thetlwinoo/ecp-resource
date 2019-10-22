package com.resource.server.service.impl;

import com.resource.server.service.PaymentMethodsService;
import com.resource.server.domain.PaymentMethods;
import com.resource.server.repository.PaymentMethodsRepository;
import com.resource.server.service.dto.PaymentMethodsDTO;
import com.resource.server.service.mapper.PaymentMethodsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PaymentMethods}.
 */
@Service
@Transactional
public class PaymentMethodsServiceImpl implements PaymentMethodsService {

    private final Logger log = LoggerFactory.getLogger(PaymentMethodsServiceImpl.class);

    private final PaymentMethodsRepository paymentMethodsRepository;

    private final PaymentMethodsMapper paymentMethodsMapper;

    public PaymentMethodsServiceImpl(PaymentMethodsRepository paymentMethodsRepository, PaymentMethodsMapper paymentMethodsMapper) {
        this.paymentMethodsRepository = paymentMethodsRepository;
        this.paymentMethodsMapper = paymentMethodsMapper;
    }

    /**
     * Save a paymentMethods.
     *
     * @param paymentMethodsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PaymentMethodsDTO save(PaymentMethodsDTO paymentMethodsDTO) {
        log.debug("Request to save PaymentMethods : {}", paymentMethodsDTO);
        PaymentMethods paymentMethods = paymentMethodsMapper.toEntity(paymentMethodsDTO);
        paymentMethods = paymentMethodsRepository.save(paymentMethods);
        return paymentMethodsMapper.toDto(paymentMethods);
    }

    /**
     * Get all the paymentMethods.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodsDTO> findAll() {
        log.debug("Request to get all PaymentMethods");
        return paymentMethodsRepository.findAll().stream()
            .map(paymentMethodsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one paymentMethods by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentMethodsDTO> findOne(Long id) {
        log.debug("Request to get PaymentMethods : {}", id);
        return paymentMethodsRepository.findById(id)
            .map(paymentMethodsMapper::toDto);
    }

    /**
     * Delete the paymentMethods by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentMethods : {}", id);
        paymentMethodsRepository.deleteById(id);
    }
}
