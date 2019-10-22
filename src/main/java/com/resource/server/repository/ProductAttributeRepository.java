package com.resource.server.repository;
import com.resource.server.domain.ProductAttribute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long>, JpaSpecificationExecutor<ProductAttribute> {

}
