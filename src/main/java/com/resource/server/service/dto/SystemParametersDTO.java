package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.SystemParameters} entity.
 */
public class SystemParametersDTO implements Serializable {

    private Long id;

    @NotNull
    private String applicationSettings;


    private Long deliveryCityId;

    private String deliveryCityCityName;

    private Long postalCityId;

    private String postalCityCityName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationSettings() {
        return applicationSettings;
    }

    public void setApplicationSettings(String applicationSettings) {
        this.applicationSettings = applicationSettings;
    }

    public Long getDeliveryCityId() {
        return deliveryCityId;
    }

    public void setDeliveryCityId(Long citiesId) {
        this.deliveryCityId = citiesId;
    }

    public String getDeliveryCityCityName() {
        return deliveryCityCityName;
    }

    public void setDeliveryCityCityName(String citiesCityName) {
        this.deliveryCityCityName = citiesCityName;
    }

    public Long getPostalCityId() {
        return postalCityId;
    }

    public void setPostalCityId(Long citiesId) {
        this.postalCityId = citiesId;
    }

    public String getPostalCityCityName() {
        return postalCityCityName;
    }

    public void setPostalCityCityName(String citiesCityName) {
        this.postalCityCityName = citiesCityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SystemParametersDTO systemParametersDTO = (SystemParametersDTO) o;
        if (systemParametersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), systemParametersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SystemParametersDTO{" +
            "id=" + getId() +
            ", applicationSettings='" + getApplicationSettings() + "'" +
            ", deliveryCity=" + getDeliveryCityId() +
            ", deliveryCity='" + getDeliveryCityCityName() + "'" +
            ", postalCity=" + getPostalCityId() +
            ", postalCity='" + getPostalCityCityName() + "'" +
            "}";
    }
}
