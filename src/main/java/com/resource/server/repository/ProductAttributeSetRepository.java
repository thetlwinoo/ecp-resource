package com.resource.server.repository;
import com.resource.server.domain.ProductAttributeSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductAttributeSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAttributeSetRepository extends JpaRepository<ProductAttributeSet, Long>, JpaSpecificationExecutor<ProductAttributeSet> {

}
