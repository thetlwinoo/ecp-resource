package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.ProductAttributeSet} entity.
 */
public class ProductAttributeSetDTO implements Serializable {

    private Long id;

    @NotNull
    private String productAttributeSetName;


    private Long productOptionSetId;

    private String productOptionSetProductOptionSetValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductAttributeSetName() {
        return productAttributeSetName;
    }

    public void setProductAttributeSetName(String productAttributeSetName) {
        this.productAttributeSetName = productAttributeSetName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductAttributeSetDTO productAttributeSetDTO = (ProductAttributeSetDTO) o;
        if (productAttributeSetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productAttributeSetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductAttributeSetDTO{" +
            "id=" + getId() +
            ", productAttributeSetName='" + getProductAttributeSetName() + "'" +
            ", productOptionSet=" + getProductOptionSetId() +
            ", productOptionSet='" + getProductOptionSetProductOptionSetValue() + "'" +
            "}";
    }
}
