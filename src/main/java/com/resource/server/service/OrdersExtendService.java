package com.resource.server.service;

import com.resource.server.domain.Orders;
import com.resource.server.service.dto.OrdersDTO;

import java.security.Principal;
import java.util.List;

public interface OrdersExtendService {
    Integer getAllOrdersCount(Principal principal);

    OrdersDTO postOrder(Principal principal, OrdersDTO ordersDTO);
}
