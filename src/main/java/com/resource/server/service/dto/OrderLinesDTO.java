package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.OrderLines} entity.
 */
public class OrderLinesDTO implements Serializable {

    private Long id;

    private String carrierTrackingNumber;

    @NotNull
    private Integer quantity;

    private Float unitPrice;

    private Float unitPriceDiscount;

    private Float lineTotal;

    private Float taxRate;

    private Integer pickedQuantity;

    private LocalDate pickingCompletedWhen;

    private String lastEditedBy;

    private LocalDate lastEditedWhen;


    private Long stockItemId;

    private String stockItemStockItemName;

    private Long packageTypeId;

    private Long orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarrierTrackingNumber() {
        return carrierTrackingNumber;
    }

    public void setCarrierTrackingNumber(String carrierTrackingNumber) {
        this.carrierTrackingNumber = carrierTrackingNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getUnitPriceDiscount() {
        return unitPriceDiscount;
    }

    public void setUnitPriceDiscount(Float unitPriceDiscount) {
        this.unitPriceDiscount = unitPriceDiscount;
    }

    public Float getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(Float lineTotal) {
        this.lineTotal = lineTotal;
    }

    public Float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Float taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getPickedQuantity() {
        return pickedQuantity;
    }

    public void setPickedQuantity(Integer pickedQuantity) {
        this.pickedQuantity = pickedQuantity;
    }

    public LocalDate getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public void setPickingCompletedWhen(LocalDate pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public LocalDate getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(LocalDate lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemsId) {
        this.stockItemId = stockItemsId;
    }

    public String getStockItemStockItemName() {
        return stockItemStockItemName;
    }

    public void setStockItemStockItemName(String stockItemsStockItemName) {
        this.stockItemStockItemName = stockItemsStockItemName;
    }

    public Long getPackageTypeId() {
        return packageTypeId;
    }

    public void setPackageTypeId(Long packageTypesId) {
        this.packageTypeId = packageTypesId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long ordersId) {
        this.orderId = ordersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderLinesDTO orderLinesDTO = (OrderLinesDTO) o;
        if (orderLinesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderLinesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderLinesDTO{" +
            "id=" + getId() +
            ", carrierTrackingNumber='" + getCarrierTrackingNumber() + "'" +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", unitPriceDiscount=" + getUnitPriceDiscount() +
            ", lineTotal=" + getLineTotal() +
            ", taxRate=" + getTaxRate() +
            ", pickedQuantity=" + getPickedQuantity() +
            ", pickingCompletedWhen='" + getPickingCompletedWhen() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", stockItem=" + getStockItemId() +
            ", stockItem='" + getStockItemStockItemName() + "'" +
            ", packageType=" + getPackageTypeId() +
            ", order=" + getOrderId() +
            "}";
    }
}
