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
 * Criteria class for the {@link com.resource.server.domain.PhoneNumberType} entity. This class is used
 * in {@link com.resource.server.web.rest.PhoneNumberTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /phone-number-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PhoneNumberTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter phoneNumberTypeName;

    public PhoneNumberTypeCriteria(){
    }

    public PhoneNumberTypeCriteria(PhoneNumberTypeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.phoneNumberTypeName = other.phoneNumberTypeName == null ? null : other.phoneNumberTypeName.copy();
    }

    @Override
    public PhoneNumberTypeCriteria copy() {
        return new PhoneNumberTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPhoneNumberTypeName() {
        return phoneNumberTypeName;
    }

    public void setPhoneNumberTypeName(StringFilter phoneNumberTypeName) {
        this.phoneNumberTypeName = phoneNumberTypeName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PhoneNumberTypeCriteria that = (PhoneNumberTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(phoneNumberTypeName, that.phoneNumberTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        phoneNumberTypeName
        );
    }

    @Override
    public String toString() {
        return "PhoneNumberTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (phoneNumberTypeName != null ? "phoneNumberTypeName=" + phoneNumberTypeName + ", " : "") +
            "}";
    }

}
