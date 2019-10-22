package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.ProductAttribute} entity.
 */
public class ProductAttributeDTO implements Serializable {

    private Long id;

    @NotNull
    private String productAttributeValue;


    private Long productAttributeSetId;

    private String productAttributeSetProductAttributeSetName;

    private Long supplierId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductAttributeValue() {
        return productAttributeValue;
    }

    public void setProductAttributeValue(String productAttributeValue) {
        this.productAttributeValue = productAttributeValue;
    }

    public Long getProductAttributeSetId() {
        return productAttributeSetId;
    }

    public void setProductAttributeSetId(Long productAttributeSetId) {
        this.productAttributeSetId = productAttributeSetId;
    }

    public String getProductAttributeSetProductAttributeSetName() {
        return productAttributeSetProductAttributeSetName;
    }

    public void setProductAttributeSetProductAttributeSetName(String productAttributeSetProductAttributeSetName) {
        this.productAttributeSetProductAttributeSetName = productAttributeSetProductAttributeSetName;
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

        ProductAttributeDTO productAttributeDTO = (ProductAttributeDTO) o;
        if (productAttributeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productAttributeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductAttributeDTO{" +
            "id=" + getId() +
            ", productAttributeValue='" + getProductAttributeValue() + "'" +
            ", productAttributeSet=" + getProductAttributeSetId() +
            ", productAttributeSet='" + getProductAttributeSetProductAttributeSetName() + "'" +
            ", supplier=" + getSupplierId() +
            "}";
    }
}
