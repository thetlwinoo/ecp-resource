package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.StockItemTransactions} entity.
 */
public class StockItemTransactionsDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate transactionOccurredWhen;

    @NotNull
    private BigDecimal quantity;

    private String lastEditedBy;

    private LocalDate lastEditedWhen;


    private Long stockItemId;

    private String stockItemStockItemName;

    private Long customerId;

    private Long invoiceId;

    private Long supplierId;

    private String supplierSupplierName;

    private Long transactionTypeId;

    private Long purchaseOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTransactionOccurredWhen() {
        return transactionOccurredWhen;
    }

    public void setTransactionOccurredWhen(LocalDate transactionOccurredWhen) {
        this.transactionOccurredWhen = transactionOccurredWhen;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customersId) {
        this.customerId = customersId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoicesId) {
        this.invoiceId = invoicesId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long suppliersId) {
        this.supplierId = suppliersId;
    }

    public String getSupplierSupplierName() {
        return supplierSupplierName;
    }

    public void setSupplierSupplierName(String suppliersSupplierName) {
        this.supplierSupplierName = suppliersSupplierName;
    }

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypesId) {
        this.transactionTypeId = transactionTypesId;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrdersId) {
        this.purchaseOrderId = purchaseOrdersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockItemTransactionsDTO stockItemTransactionsDTO = (StockItemTransactionsDTO) o;
        if (stockItemTransactionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockItemTransactionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockItemTransactionsDTO{" +
            "id=" + getId() +
            ", transactionOccurredWhen='" + getTransactionOccurredWhen() + "'" +
            ", quantity=" + getQuantity() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", stockItem=" + getStockItemId() +
            ", stockItem='" + getStockItemStockItemName() + "'" +
            ", customer=" + getCustomerId() +
            ", invoice=" + getInvoiceId() +
            ", supplier=" + getSupplierId() +
            ", supplier='" + getSupplierSupplierName() + "'" +
            ", transactionType=" + getTransactionTypeId() +
            ", purchaseOrder=" + getPurchaseOrderId() +
            "}";
    }
}
