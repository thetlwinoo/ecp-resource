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
 * Criteria class for the {@link com.resource.server.domain.ProductSet} entity. This class is used
 * in {@link com.resource.server.web.rest.ProductSetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-sets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductSetCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productSetName;

    private IntegerFilter noOfPerson;

    private BooleanFilter isExclusive;

    public ProductSetCriteria(){
    }

    public ProductSetCriteria(ProductSetCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.productSetName = other.productSetName == null ? null : other.productSetName.copy();
        this.noOfPerson = other.noOfPerson == null ? null : other.noOfPerson.copy();
        this.isExclusive = other.isExclusive == null ? null : other.isExclusive.copy();
    }

    @Override
    public ProductSetCriteria copy() {
        return new ProductSetCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProductSetName() {
        return productSetName;
    }

    public void setProductSetName(StringFilter productSetName) {
        this.productSetName = productSetName;
    }

    public IntegerFilter getNoOfPerson() {
        return noOfPerson;
    }

    public void setNoOfPerson(IntegerFilter noOfPerson) {
        this.noOfPerson = noOfPerson;
    }

    public BooleanFilter getIsExclusive() {
        return isExclusive;
    }

    public void setIsExclusive(BooleanFilter isExclusive) {
        this.isExclusive = isExclusive;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductSetCriteria that = (ProductSetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(productSetName, that.productSetName) &&
            Objects.equals(noOfPerson, that.noOfPerson) &&
            Objects.equals(isExclusive, that.isExclusive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productSetName,
        noOfPerson,
        isExclusive
        );
    }

    @Override
    public String toString() {
        return "ProductSetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productSetName != null ? "productSetName=" + productSetName + ", " : "") +
                (noOfPerson != null ? "noOfPerson=" + noOfPerson + ", " : "") +
                (isExclusive != null ? "isExclusive=" + isExclusive + ", " : "") +
            "}";
    }

}
