package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.Orders} entity.
 */
public class OrdersDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate orderDate;

    private LocalDate dueDate;

    private LocalDate shipDate;

    private Integer paymentStatus;

    private Integer orderFlag;

    private String orderNumber;

    private Float subTotal;

    private Float taxAmount;

    private Float frieight;

    private Float totalDue;

    private String comments;

    private String deliveryInstructions;

    private String internalComments;

    private LocalDate pickingCompletedWhen;

    private String lastEditedBy;

    private LocalDate lastEditedWhen;


    private Long orderOnReviewId;

    private Long customerId;

    private Long shipToAddressId;

    private Long billToAddressId;

    private Long shipMethodId;

    private String shipMethodShipMethodName;

    private Long currencyRateId;

    private Long specialDealsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getShipDate() {
        return shipDate;
    }

    public void setShipDate(LocalDate shipDate) {
        this.shipDate = shipDate;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Float subTotal) {
        this.subTotal = subTotal;
    }

    public Float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Float getFrieight() {
        return frieight;
    }

    public void setFrieight(Float frieight) {
        this.frieight = frieight;
    }

    public Float getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Float totalDue) {
        this.totalDue = totalDue;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
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

    public Long getOrderOnReviewId() {
        return orderOnReviewId;
    }

    public void setOrderOnReviewId(Long reviewsId) {
        this.orderOnReviewId = reviewsId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customersId) {
        this.customerId = customersId;
    }

    public Long getShipToAddressId() {
        return shipToAddressId;
    }

    public void setShipToAddressId(Long addressesId) {
        this.shipToAddressId = addressesId;
    }

    public Long getBillToAddressId() {
        return billToAddressId;
    }

    public void setBillToAddressId(Long addressesId) {
        this.billToAddressId = addressesId;
    }

    public Long getShipMethodId() {
        return shipMethodId;
    }

    public void setShipMethodId(Long shipMethodId) {
        this.shipMethodId = shipMethodId;
    }

    public String getShipMethodShipMethodName() {
        return shipMethodShipMethodName;
    }

    public void setShipMethodShipMethodName(String shipMethodShipMethodName) {
        this.shipMethodShipMethodName = shipMethodShipMethodName;
    }

    public Long getCurrencyRateId() {
        return currencyRateId;
    }

    public void setCurrencyRateId(Long currencyRateId) {
        this.currencyRateId = currencyRateId;
    }

    public Long getSpecialDealsId() {
        return specialDealsId;
    }

    public void setSpecialDealsId(Long specialDealsId) {
        this.specialDealsId = specialDealsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrdersDTO ordersDTO = (OrdersDTO) o;
        if (ordersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrdersDTO{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", shipDate='" + getShipDate() + "'" +
            ", paymentStatus=" + getPaymentStatus() +
            ", orderFlag=" + getOrderFlag() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", subTotal=" + getSubTotal() +
            ", taxAmount=" + getTaxAmount() +
            ", frieight=" + getFrieight() +
            ", totalDue=" + getTotalDue() +
            ", comments='" + getComments() + "'" +
            ", deliveryInstructions='" + getDeliveryInstructions() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", pickingCompletedWhen='" + getPickingCompletedWhen() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", orderOnReview=" + getOrderOnReviewId() +
            ", customer=" + getCustomerId() +
            ", shipToAddress=" + getShipToAddressId() +
            ", billToAddress=" + getBillToAddressId() +
            ", shipMethod=" + getShipMethodId() +
            ", shipMethod='" + getShipMethodShipMethodName() + "'" +
            ", currencyRate=" + getCurrencyRateId() +
            ", specialDeals=" + getSpecialDealsId() +
            "}";
    }
}
