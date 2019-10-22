package com.resource.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PaymentMethods.
 */
@Entity
@Table(name = "payment_methods")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaymentMethods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "payment_method_name", nullable = false)
    private String paymentMethodName;

    @NotNull
    @Column(name = "active_ind", nullable = false)
    private Boolean activeInd;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @NotNull
    @Column(name = "valid_to", nullable = false)
    private LocalDate validTo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public PaymentMethods paymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
        return this;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public PaymentMethods activeInd(Boolean activeInd) {
        this.activeInd = activeInd;
        return this;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public PaymentMethods validFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public PaymentMethods validTo(LocalDate validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethods)) {
            return false;
        }
        return id != null && id.equals(((PaymentMethods) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PaymentMethods{" +
            "id=" + getId() +
            ", paymentMethodName='" + getPaymentMethodName() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
