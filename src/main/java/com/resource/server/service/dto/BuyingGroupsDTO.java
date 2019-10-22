package com.resource.server.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.BuyingGroups} entity.
 */
public class BuyingGroupsDTO implements Serializable {

    private Long id;

    private String buyingGroupName;

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

    public String getBuyingGroupName() {
        return buyingGroupName;
    }

    public void setBuyingGroupName(String buyingGroupName) {
        this.buyingGroupName = buyingGroupName;
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

        BuyingGroupsDTO buyingGroupsDTO = (BuyingGroupsDTO) o;
        if (buyingGroupsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), buyingGroupsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BuyingGroupsDTO{" +
            "id=" + getId() +
            ", buyingGroupName='" + getBuyingGroupName() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
