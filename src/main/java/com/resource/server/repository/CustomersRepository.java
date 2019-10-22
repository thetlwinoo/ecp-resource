package com.resource.server.repository;
import com.resource.server.domain.Customers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Customers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long>, JpaSpecificationExecutor<Customers> {

}
