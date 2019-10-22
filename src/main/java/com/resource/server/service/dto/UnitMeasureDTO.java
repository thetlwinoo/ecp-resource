package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.UnitMeasure} entity.
 */
public class UnitMeasureDTO implements Serializable {

    private Long id;

    @NotNull
    private String unitMeasureCode;

    @NotNull
    private String unitMeasureName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitMeasureCode() {
        return unitMeasureCode;
    }

    public void setUnitMeasureCode(String unitMeasureCode) {
        this.unitMeasureCode = unitMeasureCode;
    }

    public String getUnitMeasureName() {
        return unitMeasureName;
    }

    public void setUnitMeasureName(String unitMeasureName) {
        this.unitMeasureName = unitMeasureName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnitMeasureDTO unitMeasureDTO = (UnitMeasureDTO) o;
        if (unitMeasureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unitMeasureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UnitMeasureDTO{" +
            "id=" + getId() +
            ", unitMeasureCode='" + getUnitMeasureCode() + "'" +
            ", unitMeasureName='" + getUnitMeasureName() + "'" +
            "}";
    }
}
