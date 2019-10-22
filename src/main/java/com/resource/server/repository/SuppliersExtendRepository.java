package com.resource.server.repository;

import com.resource.server.domain.Suppliers;

import java.util.Optional;

public interface SuppliersExtendRepository extends SuppliersRepository {
    Optional<Suppliers> findSuppliersByPrimaryContactPersonId(Long personId);
}
