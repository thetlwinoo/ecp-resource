package com.resource.server.repository;
import com.resource.server.domain.ContactType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContactType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long>, JpaSpecificationExecutor<ContactType> {

}
