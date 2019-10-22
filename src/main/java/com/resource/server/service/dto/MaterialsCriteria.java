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
 * Criteria class for the {@link com.resource.server.domain.Materials} entity. This class is used
 * in {@link com.resource.server.web.rest.MaterialsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /materials?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MaterialsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter materialName;

    public MaterialsCriteria(){
    }

    public MaterialsCriteria(MaterialsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.materialName = other.materialName == null ? null : other.materialName.copy();
    }

    @Override
    public MaterialsCriteria copy() {
        return new MaterialsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMaterialName() {
        return materialName;
    }

    public void setMaterialName(StringFilter materialName) {
        this.materialName = materialName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MaterialsCriteria that = (MaterialsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(materialName, that.materialName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        materialName
        );
    }

    @Override
    public String toString() {
        return "MaterialsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (materialName != null ? "materialName=" + materialName + ", " : "") +
            "}";
    }

}
