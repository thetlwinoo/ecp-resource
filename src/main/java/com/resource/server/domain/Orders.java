package com.resource.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Orders.
 */
@Entity
@Table(name = "orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "ship_date")
    private LocalDate shipDate;

    @Column(name = "payment_status")
    private Integer paymentStatus;

    @Column(name = "order_flag")
    private Integer orderFlag;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "sub_total")
    private Float subTotal;

    @Column(name = "tax_amount")
    private Float taxAmount;

    @Column(name = "frieight")
    private Float frieight;

    @Column(name = "total_due")
    private Float totalDue;

    @Column(name = "comments")
    private String comments;

    @Column(name = "delivery_instructions")
    private String deliveryInstructions;

    @Column(name = "internal_comments")
    private String internalComments;

    @Column(name = "picking_completed_when")
    private LocalDate pickingCompletedWhen;

    @Column(name = "last_edited_by")
    private String lastEditedBy;

    @Column(name = "last_edited_when")
    private LocalDate lastEditedWhen;

    @OneToOne
    @JoinColumn(unique = true)
    private Reviews orderOnReview;

    @OneToMany(mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderLines> orderLineLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Customers customer;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Addresses shipToAddress;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Addresses billToAddress;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private ShipMethod shipMethod;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private CurrencyRate currencyRate;

    @OneToOne(mappedBy = "paymentOnOrder")
    @JsonIgnore
    private PaymentTransactions paymentTransaction;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private SpecialDeals specialDeals;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Orders orderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Orders dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getShipDate() {
        return shipDate;
    }

    public Orders shipDate(LocalDate shipDate) {
        this.shipDate = shipDate;
        return this;
    }

    public void setShipDate(LocalDate shipDate) {
        this.shipDate = shipDate;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public Orders paymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getOrderFlag() {
        return orderFlag;
    }

    public Orders orderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
        return this;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Orders orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Float getSubTotal() {
        return subTotal;
    }

    public Orders subTotal(Float subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public void setSubTotal(Float subTotal) {
        this.subTotal = subTotal;
    }

    public Float getTaxAmount() {
        return taxAmount;
    }

    public Orders taxAmount(Float taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public void setTaxAmount(Float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Float getFrieight() {
        return frieight;
    }

    public Orders frieight(Float frieight) {
        this.frieight = frieight;
        return this;
    }

    public void setFrieight(Float frieight) {
        this.frieight = frieight;
    }

    public Float getTotalDue() {
        return totalDue;
    }

    public Orders totalDue(Float totalDue) {
        this.totalDue = totalDue;
        return this;
    }

    public void setTotalDue(Float totalDue) {
        this.totalDue = totalDue;
    }

    public String getComments() {
        return comments;
    }

    public Orders comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public Orders deliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
        return this;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public Orders internalComments(String internalComments) {
        this.internalComments = internalComments;
        return this;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public LocalDate getPickingCompletedWhen() {
        return pickingCompletedWhen;
    }

    public Orders pickingCompletedWhen(LocalDate pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
        return this;
    }

    public void setPickingCompletedWhen(LocalDate pickingCompletedWhen) {
        this.pickingCompletedWhen = pickingCompletedWhen;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public Orders lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public LocalDate getLastEditedWhen() {
        return lastEditedWhen;
    }

    public Orders lastEditedWhen(LocalDate lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(LocalDate lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Reviews getOrderOnReview() {
        return orderOnReview;
    }

    public Orders orderOnReview(Reviews reviews) {
        this.orderOnReview = reviews;
        return this;
    }

    public void setOrderOnReview(Reviews reviews) {
        this.orderOnReview = reviews;
    }

    public Set<OrderLines> getOrderLineLists() {
        return orderLineLists;
    }

    public Orders orderLineLists(Set<OrderLines> orderLines) {
        this.orderLineLists = orderLines;
        return this;
    }

    public Orders addOrderLineList(OrderLines orderLines) {
        this.orderLineLists.add(orderLines);
        orderLines.setOrder(this);
        return this;
    }

    public Orders removeOrderLineList(OrderLines orderLines) {
        this.orderLineLists.remove(orderLines);
        orderLines.setOrder(null);
        return this;
    }

    public void setOrderLineLists(Set<OrderLines> orderLines) {
        this.orderLineLists = orderLines;
    }

    public Customers getCustomer() {
        return customer;
    }

    public Orders customer(Customers customers) {
        this.customer = customers;
        return this;
    }

    public void setCustomer(Customers customers) {
        this.customer = customers;
    }

    public Addresses getShipToAddress() {
        return shipToAddress;
    }

    public Orders shipToAddress(Addresses addresses) {
        this.shipToAddress = addresses;
        return this;
    }

    public void setShipToAddress(Addresses addresses) {
        this.shipToAddress = addresses;
    }

    public Addresses getBillToAddress() {
        return billToAddress;
    }

    public Orders billToAddress(Addresses addresses) {
        this.billToAddress = addresses;
        return this;
    }

    public void setBillToAddress(Addresses addresses) {
        this.billToAddress = addresses;
    }

    public ShipMethod getShipMethod() {
        return shipMethod;
    }

    public Orders shipMethod(ShipMethod shipMethod) {
        this.shipMethod = shipMethod;
        return this;
    }

    public void setShipMethod(ShipMethod shipMethod) {
        this.shipMethod = shipMethod;
    }

    public CurrencyRate getCurrencyRate() {
        return currencyRate;
    }

    public Orders currencyRate(CurrencyRate currencyRate) {
        this.currencyRate = currencyRate;
        return this;
    }

    public void setCurrencyRate(CurrencyRate currencyRate) {
        this.currencyRate = currencyRate;
    }

    public PaymentTransactions getPaymentTransaction() {
        return paymentTransaction;
    }

    public Orders paymentTransaction(PaymentTransactions paymentTransactions) {
        this.paymentTransaction = paymentTransactions;
        return this;
    }

    public void setPaymentTransaction(PaymentTransactions paymentTransactions) {
        this.paymentTransaction = paymentTransactions;
    }

    public SpecialDeals getSpecialDeals() {
        return specialDeals;
    }

    public Orders specialDeals(SpecialDeals specialDeals) {
        this.specialDeals = specialDeals;
        return this;
    }

    public void setSpecialDeals(SpecialDeals specialDeals) {
        this.specialDeals = specialDeals;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Orders)) {
            return false;
        }
        return id != null && id.equals(((Orders) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Orders{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", shipDate='" + getShipDate() + "'" +
            ", paymentStatus=" + getPaymentStatus() +
            ", orderFlag=" + getOrderFlag() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", subTotal=" + getSubTotal() +
            ", taxAmount=" + getTaxAmount() +
            ", frieight=" + getFrieight() +
            ", totalDue=" + getTotalDue() +
            ", comments='" + getComments() + "'" +
            ", deliveryInstructions='" + getDeliveryInstructions() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", pickingCompletedWhen='" + getPickingCompletedWhen() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
