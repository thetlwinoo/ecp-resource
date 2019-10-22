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
 * Criteria class for the {@link com.resource.server.domain.Culture} entity. This class is used
 * in {@link com.resource.server.web.rest.CultureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cultures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CultureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cultureCode;

    private StringFilter cultureName;

    public CultureCriteria(){
    }

    public CultureCriteria(CultureCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.cultureCode = other.cultureCode == null ? null : other.cultureCode.copy();
        this.cultureName = other.cultureName == null ? null : other.cultureName.copy();
    }

    @Override
    public CultureCriteria copy() {
        return new CultureCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCultureCode() {
        return cultureCode;
    }

    public void setCultureCode(StringFilter cultureCode) {
        this.cultureCode = cultureCode;
    }

    public StringFilter getCultureName() {
        return cultureName;
    }

    public void setCultureName(StringFilter cultureName) {
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
        final CultureCriteria that = (CultureCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cultureCode, that.cultureCode) &&
            Objects.equals(cultureName, that.cultureName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cultureCode,
        cultureName
        );
    }

    @Override
    public String toString() {
        return "CultureCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cultureCode != null ? "cultureCode=" + cultureCode + ", " : "") +
                (cultureName != null ? "cultureName=" + cultureName + ", " : "") +
            "}";
    }

}
