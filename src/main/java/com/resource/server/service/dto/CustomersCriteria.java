package com.resource.server.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.resource.server.domain.Customers} entity. This class is used
 * in {@link com.resource.server.web.rest.CustomersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /customers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CustomersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter accountNumber;

    private LongFilter personId;

    public CustomersCriteria(){
    }

    public CustomersCriteria(CustomersCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.personId = other.personId == null ? null : other.personId.copy();
    }

    @Override
    public CustomersCriteria copy() {
        return new CustomersCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LongFilter getPersonId() {
        return personId;
    }

    public void setPersonId(LongFilter personId) {
        this.personId = personId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomersCriteria that = (CustomersCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        accountNumber,
        personId
        );
    }

    @Override
    public String toString() {
        return "CustomersCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
                (personId != null ? "personId=" + personId + ", " : "") +
            "}";
    }

}
