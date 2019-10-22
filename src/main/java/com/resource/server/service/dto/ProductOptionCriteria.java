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
 * Criteria class for the {@link com.resource.server.domain.ProductOption} entity. This class is used
 * in {@link com.resource.server.web.rest.ProductOptionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-options?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductOptionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productOptionValue;

    private LongFilter productOptionSetId;

    private LongFilter supplierId;

    public ProductOptionCriteria(){
    }

    public ProductOptionCriteria(ProductOptionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.productOptionValue = other.productOptionValue == null ? null : other.productOptionValue.copy();
        this.productOptionSetId = other.productOptionSetId == null ? null : other.productOptionSetId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
    }

    @Override
    public ProductOptionCriteria copy() {
        return new ProductOptionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProductOptionValue() {
        return productOptionValue;
    }

    public void setProductOptionValue(StringFilter productOptionValue) {
        this.productOptionValue = productOptionValue;
    }

    public LongFilter getProductOptionSetId() {
        return productOptionSetId;
    }

    public void setProductOptionSetId(LongFilter productOptionSetId) {
        this.productOptionSetId = productOptionSetId;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductOptionCriteria that = (ProductOptionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(productOptionValue, that.productOptionValue) &&
            Objects.equals(productOptionSetId, that.productOptionSetId) &&
            Objects.equals(supplierId, that.supplierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productOptionValue,
        productOptionSetId,
        supplierId
        );
    }

    @Override
    public String toString() {
        return "ProductOptionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productOptionValue != null ? "productOptionValue=" + productOptionValue + ", " : "") +
                (productOptionSetId != null ? "productOptionSetId=" + productOptionSetId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
            "}";
    }

}
