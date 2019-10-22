package com.resource.server.repository;
import com.resource.server.domain.Culture;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Culture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CultureRepository extends JpaRepository<Culture, Long>, JpaSpecificationExecutor<Culture> {

}
