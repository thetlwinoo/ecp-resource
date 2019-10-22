package com.resource.server.repository;

import com.resource.server.domain.Orders;

import java.util.List;

public interface OrdersExtendRepository extends OrdersRepository {
    List<Orders> findAllByCustomerIdOrderByLastEditedWhenDesc(Long id);
    Integer countAllByCustomerId(Long id);
}
