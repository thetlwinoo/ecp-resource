package com.resource.server.repository;
import com.resource.server.domain.BusinessEntityAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BusinessEntityAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessEntityAddressRepository extends JpaRepository<BusinessEntityAddress, Long>, JpaSpecificationExecutor<BusinessEntityAddress> {

}
