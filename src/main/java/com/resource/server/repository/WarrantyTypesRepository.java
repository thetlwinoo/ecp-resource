package com.resource.server.repository;
import com.resource.server.domain.WarrantyTypes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WarrantyTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WarrantyTypesRepository extends JpaRepository<WarrantyTypes, Long>, JpaSpecificationExecutor<WarrantyTypes> {

}
