package com.resource.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ContactType.
 */
@Entity
@Table(name = "contact_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContactType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "contact_type_name", nullable = false)
    private String contactTypeName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactTypeName() {
        return contactTypeName;
    }

    public ContactType contactTypeName(String contactTypeName) {
        this.contactTypeName = contactTypeName;
        return this;
    }

    public void setContactTypeName(String contactTypeName) {
        this.contactTypeName = contactTypeName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactType)) {
            return false;
        }
        return id != null && id.equals(((ContactType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContactType{" +
            "id=" + getId() +
            ", contactTypeName='" + getContactTypeName() + "'" +
            "}";
    }
}
