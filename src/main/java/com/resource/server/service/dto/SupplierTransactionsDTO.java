package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.SupplierTransactions} entity.
 */
public class SupplierTransactionsDTO implements Serializable {

    private Long id;

    private String supplierInvoiceNumber;

    @NotNull
    private LocalDate transactionDate;

    @NotNull
    private BigDecimal amountExcludingTax;

    @NotNull
    private BigDecimal taxAmount;

    @NotNull
    private BigDecimal transactionAmount;

    @NotNull
    private BigDecimal outstandingBalance;

    private LocalDate finalizationDate;

    private Boolean isFinalized;

    private String lastEditedBy;

    private LocalDate lastEditedWhen;


    private Long supplierId;

    private String supplierSupplierName;

    private Long transactionTypeId;

    private String transactionTypeTransactionTypeName;

    private Long purchaseOrderId;

    private Long paymentMethodId;

    private String paymentMethodPaymentMethodName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierInvoiceNumber() {
        return supplierInvoiceNumber;
    }

    public void setSupplierInvoiceNumber(String supplierInvoiceNumber) {
        this.supplierInvoiceNumber = supplierInvoiceNumber;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmountExcludingTax() {
        return amountExcludingTax;
    }

    public void setAmountExcludingTax(BigDecimal amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(BigDecimal outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public LocalDate getFinalizationDate() {
        return finalizationDate;
    }

    public void setFinalizationDate(LocalDate finalizationDate) {
        this.finalizationDate = finalizationDate;
    }

    public Boolean isIsFinalized() {
        return isFinalized;
    }

    public void setIsFinalized(Boolean isFinalized) {
        this.isFinalized = isFinalized;
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

    public String getTransactionTypeTransactionTypeName() {
        return transactionTypeTransactionTypeName;
    }

    public void setTransactionTypeTransactionTypeName(String transactionTypesTransactionTypeName) {
        this.transactionTypeTransactionTypeName = transactionTypesTransactionTypeName;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrdersId) {
        this.purchaseOrderId = purchaseOrdersId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodsId) {
        this.paymentMethodId = paymentMethodsId;
    }

    public String getPaymentMethodPaymentMethodName() {
        return paymentMethodPaymentMethodName;
    }

    public void setPaymentMethodPaymentMethodName(String paymentMethodsPaymentMethodName) {
        this.paymentMethodPaymentMethodName = paymentMethodsPaymentMethodName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplierTransactionsDTO supplierTransactionsDTO = (SupplierTransactionsDTO) o;
        if (supplierTransactionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplierTransactionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplierTransactionsDTO{" +
            "id=" + getId() +
            ", supplierInvoiceNumber='" + getSupplierInvoiceNumber() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", amountExcludingTax=" + getAmountExcludingTax() +
            ", taxAmount=" + getTaxAmount() +
            ", transactionAmount=" + getTransactionAmount() +
            ", outstandingBalance=" + getOutstandingBalance() +
            ", finalizationDate='" + getFinalizationDate() + "'" +
            ", isFinalized='" + isIsFinalized() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", supplier=" + getSupplierId() +
            ", supplier='" + getSupplierSupplierName() + "'" +
            ", transactionType=" + getTransactionTypeId() +
            ", transactionType='" + getTransactionTypeTransactionTypeName() + "'" +
            ", purchaseOrder=" + getPurchaseOrderId() +
            ", paymentMethod=" + getPaymentMethodId() +
            ", paymentMethod='" + getPaymentMethodPaymentMethodName() + "'" +
            "}";
    }
}
