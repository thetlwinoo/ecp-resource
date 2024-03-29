package com.resource.server.repository;
import com.resource.server.domain.Suppliers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Suppliers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuppliersRepository extends JpaRepository<Suppliers, Long>, JpaSpecificationExecutor<Suppliers> {

}
