package com.resource.server.repository;
import com.resource.server.domain.Addresses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Addresses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressesRepository extends JpaRepository<Addresses, Long>, JpaSpecificationExecutor<Addresses> {

}
