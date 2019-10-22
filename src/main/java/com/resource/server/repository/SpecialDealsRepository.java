package com.resource.server.repository;
import com.resource.server.domain.SpecialDeals;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SpecialDeals entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialDealsRepository extends JpaRepository<SpecialDeals, Long>, JpaSpecificationExecutor<SpecialDeals> {

}
