package com.resource.server.repository;
import com.resource.server.domain.ShipMethod;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShipMethod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipMethodRepository extends JpaRepository<ShipMethod, Long>, JpaSpecificationExecutor<ShipMethod> {

}
