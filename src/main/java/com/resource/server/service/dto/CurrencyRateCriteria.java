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
 * Criteria class for the {@link com.resource.server.domain.CurrencyRate} entity. This class is used
 * in {@link com.resource.server.web.rest.CurrencyRateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /currency-rates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CurrencyRateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter currencyRateDate;

    private StringFilter fromCurrencyCode;

    private StringFilter toCurrencyCode;

    private FloatFilter averageRate;

    private FloatFilter endOfDayRate;

    private StringFilter lastEditedBy;

    private LocalDateFilter lastEditedWhen;

    public CurrencyRateCriteria(){
    }

    public CurrencyRateCriteria(CurrencyRateCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.currencyRateDate = other.currencyRateDate == null ? null : other.currencyRateDate.copy();
        this.fromCurrencyCode = other.fromCurrencyCode == null ? null : other.fromCurrencyCode.copy();
        this.toCurrencyCode = other.toCurrencyCode == null ? null : other.toCurrencyCode.copy();
        this.averageRate = other.averageRate == null ? null : other.averageRate.copy();
        this.endOfDayRate = other.endOfDayRate == null ? null : other.endOfDayRate.copy();
        this.lastEditedBy = other.lastEditedBy == null ? null : other.lastEditedBy.copy();
        this.lastEditedWhen = other.lastEditedWhen == null ? null : other.lastEditedWhen.copy();
    }

    @Override
    public CurrencyRateCriteria copy() {
        return new CurrencyRateCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getCurrencyRateDate() {
        return currencyRateDate;
    }

    public void setCurrencyRateDate(LocalDateFilter currencyRateDate) {
        this.currencyRateDate = currencyRateDate;
    }

    public StringFilter getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public void setFromCurrencyCode(StringFilter fromCurrencyCode) {
        this.fromCurrencyCode = fromCurrencyCode;
    }

    public StringFilter getToCurrencyCode() {
        return toCurrencyCode;
    }

    public void setToCurrencyCode(StringFilter toCurrencyCode) {
        this.toCurrencyCode = toCurrencyCode;
    }

    public FloatFilter getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(FloatFilter averageRate) {
        this.averageRate = averageRate;
    }

    public FloatFilter getEndOfDayRate() {
        return endOfDayRate;
    }

    public void setEndOfDayRate(FloatFilter endOfDayRate) {
        this.endOfDayRate = endOfDayRate;
    }

    public StringFilter getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(StringFilter lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public LocalDateFilter getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(LocalDateFilter lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CurrencyRateCriteria that = (CurrencyRateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(currencyRateDate, that.currencyRateDate) &&
            Objects.equals(fromCurrencyCode, that.fromCurrencyCode) &&
            Objects.equals(toCurrencyCode, that.toCurrencyCode) &&
            Objects.equals(averageRate, that.averageRate) &&
            Objects.equals(endOfDayRate, that.endOfDayRate) &&
            Objects.equals(lastEditedBy, that.lastEditedBy) &&
            Objects.equals(lastEditedWhen, that.lastEditedWhen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        currencyRateDate,
        fromCurrencyCode,
        toCurrencyCode,
        averageRate,
        endOfDayRate,
        lastEditedBy,
        lastEditedWhen
        );
    }

    @Override
    public String toString() {
        return "CurrencyRateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (currencyRateDate != null ? "currencyRateDate=" + currencyRateDate + ", " : "") +
                (fromCurrencyCode != null ? "fromCurrencyCode=" + fromCurrencyCode + ", " : "") +
                (toCurrencyCode != null ? "toCurrencyCode=" + toCurrencyCode + ", " : "") +
                (averageRate != null ? "averageRate=" + averageRate + ", " : "") +
                (endOfDayRate != null ? "endOfDayRate=" + endOfDayRate + ", " : "") +
                (lastEditedBy != null ? "lastEditedBy=" + lastEditedBy + ", " : "") +
                (lastEditedWhen != null ? "lastEditedWhen=" + lastEditedWhen + ", " : "") +
            "}";
    }

}
