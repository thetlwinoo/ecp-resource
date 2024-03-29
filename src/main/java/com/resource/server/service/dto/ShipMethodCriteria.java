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
 * Criteria class for the {@link com.resource.server.domain.ShipMethod} entity. This class is used
 * in {@link com.resource.server.web.rest.ShipMethodResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ship-methods?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ShipMethodCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter shipMethodName;

    public ShipMethodCriteria(){
    }

    public ShipMethodCriteria(ShipMethodCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.shipMethodName = other.shipMethodName == null ? null : other.shipMethodName.copy();
    }

    @Override
    public ShipMethodCriteria copy() {
        return new ShipMethodCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getShipMethodName() {
        return shipMethodName;
    }

    public void setShipMethodName(StringFilter shipMethodName) {
        this.shipMethodName = shipMethodName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ShipMethodCriteria that = (ShipMethodCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(shipMethodName, that.shipMethodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        shipMethodName
        );
    }

    @Override
    public String toString() {
        return "ShipMethodCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (shipMethodName != null ? "shipMethodName=" + shipMethodName + ", " : "") +
            "}";
    }

}
