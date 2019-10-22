package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.CurrencyRate} entity.
 */
public class CurrencyRateDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate currencyRateDate;

    private String fromCurrencyCode;

    private String toCurrencyCode;

    private Float averageRate;

    private Float endOfDayRate;

    private String lastEditedBy;

    private LocalDate lastEditedWhen;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCurrencyRateDate() {
        return currencyRateDate;
    }

    public void setCurrencyRateDate(LocalDate currencyRateDate) {
        this.currencyRateDate = currencyRateDate;
    }

    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public void setFromCurrencyCode(String fromCurrencyCode) {
        this.fromCurrencyCode = fromCurrencyCode;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public void setToCurrencyCode(String toCurrencyCode) {
        this.toCurrencyCode = toCurrencyCode;
    }

    public Float getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Float averageRate) {
        this.averageRate = averageRate;
    }

    public Float getEndOfDayRate() {
        return endOfDayRate;
    }

    public void setEndOfDayRate(Float endOfDayRate) {
        this.endOfDayRate = endOfDayRate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurrencyRateDTO currencyRateDTO = (CurrencyRateDTO) o;
        if (currencyRateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), currencyRateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurrencyRateDTO{" +
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
