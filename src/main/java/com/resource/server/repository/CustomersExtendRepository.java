package com.resource.server.repository;

import com.resource.server.domain.Customers;

import java.util.List;

public interface CustomersExtendRepository extends CustomersRepository {
    Customers findCustomersByPersonId(Long personId);
}
