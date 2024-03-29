package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.WishlistProductsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WishlistProducts} and its DTO {@link WishlistProductsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductsMapper.class, WishlistsMapper.class})
public interface WishlistProductsMapper extends EntityMapper<WishlistProductsDTO, WishlistProducts> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productProductName")
    @Mapping(source = "wishlist.id", target = "wishlistId")
    WishlistProductsDTO toDto(WishlistProducts wishlistProducts);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "wishlistId", target = "wishlist")
    WishlistProducts toEntity(WishlistProductsDTO wishlistProductsDTO);

    default WishlistProducts fromId(Long id) {
        if (id == null) {
            return null;
        }
        WishlistProducts wishlistProducts = new WishlistProducts();
        wishlistProducts.setId(id);
        return wishlistProducts;
    }
}
