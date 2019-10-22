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

/**
 * Criteria class for the {@link com.resource.server.domain.UnitMeasure} entity. This class is used
 * in {@link com.resource.server.web.rest.UnitMeasureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /unit-measures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UnitMeasureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter unitMeasureCode;

    private StringFilter unitMeasureName;

    public UnitMeasureCriteria(){
    }

    public UnitMeasureCriteria(UnitMeasureCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.unitMeasureCode = other.unitMeasureCode == null ? null : other.unitMeasureCode.copy();
        this.unitMeasureName = other.unitMeasureName == null ? null : other.unitMeasureName.copy();
    }

    @Override
    public UnitMeasureCriteria copy() {
        return new UnitMeasureCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUnitMeasureCode() {
        return unitMeasureCode;
    }

    public void setUnitMeasureCode(StringFilter unitMeasureCode) {
        this.unitMeasureCode = unitMeasureCode;
    }

    public StringFilter getUnitMeasureName() {
        return unitMeasureName;
    }

    public void setUnitMeasureName(StringFilter unitMeasureName) {
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
        final UnitMeasureCriteria that = (UnitMeasureCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(unitMeasureCode, that.unitMeasureCode) &&
            Objects.equals(unitMeasureName, that.unitMeasureName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        unitMeasureCode,
        unitMeasureName
        );
    }

    @Override
    public String toString() {
        return "UnitMeasureCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (unitMeasureCode != null ? "unitMeasureCode=" + unitMeasureCode + ", " : "") +
                (unitMeasureName != null ? "unitMeasureName=" + unitMeasureName + ", " : "") +
            "}";
    }

}
