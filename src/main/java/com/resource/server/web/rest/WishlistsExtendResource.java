package com.resource.server.web.rest;

import com.resource.server.domain.Wishlists;
import com.resource.server.service.WishlistsExtendService;
import com.resource.server.service.dto.ProductsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * WishlistsExtendResource controller
 */
@RestController
@RequestMapping("/api/wishlists-extend")
public class WishlistsExtendResource {

    private final Logger log = LoggerFactory.getLogger(WishlistsExtendResource.class);
    private final WishlistsExtendService wishlistsExtendService;

    public WishlistsExtendResource(WishlistsExtendService wishlistsExtendService) {
        this.wishlistsExtendService = wishlistsExtendService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addToWishlist(@RequestBody Long id, Principal principal) throws IOException {
        Wishlists wishlists = wishlistsExtendService.addToWishlist(principal, id);
        return new ResponseEntity<Wishlists>(wishlists, HttpStatus.OK);
    }

    @RequestMapping(value = "/fetch", method = RequestMethod.GET)
    public ResponseEntity fetchWishlist(Principal principal) {
        Wishlists wishlists = wishlistsExtendService.fetchWishlist(principal);
        return new ResponseEntity<Wishlists>(wishlists, HttpStatus.OK);
    }

    @RequestMapping(value = "/fetch/products", method = RequestMethod.GET)
    public ResponseEntity fetchWishlistProducts(Principal principal) {
        List<ProductsDTO> productsList = wishlistsExtendService.fetchWishlistProducts(principal);
        return new ResponseEntity<List<ProductsDTO>>(productsList, HttpStatus.OK);
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET, params = {"productId"})
    public ResponseEntity getAllOrders(@RequestParam("productId") Long productId, Principal principal) {
        Boolean isInWishlist = wishlistsExtendService.isInWishlist(principal, productId);
        return new ResponseEntity<Boolean>(isInWishlist, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE, params = "id")
    public ResponseEntity removeFromCart(@RequestParam("id") Long id, Principal principal) {
        Wishlists wishlists = wishlistsExtendService.removeFromWishlist(principal, id);
        return new ResponseEntity<Wishlists>(wishlists, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public ResponseEntity emptyCart(Principal principal) {
        wishlistsExtendService.emptyWishlist(principal);
        return new ResponseEntity(HttpStatus.OK);
    }

}
