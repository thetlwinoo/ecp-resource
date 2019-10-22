package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.PaymentMethods} entity.
 */
public class PaymentMethodsDTO implements Serializable {

    private Long id;

    @NotNull
    private String paymentMethodName;

    @NotNull
    private Boolean activeInd;

    @NotNull
    private LocalDate validFrom;

    @NotNull
    private LocalDate validTo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentMethodsDTO paymentMethodsDTO = (PaymentMethodsDTO) o;
        if (paymentMethodsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentMethodsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentMethodsDTO{" +
            "id=" + getId() +
            ", paymentMethodName='" + getPaymentMethodName() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
