package com.resource.server.repository;
import com.resource.server.domain.SystemParameters;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SystemParameters entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemParametersRepository extends JpaRepository<SystemParameters, Long>, JpaSpecificationExecutor<SystemParameters> {

}
