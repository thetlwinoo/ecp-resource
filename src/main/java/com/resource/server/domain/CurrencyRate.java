package com.resource.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CurrencyRate.
 */
@Entity
@Table(name = "currency_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CurrencyRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "currency_rate_date", nullable = false)
    private LocalDate currencyRateDate;

    @Column(name = "from_currency_code")
    private String fromCurrencyCode;

    @Column(name = "to_currency_code")
    private String toCurrencyCode;

    @Column(name = "average_rate")
    private Float averageRate;

    @Column(name = "end_of_day_rate")
    private Float endOfDayRate;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private LocalDate lastEditedWhen;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCurrencyRateDate() {
        return currencyRateDate;
    }

    public CurrencyRate currencyRateDate(LocalDate currencyRateDate) {
        this.currencyRateDate = currencyRateDate;
        return this;
    }

    public void setCurrencyRateDate(LocalDate currencyRateDate) {
        this.currencyRateDate = currencyRateDate;
    }

    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public CurrencyRate fromCurrencyCode(String fromCurrencyCode) {
        this.fromCurrencyCode = fromCurrencyCode;
        return this;
    }

    public void setFromCurrencyCode(String fromCurrencyCode) {
        this.fromCurrencyCode = fromCurrencyCode;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public CurrencyRate toCurrencyCode(String toCurrencyCode) {
        this.toCurrencyCode = toCurrencyCode;
        return this;
    }

    public void setToCurrencyCode(String toCurrencyCode) {
        this.toCurrencyCode = toCurrencyCode;
    }

    public Float getAverageRate() {
        return averageRate;
    }

    public CurrencyRate averageRate(Float averageRate) {
        this.averageRate = averageRate;
        return this;
    }

    public void setAverageRate(Float averageRate) {
        this.averageRate = averageRate;
    }

    public Float getEndOfDayRate() {
        return endOfDayRate;
    }

    public CurrencyRate endOfDayRate(Float endOfDayRate) {
        this.endOfDayRate = endOfDayRate;
        return this;
    }

    public void setEndOfDayRate(Float endOfDayRate) {
        this.endOfDayRate = endOfDayRate;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public CurrencyRate lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public LocalDate getLastEditedWhen() {
        return lastEditedWhen;
    }

    public CurrencyRate lastEditedWhen(LocalDate lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(LocalDate lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrencyRate)) {
            return false;
        }
        return id != null && id.equals(((CurrencyRate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
            "id=" + getId() +
            ", currencyRateDate='" + getCurrencyRateDate() + "'" +
            ", fromCurrencyCode='" + getFromCurrencyCode() + "'" +
            ", toCurrencyCode='" + getToCurrencyCode() + "'" +
            ", averageRate=" + getAverageRate() +
            ", endOfDayRate=" + getEndOfDayRate() +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
