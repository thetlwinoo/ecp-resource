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
 * Criteria class for the {@link com.resource.server.domain.TransactionTypes} entity. This class is used
 * in {@link com.resource.server.web.rest.TransactionTypesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transaction-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransactionTypesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter transactionTypeName;

    private LocalDateFilter validFrom;

    private LocalDateFilter validTo;

    public TransactionTypesCriteria(){
    }

    public TransactionTypesCriteria(TransactionTypesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.transactionTypeName = other.transactionTypeName == null ? null : other.transactionTypeName.copy();
        this.validFrom = other.validFrom == null ? null : other.validFrom.copy();
        this.validTo = other.validTo == null ? null : other.validTo.copy();
    }

    @Override
    public TransactionTypesCriteria copy() {
        return new TransactionTypesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(StringFilter transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
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
        final TransactionTypesCriteria that = (TransactionTypesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(transactionTypeName, that.transactionTypeName) &&
            Objects.equals(validFrom, that.validFrom) &&
            Objects.equals(validTo, that.validTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        transactionTypeName,
        validFrom,
        validTo
        );
    }

    @Override
    public String toString() {
        return "TransactionTypesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (transactionTypeName != null ? "transactionTypeName=" + transactionTypeName + ", " : "") +
                (validFrom != null ? "validFrom=" + validFrom + ", " : "") +
                (validTo != null ? "validTo=" + validTo + ", " : "") +
            "}";
    }

}
