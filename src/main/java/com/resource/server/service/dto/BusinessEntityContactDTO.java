package com.resource.server.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.BusinessEntityContact} entity.
 */
public class BusinessEntityContactDTO implements Serializable {

    private Long id;


    private Long personId;

    private Long contactTypeId;

    private String contactTypeContactTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long peopleId) {
        this.personId = peopleId;
    }

    public Long getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(Long contactTypeId) {
        this.contactTypeId = contactTypeId;
    }

    public String getContactTypeContactTypeName() {
        return contactTypeContactTypeName;
    }

    public void setContactTypeContactTypeName(String contactTypeContactTypeName) {
        this.contactTypeContactTypeName = contactTypeContactTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BusinessEntityContactDTO businessEntityContactDTO = (BusinessEntityContactDTO) o;
        if (businessEntityContactDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessEntityContactDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessEntityContactDTO{" +
            "id=" + getId() +
            ", person=" + getPersonId() +
            ", contactType=" + getContactTypeId() +
            ", contactType='" + getContactTypeContactTypeName() + "'" +
            "}";
    }
}
