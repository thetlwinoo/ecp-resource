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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.resource.server.domain.Products} entity. This class is used
 * in {@link com.resource.server.web.rest.ProductsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productName;

    private StringFilter handle;

    private StringFilter productNumber;

    private IntegerFilter sellCount;

    private BooleanFilter activeInd;

    private StringFilter lastEditedBy;

    private LocalDateFilter lastEditedWhen;

    private LongFilter documentId;

    private LongFilter stockItemListId;

    private LongFilter supplierId;

    private LongFilter productCategoryId;

    private LongFilter productBrandId;

    public ProductsCriteria(){
    }

    public ProductsCriteria(ProductsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.productName = other.productName == null ? null : other.productName.copy();
        this.handle = other.handle == null ? null : other.handle.copy();
        this.productNumber = other.productNumber == null ? null : other.productNumber.copy();
        this.sellCount = other.sellCount == null ? null : other.sellCount.copy();
        this.activeInd = other.activeInd == null ? null : other.activeInd.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.documentId = other.documentId == null ? null : other.documentId.copy();
        this.stockItemListId = other.stockItemListId == null ? null : other.stockItemListId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
        this.productBrandId = other.productBrandId == null ? null : other.productBrandId.copy();
    }

    @Override
    public ProductsCriteria copy() {
        return new ProductsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProductName() {
        return productName;
    }

    public void setProductName(StringFilter productName) {
        this.productName = productName;
    }

    public StringFilter getHandle() {
        return handle;
    }

    public void setHandle(StringFilter handle) {
        this.handle = handle;
    }

    public StringFilter getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(StringFilter productNumber) {
        this.productNumber = productNumber;
    }

    public IntegerFilter getSellCount() {
        return sellCount;
    }

    public void setSellCount(IntegerFilter sellCount) {
        this.sellCount = sellCount;
    }

    public BooleanFilter getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(BooleanFilter activeInd) {
        this.activeInd = activeInd;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public LocalDateFilter getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(LocalDateFilter lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public LongFilter getDocumentId() {
        return documentId;
    }

    public void setDocumentId(LongFilter documentId) {
        this.documentId = documentId;
    }

    public LongFilter getStockItemListId() {
        return stockItemListId;
    }

    public void setStockItemListId(LongFilter stockItemListId) {
        this.stockItemListId = stockItemListId;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public LongFilter getProductBrandId() {
        return productBrandId;
    }

    public void setProductBrandId(LongFilter productBrandId) {
        this.productBrandId = productBrandId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductsCriteria that = (ProductsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(productName, that.productName) &&
            Objects.equals(handle, that.handle) &&
            Objects.equals(productNumber, that.productNumber) &&
            Objects.equals(sellCount, that.sellCount) &&
            Objects.equals(activeInd, that.activeInd) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(documentId, that.documentId) &&
            Objects.equals(stockItemListId, that.stockItemListId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(productBrandId, that.productBrandId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productName,
        handle,
        productNumber,
        sellCount,
        activeInd,
        lastEditedBy,
        lastEditedWhen,
        documentId,
        stockItemListId,
        supplierId,
        productCategoryId,
        productBrandId
        );
    }

    @Override
    public String toString() {
        return "ProductsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productName != null ? "productName=" + productName + ", " : "") +
                (handle != null ? "handle=" + handle + ", " : "") +
                (productNumber != null ? "productNumber=" + productNumber + ", " : "") +
                (sellCount != null ? "sellCount=" + sellCount + ", " : "") +
                (activeInd != null ? "activeInd=" + activeInd + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (documentId != null ? "documentId=" + documentId + ", " : "") +
                (stockItemListId != null ? "stockItemListId=" + stockItemListId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (productBrandId != null ? "productBrandId=" + productBrandId + ", " : "") +
            "}";
    }

}
