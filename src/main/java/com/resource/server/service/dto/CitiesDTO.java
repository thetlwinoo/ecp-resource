package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.Cities} entity.
 */
public class CitiesDTO implements Serializable {

    private Long id;

    @NotNull
    private String cityName;

    private String location;

    private Long latestRecordedPopulation;

    @NotNull
    private LocalDate validFrom;

    @NotNull
    private LocalDate validTo;


    private Long stateProvinceId;

    private String stateProvinceStateProvinceName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Long getStateProvinceId() {
        return stateProvinceId;
    }

    public void setStateProvinceId(Long stateProvincesId) {
        this.stateProvinceId = stateProvincesId;
    }

    public String getStateProvinceStateProvinceName() {
        return stateProvinceStateProvinceName;
    }

    public void setStateProvinceStateProvinceName(String stateProvincesStateProvinceName) {
        this.stateProvinceStateProvinceName = stateProvincesStateProvinceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CitiesDTO citiesDTO = (CitiesDTO) o;
        if (citiesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), citiesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CitiesDTO{" +
            "id=" + getId() +
            ", cityName='" + getCityName() + "'" +
            ", location='" + getLocation() + "'" +
            ", latestRecordedPopulation=" + getLatestRecordedPopulation() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", stateProvince=" + getStateProvinceId() +
            ", stateProvince='" + getStateProvinceStateProvinceName() + "'" +
            "}";
    }
}
