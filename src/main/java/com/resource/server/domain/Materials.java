package com.resource.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Materials.
 */
@Entity
@Table(name = "materials")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Materials implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "material_name", nullable = false)
    private String materialName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public Materials materialName(String materialName) {
        this.materialName = materialName;
        return this;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Materials)) {
            return false;
        }
        return id != null && id.equals(((Materials) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Materials{" +
            "id=" + getId() +
            ", materialName='" + getMaterialName() + "'" +
            "}";
    }
}
