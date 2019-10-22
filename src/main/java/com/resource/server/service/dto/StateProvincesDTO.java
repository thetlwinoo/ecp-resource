package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.StateProvinces} entity.
 */
public class StateProvincesDTO implements Serializable {

    private Long id;

    @NotNull
    private String stateProvinceCode;

    @NotNull
    private String stateProvinceName;

    @NotNull
    private String salesTerritory;

    private String border;

    private Long latestRecordedPopulation;

    @NotNull
    private LocalDate validFrom;

    @NotNull
    private LocalDate validTo;


    private Long countryId;

    private String countryCountryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStateProvinceCode() {
        return stateProvinceCode;
    }

    public void setStateProvinceCode(String stateProvinceCode) {
        this.stateProvinceCode = stateProvinceCode;
    }

    public String getStateProvinceName() {
        return stateProvinceName;
    }

    public void setStateProvinceName(String stateProvinceName) {
        this.stateProvinceName = stateProvinceName;
    }

    public String getSalesTerritory() {
        return salesTerritory;
    }

    public void setSalesTerritory(String salesTerritory) {
        this.salesTerritory = salesTerritory;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public Long getLatestRecordedPopulation() {
        return latestRecordedPopulation;
    }

    public void setLatestRecordedPopulation(Long latestRecordedPopulation) {
        this.latestRecordedPopulation = latestRecordedPopulation;
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

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countriesId) {
        this.countryId = countriesId;
    }

    public String getCountryCountryName() {
        return countryCountryName;
    }

    public void setCountryCountryName(String countriesCountryName) {
        this.countryCountryName = countriesCountryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StateProvincesDTO stateProvincesDTO = (StateProvincesDTO) o;
        if (stateProvincesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stateProvincesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StateProvincesDTO{" +
            "id=" + getId() +
            ", stateProvinceCode='" + getStateProvinceCode() + "'" +
            ", stateProvinceName='" + getStateProvinceName() + "'" +
            ", salesTerritory='" + getSalesTerritory() + "'" +
            ", border='" + getBorder() + "'" +
            ", latestRecordedPopulation=" + getLatestRecordedPopulation() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", country=" + getCountryId() +
            ", country='" + getCountryCountryName() + "'" +
            "}";
    }
}
