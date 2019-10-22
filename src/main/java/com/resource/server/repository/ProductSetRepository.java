package com.resource.server.repository;
import com.resource.server.domain.ProductSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSetRepository extends JpaRepository<ProductSet, Long>, JpaSpecificationExecutor<ProductSet> {

}
