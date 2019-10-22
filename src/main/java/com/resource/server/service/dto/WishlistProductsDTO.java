package com.resource.server.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.WishlistProducts} entity.
 */
public class WishlistProductsDTO implements Serializable {

    private Long id;


    private Long productId;

    private String productProductName;

    private Long wishlistId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productsId) {
        this.productId = productsId;
    }

    public String getProductProductName() {
        return productProductName;
    }

    public void setProductProductName(String productsProductName) {
        this.productProductName = productsProductName;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistsId) {
        this.wishlistId = wishlistsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WishlistProductsDTO wishlistProductsDTO = (WishlistProductsDTO) o;
        if (wishlistProductsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wishlistProductsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WishlistProductsDTO{" +
            "id=" + getId() +
            ", product=" + getProductId() +
            ", product='" + getProductProductName() + "'" +
            ", wishlist=" + getWishlistId() +
            "}";
    }
}
