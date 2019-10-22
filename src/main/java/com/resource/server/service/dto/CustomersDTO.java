package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.resource.server.domain.Customers} entity.
 */
public class CustomersDTO implements Serializable {

    private Long id;

    @NotNull
    private String accountNumber;


    private Long personId;

    private String personFullName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long peopleId) {
        this.personId = peopleId;
    }

    public String getPersonFullName() {
        return personFullName;
    }

    public void setPersonFullName(String peopleFullName) {
        this.personFullName = peopleFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomersDTO customersDTO = (CustomersDTO) o;
        if (customersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomersDTO{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", person=" + getPersonId() +
            ", person='" + getPersonFullName() + "'" +
            "}";
    }
}
