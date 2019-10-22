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
 * Criteria class for the {@link com.resource.server.domain.ProductBrand} entity. This class is used
 * in {@link com.resource.server.web.rest.ProductBrandResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-brands?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductBrandCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productBrandName;

    public ProductBrandCriteria(){
    }

    public ProductBrandCriteria(ProductBrandCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.productBrandName = other.productBrandName == null ? null : other.productBrandName.copy();
    }

    @Override
    public ProductBrandCriteria copy() {
        return new ProductBrandCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(StringFilter productBrandName) {
        this.productBrandName = productBrandName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductBrandCriteria that = (ProductBrandCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(productBrandName, that.productBrandName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productBrandName
        );
    }

    @Override
    public String toString() {
        return "ProductBrandCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productBrandName != null ? "productBrandName=" + productBrandName + ", " : "") +
            "}";
    }

}
