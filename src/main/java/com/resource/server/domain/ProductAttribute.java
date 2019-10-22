package com.resource.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProductAttribute.
 */
@Entity
@Table(name = "product_attribute")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "product_attribute_value", nullable = false)
    private String productAttributeValue;

    @ManyToOne
    @JsonIgnoreProperties("productAttributes")
    private ProductAttributeSet productAttributeSet;

    @ManyToOne
    @JsonIgnoreProperties("productAttributes")
    private Suppliers supplier;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductAttributeValue() {
        return productAttributeValue;
    }

    public ProductAttribute productAttributeValue(String productAttributeValue) {
        this.productAttributeValue = productAttributeValue;
        return this;
    }

    public void setProductAttributeValue(String productAttributeValue) {
        this.productAttributeValue = productAttributeValue;
    }

    public ProductAttributeSet getProductAttributeSet() {
        return productAttributeSet;
    }

    public ProductAttribute productAttributeSet(ProductAttributeSet productAttributeSet) {
        this.productAttributeSet = productAttributeSet;
        return this;
    }

    public void setProductAttributeSet(ProductAttributeSet productAttributeSet) {
        this.productAttributeSet = productAttributeSet;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public ProductAttribute supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductAttribute)) {
            return false;
        }
        return id != null && id.equals(((ProductAttribute) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductAttribute{" +
            "id=" + getId() +
            ", productAttributeValue='" + getProductAttributeValue() + "'" +
            "}";
    }
}
