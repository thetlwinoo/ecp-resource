package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.ContactType} entity.
 */
public class ContactTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String contactTypeName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactTypeName() {
        return contactTypeName;
    }

    public void setContactTypeName(String contactTypeName) {
        this.contactTypeName = contactTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContactTypeDTO contactTypeDTO = (ContactTypeDTO) o;
        if (contactTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactTypeDTO{" +
            "id=" + getId() +
            ", contactTypeName='" + getContactTypeName() + "'" +
            "}";
    }
}
