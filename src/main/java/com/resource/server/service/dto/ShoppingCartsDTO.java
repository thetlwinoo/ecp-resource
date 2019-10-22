package com.resource.server.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.ShoppingCarts} entity.
 */
public class ShoppingCartsDTO implements Serializable {

    private Long id;

    private Float totalPrice;

    private Float totalCargoPrice;

    private String lastEditedBy;

    private LocalDate lastEditedWhen;


    private Long specialDealsId;

    private Long cartUserId;

    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Float getTotalCargoPrice() {
        return totalCargoPrice;
    }

    public void setTotalCargoPrice(Float totalCargoPrice) {
        this.totalCargoPrice = totalCargoPrice;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public LocalDate getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(LocalDate lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Long getSpecialDealsId() {
        return specialDealsId;
    }

    public void setSpecialDealsId(Long specialDealsId) {
        this.specialDealsId = specialDealsId;
    }

    public Long getCartUserId() {
        return cartUserId;
    }

    public void setCartUserId(Long peopleId) {
        this.cartUserId = peopleId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customersId) {
        this.customerId = customersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShoppingCartsDTO shoppingCartsDTO = (ShoppingCartsDTO) o;
        if (shoppingCartsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shoppingCartsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShoppingCartsDTO{" +
            "id=" + getId() +
            ", totalPrice=" + getTotalPrice() +
            ", totalCargoPrice=" + getTotalCargoPrice() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", specialDeals=" + getSpecialDealsId() +
            ", cartUser=" + getCartUserId() +
            ", customer=" + getCustomerId() +
            "}";
    }
}
