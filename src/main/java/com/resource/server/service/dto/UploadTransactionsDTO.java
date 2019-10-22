package com.resource.server.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.UploadTransactions} entity.
 */
public class UploadTransactionsDTO implements Serializable {

    private Long id;

    private String fileName;

    private String templateUrl;

    private Integer status;

    private String generatedCode;

    private String lastEditedBy;

    private LocalDate lastEditedWhen;


    private Long supplierId;

    private String supplierSupplierName;

    private Long actionTypeId;

    private String actionTypeActionTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGeneratedCode() {
        return generatedCode;
    }

    public void setGeneratedCode(String generatedCode) {
        this.generatedCode = generatedCode;
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

    public Long getActionTypeId() {
        return actionTypeId;
    }

    public void setActionTypeId(Long uploadActionTypesId) {
        this.actionTypeId = uploadActionTypesId;
    }

    public String getActionTypeActionTypeName() {
        return actionTypeActionTypeName;
    }

    public void setActionTypeActionTypeName(String uploadActionTypesActionTypeName) {
        this.actionTypeActionTypeName = uploadActionTypesActionTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UploadTransactionsDTO uploadTransactionsDTO = (UploadTransactionsDTO) o;
        if (uploadTransactionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uploadTransactionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UploadTransactionsDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", templateUrl='" + getTemplateUrl() + "'" +
            ", status=" + getStatus() +
            ", generatedCode='" + getGeneratedCode() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", supplier=" + getSupplierId() +
            ", supplier='" + getSupplierSupplierName() + "'" +
            ", actionType=" + getActionTypeId() +
            ", actionType='" + getActionTypeActionTypeName() + "'" +
            "}";
    }
}
