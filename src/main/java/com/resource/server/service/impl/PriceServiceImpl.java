package com.resource.server.service.impl;

import com.resource.server.domain.ShoppingCartItems;
import com.resource.server.domain.ShoppingCarts;
import com.resource.server.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;

@Service
@Transactional
public class PriceServiceImpl implements PriceService {

    private final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);

    public ShoppingCarts calculatePrice(ShoppingCarts cart) {

        Float totalPrice = 0F;
        Float totalCargoPrice = 0F;

        for (ShoppingCartItems i : cart.getCartItemLists()) {
            System.out.println("amount " + i.getQuantity());
            totalPrice = totalPrice + (((i.getStockItem().getUnitPrice() + i.getStockItem().getRecommendedRetailPrice()) * i.getQuantity()));
            totalCargoPrice = totalCargoPrice + (i.getStockItem().getRecommendedRetailPrice() * i.getQuantity());
        }

        //Applying discount percent if exists
        if (cart.getSpecialDeals() != null) {
            totalPrice = totalPrice - ((totalPrice * cart.getSpecialDeals().getDiscountPercentage()) / 100);
        }

        cart.setTotalPrice(roundTwoDecimals(totalPrice));
        cart.setTotalCargoPrice(roundTwoDecimals(totalCargoPrice));
        return cart;
    }

    private float roundTwoDecimals(float d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Float.valueOf(twoDForm.format(d));
    }
}
