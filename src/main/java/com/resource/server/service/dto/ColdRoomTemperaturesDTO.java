package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.ColdRoomTemperatures} entity.
 */
public class ColdRoomTemperaturesDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer coldRoomSensorNumber;

    @NotNull
    private LocalDate recordedWhen;

    @NotNull
    private Float temperature;

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

    public Integer getColdRoomSensorNumber() {
        return coldRoomSensorNumber;
    }

    public void setColdRoomSensorNumber(Integer coldRoomSensorNumber) {
        this.coldRoomSensorNumber = coldRoomSensorNumber;
    }

    public LocalDate getRecordedWhen() {
        return recordedWhen;
    }

    public void setRecordedWhen(LocalDate recordedWhen) {
        this.recordedWhen = recordedWhen;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
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

        ColdRoomTemperaturesDTO coldRoomTemperaturesDTO = (ColdRoomTemperaturesDTO) o;
        if (coldRoomTemperaturesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coldRoomTemperaturesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ColdRoomTemperaturesDTO{" +
            "id=" + getId() +
            ", coldRoomSensorNumber=" + getColdRoomSensorNumber() +
            ", recordedWhen='" + getRecordedWhen() + "'" +
            ", temperature=" + getTemperature() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
