package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.Countries} entity.
 */
public class CountriesDTO implements Serializable {

    private Long id;

    @NotNull
    private String countryName;

    @NotNull
    private String formalName;

    private String isoAplha3Code;

    private Integer isoNumericCode;

    private String countryType;

    private Long latestRecordedPopulation;

    @NotNull
    private String continent;

    @NotNull
    private String region;

    @NotNull
    private String subregion;

    private String border;

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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getFormalName() {
        return formalName;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }

    public String getIsoAplha3Code() {
        return isoAplha3Code;
    }

    public void setIsoAplha3Code(String isoAplha3Code) {
        this.isoAplha3Code = isoAplha3Code;
    }

    public Integer getIsoNumericCode() {
        return isoNumericCode;
    }

    public void setIsoNumericCode(Integer isoNumericCode) {
        this.isoNumericCode = isoNumericCode;
    }

    public String getCountryType() {
        return countryType;
    }

    public void setCountryType(String countryType) {
        this.countryType = countryType;
    }

    public Long getLatestRecordedPopulation() {
        return latestRecordedPopulation;
    }

    public void setLatestRecordedPopulation(Long latestRecordedPopulation) {
        this.latestRecordedPopulation = latestRecordedPopulation;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
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

        CountriesDTO countriesDTO = (CountriesDTO) o;
        if (countriesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countriesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CountriesDTO{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", formalName='" + getFormalName() + "'" +
            ", isoAplha3Code='" + getIsoAplha3Code() + "'" +
            ", isoNumericCode=" + getIsoNumericCode() +
            ", countryType='" + getCountryType() + "'" +
            ", latestRecordedPopulation=" + getLatestRecordedPopulation() +
            ", continent='" + getContinent() + "'" +
            ", region='" + getRegion() + "'" +
            ", subregion='" + getSubregion() + "'" +
            ", border='" + getBorder() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
