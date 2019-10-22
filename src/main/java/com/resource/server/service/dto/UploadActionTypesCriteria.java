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
 * Criteria class for the {@link com.resource.server.domain.UploadActionTypes} entity. This class is used
 * in {@link com.resource.server.web.rest.UploadActionTypesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /upload-action-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UploadActionTypesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter actionTypeName;

    public UploadActionTypesCriteria(){
    }

    public UploadActionTypesCriteria(UploadActionTypesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.actionTypeName = other.actionTypeName == null ? null : other.actionTypeName.copy();
    }

    @Override
    public UploadActionTypesCriteria copy() {
        return new UploadActionTypesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getActionTypeName() {
        return actionTypeName;
    }

    public void setActionTypeName(StringFilter actionTypeName) {
        this.actionTypeName = actionTypeName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UploadActionTypesCriteria that = (UploadActionTypesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(actionTypeName, that.actionTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        actionTypeName
        );
    }

    @Override
    public String toString() {
        return "UploadActionTypesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (actionTypeName != null ? "actionTypeName=" + actionTypeName + ", " : "") +
            "}";
    }

}
