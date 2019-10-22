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
 * Criteria class for the {@link com.resource.server.domain.ProductOptionSet} entity. This class is used
 * in {@link com.resource.server.web.rest.ProductOptionSetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-option-sets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductOptionSetCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productOptionSetValue;

    public ProductOptionSetCriteria(){
    }

    public ProductOptionSetCriteria(ProductOptionSetCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.productOptionSetValue = other.productOptionSetValue == null ? null : other.productOptionSetValue.copy();
    }

    @Override
    public ProductOptionSetCriteria copy() {
        return new ProductOptionSetCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProductOptionSetValue() {
        return productOptionSetValue;
    }

    public void setProductOptionSetValue(StringFilter productOptionSetValue) {
        this.productOptionSetValue = productOptionSetValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductOptionSetCriteria that = (ProductOptionSetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(productOptionSetValue, that.productOptionSetValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productOptionSetValue
        );
    }

    @Override
    public String toString() {
        return "ProductOptionSetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productOptionSetValue != null ? "productOptionSetValue=" + productOptionSetValue + ", " : "") +
            "}";
    }

}
