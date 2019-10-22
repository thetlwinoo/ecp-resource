package com.resource.server.repository;
import com.resource.server.domain.AddressTypes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AddressTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressTypesRepository extends JpaRepository<AddressTypes, Long>, JpaSpecificationExecutor<AddressTypes> {

}
