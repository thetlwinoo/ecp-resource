package com.resource.server.service;

import com.resource.server.domain.ShoppingCarts;

public interface PriceService {
    ShoppingCarts calculatePrice(ShoppingCarts cart);
}
