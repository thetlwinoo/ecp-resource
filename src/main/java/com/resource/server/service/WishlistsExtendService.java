package com.resource.server.service;

import com.resource.server.domain.Wishlists;
import com.resource.server.service.dto.ProductsDTO;

import java.security.Principal;
import java.util.List;

public interface WishlistsExtendService {
    Wishlists addToWishlist(Principal principal, Long id);

    Wishlists fetchWishlist(Principal principal);

    List<ProductsDTO> fetchWishlistProducts(Principal principal);

    Wishlists removeFromWishlist(Principal principal, Long id);

    void emptyWishlist(Principal principal);

    Boolean isInWishlist(Principal principal, Long id);
}
