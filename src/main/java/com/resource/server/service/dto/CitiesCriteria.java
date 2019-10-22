package com.resource.server.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.resource.server.domain.Cities} entity. This class is used
 * in {@link com.resource.server.web.rest.CitiesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CitiesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cityName;

    private StringFilter location;

    private LongFilter latestRecordedPopulation;

    private LocalDateFilter validFrom;

    private LocalDateFilter validTo;

    private LongFilter stateProvinceId;

    public CitiesCriteria(){
    }

    public CitiesCriteria(CitiesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.cityName = other.cityName == null ? null : other.cityName.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.latestRecordedPopulation = other.latestRecordedPopulation == null ? null : other.latestRecordedPopulation.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
        this.stateProvinceId = other.stateProvinceId == null ? null : other.stateProvinceId.copy();
    }

    @Override
    public CitiesCriteria copy() {
        return new CitiesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCityName() {
        return cityName;
    }

    public void setCityName(StringFilter cityName) {
        this.cityName = cityName;
    }

    public StringFilter getLocation() {
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public LongFilter getLatestRecordedPopulation() {
        return latestRecordedPopulation;
    }

    public void setLatestRecordedPopulation(LongFilter latestRecordedPopulation) {
        this.latestRecordedPopulation = latestRecordedPopulation;
    }

    public LocalDateFilter getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateFilter validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateFilter getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateFilter validTo) {
        this.validTo = validTo;
    }

    public LongFilter getStateProvinceId() {
        return stateProvinceId;
    }

    public void setStateProvinceId(LongFilter stateProvinceId) {
        this.stateProvinceId = stateProvinceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CitiesCriteria that = (CitiesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cityName, that.cityName) &&
            Objects.equals(location, that.location) &&
            Objects.equals(latestRecordedPopulation, that.latestRecordedPopulation) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo) &&
            Objects.equals(stateProvinceId, that.stateProvinceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cityName,
        location,
        latestRecordedPopulation,
        validFrom,
        validTo,
        stateProvinceId
        );
    }

    @Override
    public String toString() {
        return "CitiesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cityName != null ? "cityName=" + cityName + ", " : "") +
                (location != null ? "location=" + location + ", " : "") +
                (latestRecordedPopulation != null ? "latestRecordedPopulation=" + latestRecordedPopulation + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
                (stateProvinceId != null ? "stateProvinceId=" + stateProvinceId + ", " : "") +
            "}";
    }

}
