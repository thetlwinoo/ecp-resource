package com.resource.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PhoneNumberType.
 */
@Entity
@Table(name = "phone_number_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PhoneNumberType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "phone_number_type_name", nullable = false)
    private String phoneNumberTypeName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumberTypeName() {
        return phoneNumberTypeName;
    }

    public PhoneNumberType phoneNumberTypeName(String phoneNumberTypeName) {
        this.phoneNumberTypeName = phoneNumberTypeName;
        return this;
    }

    public void setPhoneNumberTypeName(String phoneNumberTypeName) {
        this.phoneNumberTypeName = phoneNumberTypeName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhoneNumberType)) {
            return false;
        }
        return id != null && id.equals(((PhoneNumberType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PhoneNumberType{" +
            "id=" + getId() +
            ", phoneNumberTypeName='" + getPhoneNumberTypeName() + "'" +
            "}";
    }
}
