package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.ShoppingCartsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShoppingCarts} and its DTO {@link ShoppingCartsDTO}.
 */
@Mapper(componentModel = "spring", uses = {SpecialDealsMapper.class, PeopleMapper.class, CustomersMapper.class})
public interface ShoppingCartsMapper extends EntityMapper<ShoppingCartsDTO, ShoppingCarts> {

    @Mapping(source = "specialDeals.id", target = "specialDealsId")
    @Mapping(source = "cartUser.id", target = "cartUserId")
    @Mapping(source = "customer.id", target = "customerId")
    ShoppingCartsDTO toDto(ShoppingCarts shoppingCarts);

    @Mapping(source = "specialDealsId", target = "specialDeals")
    @Mapping(source = "cartUserId", target = "cartUser")
    @Mapping(target = "cartItemLists", ignore = true)
    @Mapping(target = "removeCartItemList", ignore = true)
    @Mapping(source = "customerId", target = "customer")
    ShoppingCarts toEntity(ShoppingCartsDTO shoppingCartsDTO);

    default ShoppingCarts fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShoppingCarts shoppingCarts = new ShoppingCarts();
        shoppingCarts.setId(id);
        return shoppingCarts;
    }
}
