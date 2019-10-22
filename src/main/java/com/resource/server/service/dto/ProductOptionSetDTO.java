package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.ProductOptionSet} entity.
 */
public class ProductOptionSetDTO implements Serializable {

    private Long id;

    @NotNull
    private String productOptionSetValue;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductOptionSetValue() {
        return productOptionSetValue;
    }

    public void setProductOptionSetValue(String productOptionSetValue) {
        this.productOptionSetValue = productOptionSetValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductOptionSetDTO productOptionSetDTO = (ProductOptionSetDTO) o;
        if (productOptionSetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productOptionSetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductOptionSetDTO{" +
            "id=" + getId() +
            ", productOptionSetValue='" + getProductOptionSetValue() + "'" +
            "}";
    }
}
