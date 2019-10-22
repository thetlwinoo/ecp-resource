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
 * Criteria class for the {@link com.resource.server.domain.OrderLines} entity. This class is used
 * in {@link com.resource.server.web.rest.OrderLinesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /order-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrderLinesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter carrierTrackingNumber;

    private IntegerFilter quantity;

    private FloatFilter unitPrice;

    private FloatFilter unitPriceDiscount;

    private FloatFilter lineTotal;

    private FloatFilter taxRate;

    private IntegerFilter pickedQuantity;

    private LocalDateFilter pickingCompletedWhen;

    private StringFilter lastEditedBy;

    private LocalDateFilter lastEditedWhen;

    private LongFilter stockItemId;

    private LongFilter packageTypeId;

    private LongFilter orderId;

    public OrderLinesCriteria(){
    }

    public OrderLinesCriteria(OrderLinesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.carrierTrackingNumber = other.carrierTrackingNumber == null ? null : other.carrierTrackingNumber.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.unitPrice = other.unitPrice == null ? null : other.unitPrice.copy();
        this.unitPriceDiscount = other.unitPriceDiscount == null ? null : other.unitPriceDiscount.copy();
        this.lineTotal = other.lineTotal == null ? null : other.lineTotal.copy();
        this.taxRate = other.taxRate == null ? null : other.taxRate.copy();
        this.pickedQuantity = other.pickedQuantity == null ? null : other.pickedQuantity.copy();
        this.pickingCompletedWhen = other.pickingCompletedWhen == null ? null : other.pickingCompletedWhen.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
        this.packageTypeId = other.packageTypeId == null ? null : other.packageTypeId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
    }

    @Override
    public OrderLinesCriteria copy() {
        return new OrderLinesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCarrierTrackingNumber() {
        return carrierTrackingNumber;
    }

    public void setCarrierTrackingNumber(StringFilter carrierTrackingNumber) {
        this.carrierTrackingNumber = carrierTrackingNumber;
    }

    public IntegerFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(IntegerFilter quantity) {
        this.quantity = quantity;
    }

    public FloatFilter getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(FloatFilter unitPrice) {
        this.unitPrice = unitPrice;
    }

    public FloatFilter getUnitPriceDiscount() {
        return unitPriceDiscount;
    }

    public void setUnitPriceDiscount(FloatFilter unitPriceDiscount) {
        this.unitPriceDiscount = unitPriceDiscount;
    }

    public FloatFilter getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(FloatFilter lineTotal) {
        this.lineTotal = lineTotal;
    }

    public FloatFilter getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(FloatFilter taxRate) {
        this.taxRate = taxRate;
    }

    public IntegerFilter getPickedQuantity() {
        return pickedQuantity;
    }

    public void setPickedQuantity(IntegerFilter pickedQuantity) {
        this.pickedQuantity = pickedQuantity;
    }

    public LocalDateFilter getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public void setPickingCompletedWhen(LocalDateFilter pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
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

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
    }

    public LongFilter getPackageTypeId() {
        return packageTypeId;
    }

    public void setPackageTypeId(LongFilter packageTypeId) {
        this.packageTypeId = packageTypeId;
    }

    public LongFilter getOrderId() {
        return orderId;
    }

    public void setOrderId(LongFilter orderId) {
        this.orderId = orderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderLinesCriteria that = (OrderLinesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(carrierTrackingNumber, that.carrierTrackingNumber) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(unitPriceDiscount, that.unitPriceDiscount) &&
            Objects.equals(lineTotal, that.lineTotal) &&
            Objects.equals(taxRate, that.taxRate) &&
            Objects.equals(pickedQuantity, that.pickedQuantity) &&
            Objects.equals(pickingCompletedWhen, that.pickingCompletedWhen) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen) &&
            Objects.equals(stockItemId, that.stockItemId) &&
            Objects.equals(packageTypeId, that.packageTypeId) &&
            Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        carrierTrackingNumber,
        quantity,
        unitPrice,
        unitPriceDiscount,
        lineTotal,
        taxRate,
        pickedQuantity,
        pickingCompletedWhen,
        lastEditedBy,
        lastEditedWhen,
        stockItemId,
        packageTypeId,
        orderId
        );
    }

    @Override
    public String toString() {
        return "OrderLinesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (carrierTrackingNumber != null ? "carrierTrackingNumber=" + carrierTrackingNumber + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (unitPriceDiscount != null ? "unitPriceDiscount=" + unitPriceDiscount + ", " : "") +
                (lineTotal != null ? "lineTotal=" + lineTotal + ", " : "") +
                (taxRate != null ? "taxRate=" + taxRate + ", " : "") +
                (pickedQuantity != null ? "pickedQuantity=" + pickedQuantity + ", " : "") +
                (pickingCompletedWhen != null ? "pickingCompletedWhen=" + pickingCompletedWhen + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
                (packageTypeId != null ? "packageTypeId=" + packageTypeId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
            "}";
    }

}
