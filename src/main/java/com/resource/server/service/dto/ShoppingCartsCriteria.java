package com.resource.server.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.resource.server.domain.ShoppingCarts} entity. This class is used
 * in {@link com.resource.server.web.rest.ShoppingCartsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /shopping-carts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ShoppingCartsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter totalPrice;

    private FloatFilter totalCargoPrice;

    private StringFilter lastEditedBy;

    private LocalDateFilter lastEditedWhen;

    private LongFilter specialDealsId;

    private LongFilter cartUserId;

    private LongFilter cartItemListId;

    private LongFilter customerId;

    public ShoppingCartsCriteria(){
    }

    public ShoppingCartsCriteria(ShoppingCartsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.totalPrice = other.totalPrice == null ? null : other.totalPrice.copy();
        this.totalCargoPrice = other.totalCargoPrice == null ? null : other.totalCargoPrice.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.specialDealsId = other.specialDealsId == null ? null : other.specialDealsId.copy();
        this.cartUserId = other.cartUserId == null ? null : other.cartUserId.copy();
        this.cartItemListId = other.cartItemListId == null ? null : other.cartItemListId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public ShoppingCartsCriteria copy() {
        return new ShoppingCartsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public FloatFilter getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(FloatFilter totalPrice) {
        this.totalPrice = totalPrice;
    }

    public FloatFilter getTotalCargoPrice() {
        return totalCargoPrice;
    }

    public void setTotalCargoPrice(FloatFilter totalCargoPrice) {
        this.totalCargoPrice = totalCargoPrice;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public LocalDateFilter getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(LocalDateFilter lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public LongFilter getSpecialDealsId() {
        return specialDealsId;
    }

    public void setSpecialDealsId(LongFilter specialDealsId) {
        this.specialDealsId = specialDealsId;
    }

    public LongFilter getCartUserId() {
        return cartUserId;
    }

    public void setCartUserId(LongFilter cartUserId) {
        this.cartUserId = cartUserId;
    }

    public LongFilter getCartItemListId() {
        return cartItemListId;
    }

    public void setCartItemListId(LongFilter cartItemListId) {
        this.cartItemListId = cartItemListId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ShoppingCartsCriteria that = (ShoppingCartsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(totalPrice, that.totalPrice) &&
            Objects.equals(totalCargoPrice, that.totalCargoPrice) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(specialDealsId, that.specialDealsId) &&
            Objects.equals(cartUserId, that.cartUserId) &&
            Objects.equals(cartItemListId, that.cartItemListId) &&
            Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        totalPrice,
        totalCargoPrice,
        lastEditedBy,
        lastEditedWhen,
        specialDealsId,
        cartUserId,
        cartItemListId,
        customerId
        );
    }

    @Override
    public String toString() {
        return "ShoppingCartsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (totalPrice != null ? "totalPrice=" + totalPrice + ", " : "") +
                (totalCargoPrice != null ? "totalCargoPrice=" + totalCargoPrice + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (specialDealsId != null ? "specialDealsId=" + specialDealsId + ", " : "") +
                (cartUserId != null ? "cartUserId=" + cartUserId + ", " : "") +
                (cartItemListId != null ? "cartItemListId=" + cartItemListId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }

}
