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
 * Criteria class for the {@link com.resource.server.domain.PackageTypes} entity. This class is used
 * in {@link com.resource.server.web.rest.PackageTypesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /package-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PackageTypesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter packageTypeName;

    private LocalDateFilter validFrom;

    private LocalDateFilter validTo;

    public PackageTypesCriteria(){
    }

    public PackageTypesCriteria(PackageTypesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.packageTypeName = other.packageTypeName == null ? null : other.packageTypeName.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
    }

    @Override
    public PackageTypesCriteria copy() {
        return new PackageTypesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPackageTypeName() {
        return packageTypeName;
    }

    public void setPackageTypeName(StringFilter packageTypeName) {
        this.packageTypeName = packageTypeName;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PackageTypesCriteria that = (PackageTypesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(packageTypeName, that.packageTypeName) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        packageTypeName,
        validFrom,
        validTo
        );
    }

    @Override
    public String toString() {
        return "PackageTypesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (packageTypeName != null ? "packageTypeName=" + packageTypeName + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
            "}";
    }

}
