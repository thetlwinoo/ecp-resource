package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.PackageTypes} entity.
 */
public class PackageTypesDTO implements Serializable {

    private Long id;

    @NotNull
    private String packageTypeName;

    @NotNull
    private LocalDate validFrom;

    @NotNull
    private LocalDate validTo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageTypeName() {
        return packageTypeName;
    }

    public void setPackageTypeName(String packageTypeName) {
        this.packageTypeName = packageTypeName;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PackageTypesDTO packageTypesDTO = (PackageTypesDTO) o;
        if (packageTypesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), packageTypesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PackageTypesDTO{" +
            "id=" + getId() +
            ", packageTypeName='" + getPackageTypeName() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
