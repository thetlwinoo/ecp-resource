package com.resource.server.repository;

import com.resource.server.domain.Addresses;

import java.util.List;

public interface AddressesExtendRepository extends AddressesRepository {
    List<Addresses> findAllByPersonId(Long id);
}
