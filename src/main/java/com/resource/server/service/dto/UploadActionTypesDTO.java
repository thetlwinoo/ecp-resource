package com.resource.server.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.UploadActionTypes} entity.
 */
public class UploadActionTypesDTO implements Serializable {

    private Long id;

    private String actionTypeName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActionTypeName() {
        return actionTypeName;
    }

    public void setActionTypeName(String actionTypeName) {
        this.actionTypeName = actionTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UploadActionTypesDTO uploadActionTypesDTO = (UploadActionTypesDTO) o;
        if (uploadActionTypesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uploadActionTypesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UploadActionTypesDTO{" +
            "id=" + getId() +
            ", actionTypeName='" + getActionTypeName() + "'" +
            "}";
    }
}
