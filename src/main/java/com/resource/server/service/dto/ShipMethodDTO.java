package com.resource.server.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.ShipMethod} entity.
 */
public class ShipMethodDTO implements Serializable {

    private Long id;

    private String shipMethodName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShipMethodName() {
        return shipMethodName;
    }

    public void setShipMethodName(String shipMethodName) {
        this.shipMethodName = shipMethodName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShipMethodDTO shipMethodDTO = (ShipMethodDTO) o;
        if (shipMethodDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipMethodDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipMethodDTO{" +
            "id=" + getId() +
            ", shipMethodName='" + getShipMethodName() + "'" +
            "}";
    }
}
