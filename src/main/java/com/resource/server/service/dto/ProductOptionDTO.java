package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.ProductOption} entity.
 */
public class ProductOptionDTO implements Serializable {

    private Long id;

    @NotNull
    private String productOptionValue;


    private Long productOptionSetId;

    private String productOptionSetProductOptionSetValue;

    private Long supplierId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductOptionValue() {
        return productOptionValue;
    }

    public void setProductOptionValue(String productOptionValue) {
        this.productOptionValue = productOptionValue;
    }

    public Long getProductOptionSetId() {
        return productOptionSetId;
    }

    public void setProductOptionSetId(Long productOptionSetId) {
        this.productOptionSetId = productOptionSetId;
    }

    public String getProductOptionSetProductOptionSetValue() {
        return productOptionSetProductOptionSetValue;
    }

    public void setProductOptionSetProductOptionSetValue(String productOptionSetProductOptionSetValue) {
        this.productOptionSetProductOptionSetValue = productOptionSetProductOptionSetValue;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long suppliersId) {
        this.supplierId = suppliersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductOptionDTO productOptionDTO = (ProductOptionDTO) o;
        if (productOptionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productOptionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductOptionDTO{" +
            "id=" + getId() +
            ", productOptionValue='" + getProductOptionValue() + "'" +
            ", productOptionSet=" + getProductOptionSetId() +
            ", productOptionSet='" + getProductOptionSetProductOptionSetValue() + "'" +
            ", supplier=" + getSupplierId() +
            "}";
    }
}
