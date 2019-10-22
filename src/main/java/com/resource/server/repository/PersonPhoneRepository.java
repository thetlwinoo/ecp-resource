package com.resource.server.repository;
import com.resource.server.domain.PersonPhone;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PersonPhone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonPhoneRepository extends JpaRepository<PersonPhone, Long>, JpaSpecificationExecutor<PersonPhone> {

}
