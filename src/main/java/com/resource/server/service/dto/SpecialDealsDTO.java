package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.SpecialDeals} entity.
 */
public class SpecialDealsDTO implements Serializable {

    private Long id;

    @NotNull
    private String dealDescription;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private Float discountAmount;

    private Float discountPercentage;

    private String discountCode;

    private Float unitPrice;

    private String lastEditedBy;

    private LocalDate lastEditedWhen;


    private Long buyingGroupId;

    private String buyingGroupBuyingGroupName;

    private Long customerCategoryId;

    private String customerCategoryCustomerCategoryName;

    private Long customerId;

    private Long productCategoryId;

    private String productCategoryName;

    private Long stockItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDealDescription() {
        return dealDescription;
    }

    public void setDealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
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

    public Long getBuyingGroupId() {
        return buyingGroupId;
    }

    public void setBuyingGroupId(Long buyingGroupsId) {
        this.buyingGroupId = buyingGroupsId;
    }

    public String getBuyingGroupBuyingGroupName() {
        return buyingGroupBuyingGroupName;
    }

    public void setBuyingGroupBuyingGroupName(String buyingGroupsBuyingGroupName) {
        this.buyingGroupBuyingGroupName = buyingGroupsBuyingGroupName;
    }

    public Long getCustomerCategoryId() {
        return customerCategoryId;
    }

    public void setCustomerCategoryId(Long customerCategoriesId) {
        this.customerCategoryId = customerCategoriesId;
    }

    public String getCustomerCategoryCustomerCategoryName() {
        return customerCategoryCustomerCategoryName;
    }

    public void setCustomerCategoryCustomerCategoryName(String customerCategoriesCustomerCategoryName) {
        this.customerCategoryCustomerCategoryName = customerCategoriesCustomerCategoryName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customersId) {
        this.customerId = customersId;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemsId) {
        this.stockItemId = stockItemsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpecialDealsDTO specialDealsDTO = (SpecialDealsDTO) o;
        if (specialDealsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), specialDealsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpecialDealsDTO{" +
            "id=" + getId() +
            ", dealDescription='" + getDealDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", discountAmount=" + getDiscountAmount() +
            ", discountPercentage=" + getDiscountPercentage() +
            ", discountCode='" + getDiscountCode() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", buyingGroup=" + getBuyingGroupId() +
            ", buyingGroup='" + getBuyingGroupBuyingGroupName() + "'" +
            ", customerCategory=" + getCustomerCategoryId() +
            ", customerCategory='" + getCustomerCategoryCustomerCategoryName() + "'" +
            ", customer=" + getCustomerId() +
            ", productCategory=" + getProductCategoryId() +
            ", productCategory='" + getProductCategoryName() + "'" +
            ", stockItem=" + getStockItemId() +
            "}";
    }
}
