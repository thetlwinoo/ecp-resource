package com.resource.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PurchaseOrderLines.
 */
@Entity
@Table(name = "purchase_order_lines")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PurchaseOrderLines implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "orders_outers", nullable = false)
    private Integer ordersOuters;

    @Column(name = "description")
    private String description;

    @Column(name = "received_outers")
    private Integer receivedOuters;

    @Column(name = "expected_unit_price_per_outer")
    private Float expectedUnitPricePerOuter;

    @Column(name = "last_receipt_date")
    private LocalDate lastReceiptDate;

    @Column(name = "is_order_line_finalized")
    private Boolean isOrderLineFinalized;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private LocalDate lastEditedWhen;

    @ManyToOne
    @JsonIgnoreProperties("purchaseOrderLines")
    private PackageTypes packageType;

    @ManyToOne
    @JsonIgnoreProperties("purchaseOrderLines")
    private StockItems stockItem;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnoreProperties("purchaseOrderLineLists")
    private PurchaseOrders purchaseOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdersOuters() {
        return ordersOuters;
    }

    public PurchaseOrderLines ordersOuters(Integer ordersOuters) {
        this.ordersOuters = ordersOuters;
        return this;
    }

    public void setOrdersOuters(Integer ordersOuters) {
        this.ordersOuters = ordersOuters;
    }

    public String getDescription() {
        return description;
    }

    public PurchaseOrderLines description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReceivedOuters() {
        return receivedOuters;
    }

    public PurchaseOrderLines receivedOuters(Integer receivedOuters) {
        this.receivedOuters = receivedOuters;
        return this;
    }

    public void setReceivedOuters(Integer receivedOuters) {
        this.receivedOuters = receivedOuters;
    }

    public Float getExpectedUnitPricePerOuter() {
        return expectedUnitPricePerOuter;
    }

    public PurchaseOrderLines expectedUnitPricePerOuter(Float expectedUnitPricePerOuter) {
        this.expectedUnitPricePerOuter = expectedUnitPricePerOuter;
        return this;
    }

    public void setExpectedUnitPricePerOuter(Float expectedUnitPricePerOuter) {
        this.expectedUnitPricePerOuter = expectedUnitPricePerOuter;
    }

    public LocalDate getLastReceiptDate() {
        return lastReceiptDate;
    }

    public PurchaseOrderLines lastReceiptDate(LocalDate lastReceiptDate) {
        this.lastReceiptDate = lastReceiptDate;
        return this;
    }

    public void setLastReceiptDate(LocalDate lastReceiptDate) {
        this.lastReceiptDate = lastReceiptDate;
    }

    public Boolean isIsOrderLineFinalized() {
        return isOrderLineFinalized;
    }

    public PurchaseOrderLines isOrderLineFinalized(Boolean isOrderLineFinalized) {
        this.isOrderLineFinalized = isOrderLineFinalized;
        return this;
    }

    public void setIsOrderLineFinalized(Boolean isOrderLineFinalized) {
        this.isOrderLineFinalized = isOrderLineFinalized;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public PurchaseOrderLines lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public LocalDate getLastEditedWhen() {
        return lastEditedWhen;
    }

    public PurchaseOrderLines lastEditedWhen(LocalDate lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(LocalDate lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public PackageTypes getPackageType() {
        return packageType;
    }

    public PurchaseOrderLines packageType(PackageTypes packageTypes) {
        this.packageType = packageTypes;
        return this;
    }

    public void setPackageType(PackageTypes packageTypes) {
        this.packageType = packageTypes;
    }

    public StockItems getStockItem() {
        return stockItem;
    }

    public PurchaseOrderLines stockItem(StockItems stockItems) {
        this.stockItem = stockItems;
        return this;
    }

    public void setStockItem(StockItems stockItems) {
        this.stockItem = stockItems;
    }

    public PurchaseOrders getPurchaseOrder() {
        return purchaseOrder;
    }

    public PurchaseOrderLines purchaseOrder(PurchaseOrders purchaseOrders) {
        this.purchaseOrder = purchaseOrders;
        return this;
    }

    public void setPurchaseOrder(PurchaseOrders purchaseOrders) {
        this.purchaseOrder = purchaseOrders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseOrderLines)) {
            return false;
        }
        return id != null && id.equals(((PurchaseOrderLines) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PurchaseOrderLines{" +
            "id=" + getId() +
            ", ordersOuters=" + getOrdersOuters() +
            ", description='" + getDescription() + "'" +
            ", receivedOuters=" + getReceivedOuters() +
            ", expectedUnitPricePerOuter=" + getExpectedUnitPricePerOuter() +
            ", lastReceiptDate='" + getLastReceiptDate() + "'" +
            ", isOrderLineFinalized='" + isIsOrderLineFinalized() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
