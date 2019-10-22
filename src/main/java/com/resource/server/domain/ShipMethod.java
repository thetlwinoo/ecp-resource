package com.resource.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ShipMethod.
 */
@Entity
@Table(name = "ship_method")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShipMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ship_method_name")
    private String shipMethodName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShipMethodName() {
        return shipMethodName;
    }

    public ShipMethod shipMethodName(String shipMethodName) {
        this.shipMethodName = shipMethodName;
        return this;
    }

    public void setShipMethodName(String shipMethodName) {
        this.shipMethodName = shipMethodName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipMethod)) {
            return false;
        }
        return id != null && id.equals(((ShipMethod) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ShipMethod{" +
            "id=" + getId() +
            ", shipMethodName='" + getShipMethodName() + "'" +
            "}";
    }
}
