package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.PhoneNumberType} entity.
 */
public class PhoneNumberTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String phoneNumberTypeName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumberTypeName() {
        return phoneNumberTypeName;
    }

    public void setPhoneNumberTypeName(String phoneNumberTypeName) {
        this.phoneNumberTypeName = phoneNumberTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhoneNumberTypeDTO phoneNumberTypeDTO = (PhoneNumberTypeDTO) o;
        if (phoneNumberTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), phoneNumberTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PhoneNumberTypeDTO{" +
            "id=" + getId() +
            ", phoneNumberTypeName='" + getPhoneNumberTypeName() + "'" +
            "}";
    }
}
