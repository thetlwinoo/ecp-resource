package com.resource.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A WarrantyTypes.
 */
@Entity
@Table(name = "warranty_types")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WarrantyTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "warranty_type_name", nullable = false)
    private String warrantyTypeName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWarrantyTypeName() {
        return warrantyTypeName;
    }

    public WarrantyTypes warrantyTypeName(String warrantyTypeName) {
        this.warrantyTypeName = warrantyTypeName;
        return this;
    }

    public void setWarrantyTypeName(String warrantyTypeName) {
        this.warrantyTypeName = warrantyTypeName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WarrantyTypes)) {
            return false;
        }
        return id != null && id.equals(((WarrantyTypes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WarrantyTypes{" +
            "id=" + getId() +
            ", warrantyTypeName='" + getWarrantyTypeName() + "'" +
            "}";
    }
}
