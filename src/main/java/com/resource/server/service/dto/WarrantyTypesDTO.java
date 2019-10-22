package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.WarrantyTypes} entity.
 */
public class WarrantyTypesDTO implements Serializable {

    private Long id;

    @NotNull
    private String warrantyTypeName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWarrantyTypeName() {
        return warrantyTypeName;
    }

    public void setWarrantyTypeName(String warrantyTypeName) {
        this.warrantyTypeName = warrantyTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WarrantyTypesDTO warrantyTypesDTO = (WarrantyTypesDTO) o;
        if (warrantyTypesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), warrantyTypesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WarrantyTypesDTO{" +
            "id=" + getId() +
            ", warrantyTypeName='" + getWarrantyTypeName() + "'" +
            "}";
    }
}
