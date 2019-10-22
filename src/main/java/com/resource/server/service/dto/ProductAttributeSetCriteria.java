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
 * Criteria class for the {@link com.resource.server.domain.ProductAttributeSet} entity. This class is used
 * in {@link com.resource.server.web.rest.ProductAttributeSetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-attribute-sets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductAttributeSetCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productAttributeSetName;

    private LongFilter productOptionSetId;

    public ProductAttributeSetCriteria(){
    }

    public ProductAttributeSetCriteria(ProductAttributeSetCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.productAttributeSetName = other.productAttributeSetName == null ? null : other.productAttributeSetName.copy();
        this.productOptionSetId = other.productOptionSetId == null ? null : other.productOptionSetId.copy();
    }

    @Override
    public ProductAttributeSetCriteria copy() {
        return new ProductAttributeSetCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProductAttributeSetName() {
        return productAttributeSetName;
    }

    public void setProductAttributeSetName(StringFilter productAttributeSetName) {
        this.productAttributeSetName = productAttributeSetName;
    }

    public LongFilter getProductOptionSetId() {
        return productOptionSetId;
    }

    public void setProductOptionSetId(LongFilter productOptionSetId) {
        this.productOptionSetId = productOptionSetId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductAttributeSetCriteria that = (ProductAttributeSetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(productAttributeSetName, that.productAttributeSetName) &&
            Objects.equals(productOptionSetId, that.productOptionSetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productAttributeSetName,
        productOptionSetId
        );
    }

    @Override
    public String toString() {
        return "ProductAttributeSetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productAttributeSetName != null ? "productAttributeSetName=" + productAttributeSetName + ", " : "") +
                (productOptionSetId != null ? "productOptionSetId=" + productOptionSetId + ", " : "") +
            "}";
    }

}
