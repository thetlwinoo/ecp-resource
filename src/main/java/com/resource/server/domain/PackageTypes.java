package com.resource.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PackageTypes.
 */
@Entity
@Table(name = "package_types")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PackageTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "package_type_name", nullable = false)
    private String packageTypeName;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @NotNull
    @Column(name = "valid_to", nullable = false)
    private LocalDate validTo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageTypeName() {
        return packageTypeName;
    }

    public PackageTypes packageTypeName(String packageTypeName) {
        this.packageTypeName = packageTypeName;
        return this;
    }

    public void setPackageTypeName(String packageTypeName) {
        this.packageTypeName = packageTypeName;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public PackageTypes validFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public PackageTypes validTo(LocalDate validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PackageTypes)) {
            return false;
        }
        return id != null && id.equals(((PackageTypes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PackageTypes{" +
            "id=" + getId() +
            ", packageTypeName='" + getPackageTypeName() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
