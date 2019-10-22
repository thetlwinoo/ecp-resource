package com.resource.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UploadActionTypes.
 */
@Entity
@Table(name = "upload_action_types")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UploadActionTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "action_type_name")
    private String actionTypeName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActionTypeName() {
        return actionTypeName;
    }

    public UploadActionTypes actionTypeName(String actionTypeName) {
        this.actionTypeName = actionTypeName;
        return this;
    }

    public void setActionTypeName(String actionTypeName) {
        this.actionTypeName = actionTypeName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadActionTypes)) {
            return false;
        }
        return id != null && id.equals(((UploadActionTypes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UploadActionTypes{" +
            "id=" + getId() +
            ", actionTypeName='" + getActionTypeName() + "'" +
            "}";
    }
}
