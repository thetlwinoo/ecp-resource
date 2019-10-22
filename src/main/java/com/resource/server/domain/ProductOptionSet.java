package com.resource.server.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProductOptionSet.
 */
@Entity
@Table(name = "product_option_set")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductOptionSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "product_option_set_value", nullable = false)
    private String productOptionSetValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductOptionSetValue() {
        return productOptionSetValue;
    }

    public ProductOptionSet productOptionSetValue(String productOptionSetValue) {
        this.productOptionSetValue = productOptionSetValue;
        return this;
    }

    public void setProductOptionSetValue(String productOptionSetValue) {
        this.productOptionSetValue = productOptionSetValue;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductOptionSet)) {
            return false;
        }
        return id != null && id.equals(((ProductOptionSet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductOptionSet{" +
            "id=" + getId() +
            ", productOptionSetValue='" + getProductOptionSetValue() + "'" +
            "}";
    }
}
