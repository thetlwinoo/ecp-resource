package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.ProductTags} entity.
 */
public class ProductTagsDTO implements Serializable {

    private Long id;

    @NotNull
    private String tagName;


    private Long productId;

    private String productProductName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductTagsDTO productTagsDTO = (ProductTagsDTO) o;
        if (productTagsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productTagsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductTagsDTO{" +
            "id=" + getId() +
            ", tagName='" + getTagName() + "'" +
            ", product=" + getProductId() +
            ", product='" + getProductProductName() + "'" +
            "}";
    }
}
