package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.StockItems} entity.
 */
public class StockItemsDTO implements Serializable {

    private Long id;

    @NotNull
    private String stockItemName;

    private String vendorCode;

    private String vendorSKU;

    private String generatedSKU;

    private String barcode;

    @NotNull
    private Float unitPrice;

    private Float recommendedRetailPrice;

    @NotNull
    private Integer quantityOnHand;

    private Integer itemLength;

    private Integer itemWidth;

    private Integer itemHeight;

    private Float itemWeight;

    private Integer itemPackageLength;

    private Integer itemPackageWidth;

    private Integer itemPackageHeight;

    private Float itemPackageWeight;

    private Integer noOfPieces;

    private Integer noOfItems;

    private String manufacture;

    private String marketingComments;

    private String internalComments;

    private LocalDate sellStartDate;

    private LocalDate sellEndDate;

    private Integer sellCount;

    private String customFields;

    private String thumbnailUrl;

    private Boolean activeInd;

    private String lastEditedBy;

    private LocalDate lastEditedWhen;


    private Long stockItemOnReviewLineId;

    private Long itemLengthUnitId;

    private String itemLengthUnitUnitMeasureCode;

    private Long itemWidthUnitId;

    private String itemWidthUnitUnitMeasureCode;

    private Long itemHeightUnitId;

    private String itemHeightUnitUnitMeasureCode;

    private Long packageLengthUnitId;

    private String packageLengthUnitUnitMeasureCode;

    private Long packageWidthUnitId;

    private String packageWidthUnitUnitMeasureCode;

    private Long packageHeightUnitId;

    private String packageHeightUnitUnitMeasureCode;

    private Long itemPackageWeightUnitId;

    private String itemPackageWeightUnitUnitMeasureCode;

    private Long productAttributeId;

    private String productAttributeProductAttributeValue;

    private Long productOptionId;

    private String productOptionProductOptionValue;

    private Long materialId;

    private String materialMaterialName;

    private Long currencyId;

    private String currencyCurrencyCode;

    private Long barcodeTypeId;

    private String barcodeTypeBarcodeTypeName;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockItemName() {
        return stockItemName;
    }

    public void setStockItemName(String stockItemName) {
        this.stockItemName = stockItemName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorSKU() {
        return vendorSKU;
    }

    public void setVendorSKU(String vendorSKU) {
        this.vendorSKU = vendorSKU;
    }

    public String getGeneratedSKU() {
        return generatedSKU;
    }

    public void setGeneratedSKU(String generatedSKU) {
        this.generatedSKU = generatedSKU;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getRecommendedRetailPrice() {
        return recommendedRetailPrice;
    }

    public void setRecommendedRetailPrice(Float recommendedRetailPrice) {
        this.recommendedRetailPrice = recommendedRetailPrice;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public Integer getItemLength() {
        return itemLength;
    }

    public void setItemLength(Integer itemLength) {
        this.itemLength = itemLength;
    }

    public Integer getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(Integer itemWidth) {
        this.itemWidth = itemWidth;
    }

    public Integer getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(Integer itemHeight) {
        this.itemHeight = itemHeight;
    }

    public Float getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(Float itemWeight) {
        this.itemWeight = itemWeight;
    }

    public Integer getItemPackageLength() {
        return itemPackageLength;
    }

    public void setItemPackageLength(Integer itemPackageLength) {
        this.itemPackageLength = itemPackageLength;
    }

    public Integer getItemPackageWidth() {
        return itemPackageWidth;
    }

    public void setItemPackageWidth(Integer itemPackageWidth) {
        this.itemPackageWidth = itemPackageWidth;
    }

    public Integer getItemPackageHeight() {
        return itemPackageHeight;
    }

    public void setItemPackageHeight(Integer itemPackageHeight) {
        this.itemPackageHeight = itemPackageHeight;
    }

    public Float getItemPackageWeight() {
        return itemPackageWeight;
    }

    public void setItemPackageWeight(Float itemPackageWeight) {
        this.itemPackageWeight = itemPackageWeight;
    }

    public Integer getNoOfPieces() {
        return noOfPieces;
    }

    public void setNoOfPieces(Integer noOfPieces) {
        this.noOfPieces = noOfPieces;
    }

    public Integer getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(Integer noOfItems) {
        this.noOfItems = noOfItems;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getMarketingComments() {
        return marketingComments;
    }

    public void setMarketingComments(String marketingComments) {
        this.marketingComments = marketingComments;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public LocalDate getSellStartDate() {
        return sellStartDate;
    }

    public void setSellStartDate(LocalDate sellStartDate) {
        this.sellStartDate = sellStartDate;
    }

    public LocalDate getSellEndDate() {
        return sellEndDate;
    }

    public void setSellEndDate(LocalDate sellEndDate) {
        this.sellEndDate = sellEndDate;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public String getCustomFields() {
        return customFields;
    }

    public void setCustomFields(String customFields) {
        this.customFields = customFields;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
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

    public Long getStockItemOnReviewLineId() {
        return stockItemOnReviewLineId;
    }

    public void setStockItemOnReviewLineId(Long reviewLinesId) {
        this.stockItemOnReviewLineId = reviewLinesId;
    }

    public Long getItemLengthUnitId() {
        return itemLengthUnitId;
    }

    public void setItemLengthUnitId(Long unitMeasureId) {
        this.itemLengthUnitId = unitMeasureId;
    }

    public String getItemLengthUnitUnitMeasureCode() {
        return itemLengthUnitUnitMeasureCode;
    }

    public void setItemLengthUnitUnitMeasureCode(String unitMeasureUnitMeasureCode) {
        this.itemLengthUnitUnitMeasureCode = unitMeasureUnitMeasureCode;
    }

    public Long getItemWidthUnitId() {
        return itemWidthUnitId;
    }

    public void setItemWidthUnitId(Long unitMeasureId) {
        this.itemWidthUnitId = unitMeasureId;
    }

    public String getItemWidthUnitUnitMeasureCode() {
        return itemWidthUnitUnitMeasureCode;
    }

    public void setItemWidthUnitUnitMeasureCode(String unitMeasureUnitMeasureCode) {
        this.itemWidthUnitUnitMeasureCode = unitMeasureUnitMeasureCode;
    }

    public Long getItemHeightUnitId() {
        return itemHeightUnitId;
    }

    public void setItemHeightUnitId(Long unitMeasureId) {
        this.itemHeightUnitId = unitMeasureId;
    }

    public String getItemHeightUnitUnitMeasureCode() {
        return itemHeightUnitUnitMeasureCode;
    }

    public void setItemHeightUnitUnitMeasureCode(String unitMeasureUnitMeasureCode) {
        this.itemHeightUnitUnitMeasureCode = unitMeasureUnitMeasureCode;
    }

    public Long getPackageLengthUnitId() {
        return packageLengthUnitId;
    }

    public void setPackageLengthUnitId(Long unitMeasureId) {
        this.packageLengthUnitId = unitMeasureId;
    }

    public String getPackageLengthUnitUnitMeasureCode() {
        return packageLengthUnitUnitMeasureCode;
    }

    public void setPackageLengthUnitUnitMeasureCode(String unitMeasureUnitMeasureCode) {
        this.packageLengthUnitUnitMeasureCode = unitMeasureUnitMeasureCode;
    }

    public Long getPackageWidthUnitId() {
        return packageWidthUnitId;
    }

    public void setPackageWidthUnitId(Long unitMeasureId) {
        this.packageWidthUnitId = unitMeasureId;
    }

    public String getPackageWidthUnitUnitMeasureCode() {
        return packageWidthUnitUnitMeasureCode;
    }

    public void setPackageWidthUnitUnitMeasureCode(String unitMeasureUnitMeasureCode) {
        this.packageWidthUnitUnitMeasureCode = unitMeasureUnitMeasureCode;
    }

    public Long getPackageHeightUnitId() {
        return packageHeightUnitId;
    }

    public void setPackageHeightUnitId(Long unitMeasureId) {
        this.packageHeightUnitId = unitMeasureId;
    }

    public String getPackageHeightUnitUnitMeasureCode() {
        return packageHeightUnitUnitMeasureCode;
    }

    public void setPackageHeightUnitUnitMeasureCode(String unitMeasureUnitMeasureCode) {
        this.packageHeightUnitUnitMeasureCode = unitMeasureUnitMeasureCode;
    }

    public Long getItemPackageWeightUnitId() {
        return itemPackageWeightUnitId;
    }

    public void setItemPackageWeightUnitId(Long unitMeasureId) {
        this.itemPackageWeightUnitId = unitMeasureId;
    }

    public String getItemPackageWeightUnitUnitMeasureCode() {
        return itemPackageWeightUnitUnitMeasureCode;
    }

    public void setItemPackageWeightUnitUnitMeasureCode(String unitMeasureUnitMeasureCode) {
        this.itemPackageWeightUnitUnitMeasureCode = unitMeasureUnitMeasureCode;
    }

    public Long getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(Long productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getProductAttributeProductAttributeValue() {
        return productAttributeProductAttributeValue;
    }

    public void setProductAttributeProductAttributeValue(String productAttributeProductAttributeValue) {
        this.productAttributeProductAttributeValue = productAttributeProductAttributeValue;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getProductOptionProductOptionValue() {
        return productOptionProductOptionValue;
    }

    public void setProductOptionProductOptionValue(String productOptionProductOptionValue) {
        this.productOptionProductOptionValue = productOptionProductOptionValue;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialsId) {
        this.materialId = materialsId;
    }

    public String getMaterialMaterialName() {
        return materialMaterialName;
    }

    public void setMaterialMaterialName(String materialsMaterialName) {
        this.materialMaterialName = materialsMaterialName;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCurrencyCode() {
        return currencyCurrencyCode;
    }

    public void setCurrencyCurrencyCode(String currencyCurrencyCode) {
        this.currencyCurrencyCode = currencyCurrencyCode;
    }

    public Long getBarcodeTypeId() {
        return barcodeTypeId;
    }

    public void setBarcodeTypeId(Long barcodeTypesId) {
        this.barcodeTypeId = barcodeTypesId;
    }

    public String getBarcodeTypeBarcodeTypeName() {
        return barcodeTypeBarcodeTypeName;
    }

    public void setBarcodeTypeBarcodeTypeName(String barcodeTypesBarcodeTypeName) {
        this.barcodeTypeBarcodeTypeName = barcodeTypesBarcodeTypeName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productsId) {
        this.productId = productsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockItemsDTO stockItemsDTO = (StockItemsDTO) o;
        if (stockItemsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockItemsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockItemsDTO{" +
            "id=" + getId() +
            ", stockItemName='" + getStockItemName() + "'" +
            ", vendorCode='" + getVendorCode() + "'" +
            ", vendorSKU='" + getVendorSKU() + "'" +
            ", generatedSKU='" + getGeneratedSKU() + "'" +
            ", barcode='" + getBarcode() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", recommendedRetailPrice=" + getRecommendedRetailPrice() +
            ", quantityOnHand=" + getQuantityOnHand() +
            ", itemLength=" + getItemLength() +
            ", itemWidth=" + getItemWidth() +
            ", itemHeight=" + getItemHeight() +
            ", itemWeight=" + getItemWeight() +
            ", itemPackageLength=" + getItemPackageLength() +
            ", itemPackageWidth=" + getItemPackageWidth() +
            ", itemPackageHeight=" + getItemPackageHeight() +
            ", itemPackageWeight=" + getItemPackageWeight() +
            ", noOfPieces=" + getNoOfPieces() +
            ", noOfItems=" + getNoOfItems() +
            ", manufacture='" + getManufacture() + "'" +
            ", marketingComments='" + getMarketingComments() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", sellStartDate='" + getSellStartDate() + "'" +
            ", sellEndDate='" + getSellEndDate() + "'" +
            ", sellCount=" + getSellCount() +
            ", customFields='" + getCustomFields() + "'" +
            ", thumbnailUrl='" + getThumbnailUrl() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", stockItemOnReviewLine=" + getStockItemOnReviewLineId() +
            ", itemLengthUnit=" + getItemLengthUnitId() +
            ", itemLengthUnit='" + getItemLengthUnitUnitMeasureCode() + "'" +
            ", itemWidthUnit=" + getItemWidthUnitId() +
            ", itemWidthUnit='" + getItemWidthUnitUnitMeasureCode() + "'" +
            ", itemHeightUnit=" + getItemHeightUnitId() +
            ", itemHeightUnit='" + getItemHeightUnitUnitMeasureCode() + "'" +
            ", packageLengthUnit=" + getPackageLengthUnitId() +
            ", packageLengthUnit='" + getPackageLengthUnitUnitMeasureCode() + "'" +
            ", packageWidthUnit=" + getPackageWidthUnitId() +
            ", packageWidthUnit='" + getPackageWidthUnitUnitMeasureCode() + "'" +
            ", packageHeightUnit=" + getPackageHeightUnitId() +
            ", packageHeightUnit='" + getPackageHeightUnitUnitMeasureCode() + "'" +
            ", itemPackageWeightUnit=" + getItemPackageWeightUnitId() +
            ", itemPackageWeightUnit='" + getItemPackageWeightUnitUnitMeasureCode() + "'" +
            ", productAttribute=" + getProductAttributeId() +
            ", productAttribute='" + getProductAttributeProductAttributeValue() + "'" +
            ", productOption=" + getProductOptionId() +
            ", productOption='" + getProductOptionProductOptionValue() + "'" +
            ", material=" + getMaterialId() +
            ", material='" + getMaterialMaterialName() + "'" +
            ", currency=" + getCurrencyId() +
            ", currency='" + getCurrencyCurrencyCode() + "'" +
            ", barcodeType=" + getBarcodeTypeId() +
            ", barcodeType='" + getBarcodeTypeBarcodeTypeName() + "'" +
            ", product=" + getProductId() +
            "}";
    }
}
