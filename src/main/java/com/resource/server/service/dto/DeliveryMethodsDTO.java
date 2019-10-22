package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.DeliveryMethods} entity.
 */
public class DeliveryMethodsDTO implements Serializable {

    private Long id;

    @NotNull
    private String deliveryMethodName;

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

    public String getDeliveryMethodName() {
        return deliveryMethodName;
    }

    public void setDeliveryMethodName(String deliveryMethodName) {
        this.deliveryMethodName = deliveryMethodName;
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

        DeliveryMethodsDTO deliveryMethodsDTO = (DeliveryMethodsDTO) o;
        if (deliveryMethodsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deliveryMethodsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeliveryMethodsDTO{" +
            "id=" + getId() +
            ", deliveryMethodName='" + getDeliveryMethodName() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
