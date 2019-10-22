package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.Materials} entity.
 */
public class MaterialsDTO implements Serializable {

    private Long id;

    @NotNull
    private String materialName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MaterialsDTO materialsDTO = (MaterialsDTO) o;
        if (materialsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), materialsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MaterialsDTO{" +
            "id=" + getId() +
            ", materialName='" + getMaterialName() + "'" +
            "}";
    }
}
