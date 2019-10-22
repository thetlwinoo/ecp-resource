package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.AddressTypes} entity.
 */
public class AddressTypesDTO implements Serializable {

    private Long id;

    @NotNull
    private String addressTypeName;

    private String refer;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressTypeName() {
        return addressTypeName;
    }

    public void setAddressTypeName(String addressTypeName) {
        this.addressTypeName = addressTypeName;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressTypesDTO addressTypesDTO = (AddressTypesDTO) o;
        if (addressTypesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressTypesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressTypesDTO{" +
            "id=" + getId() +
            ", addressTypeName='" + getAddressTypeName() + "'" +
            ", refer='" + getRefer() + "'" +
            "}";
    }
}
