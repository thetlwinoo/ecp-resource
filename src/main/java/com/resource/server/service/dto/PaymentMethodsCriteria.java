package com.resource.server.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.resource.server.domain.PaymentMethods} entity. This class is used
 * in {@link com.resource.server.web.rest.PaymentMethodsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payment-methods?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentMethodsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter paymentMethodName;

    private BooleanFilter activeInd;

    private LocalDateFilter validFrom;

    private LocalDateFilter validTo;

    public PaymentMethodsCriteria(){
    }

    public PaymentMethodsCriteria(PaymentMethodsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.paymentMethodName = other.paymentMethodName == null ? null : other.paymentMethodName.copy();
        this.activeInd = other.activeInd == null ? null : other.activeInd.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
    }

    @Override
    public PaymentMethodsCriteria copy() {
        return new PaymentMethodsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(StringFilter paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public BooleanFilter getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(BooleanFilter activeInd) {
        this.activeInd = activeInd;
    }

    public LocalDateFilter getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateFilter validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateFilter getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateFilter validTo) {
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
        final PaymentMethodsCriteria that = (PaymentMethodsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(paymentMethodName, that.paymentMethodName) &&
            Objects.equals(activeInd, that.activeInd) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        paymentMethodName,
        activeInd,
        validFrom,
        validTo
        );
    }

    @Override
    public String toString() {
        return "PaymentMethodsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (paymentMethodName != null ? "paymentMethodName=" + paymentMethodName + ", " : "") +
                (activeInd != null ? "activeInd=" + activeInd + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
            "}";
    }

}
