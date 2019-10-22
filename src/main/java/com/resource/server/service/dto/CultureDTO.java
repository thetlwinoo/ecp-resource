package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.Culture} entity.
 */
public class CultureDTO implements Serializable {

    private Long id;

    @NotNull
    private String cultureCode;

    @NotNull
    private String cultureName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCultureCode() {
        return cultureCode;
    }

    public void setCultureCode(String cultureCode) {
        this.cultureCode = cultureCode;
    }

    public String getCultureName() {
        return cultureName;
    }

    public void setCultureName(String cultureName) {
        this.cultureName = cultureName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CultureDTO cultureDTO = (CultureDTO) o;
        if (cultureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cultureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CultureDTO{" +
            "id=" + getId() +
            ", cultureCode='" + getCultureCode() + "'" +
            ", cultureName='" + getCultureName() + "'" +
            "}";
    }
}
