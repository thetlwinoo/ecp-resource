package com.resource.server.service;

import com.resource.server.domain.ShoppingCarts;

import java.security.Principal;

public interface ShoppingCartsExtendService {
    ShoppingCarts addToCart(Principal principal, Long id, Integer quantity);

    ShoppingCarts fetchCart(Principal principal);

    ShoppingCarts removeFromCart(Principal principal, Long id);

    ShoppingCarts reduceFromCart(Principal principal, Long id, Integer quantity);

    Boolean confirmCart(Principal principal, ShoppingCarts cart);

    void emptyCart(Principal principal);
}
